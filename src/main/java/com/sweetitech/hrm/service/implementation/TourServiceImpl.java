package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.ConstantsUtil;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.MobileLogs;
import com.sweetitech.hrm.domain.Tour;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.MobileLogsResponseDTO;
import com.sweetitech.hrm.domain.dto.NotificationResponseDTO;
import com.sweetitech.hrm.domain.dto.TourDTO;
import com.sweetitech.hrm.domain.dto.TourResponseDTO;
import com.sweetitech.hrm.repository.TourRepository;
import com.sweetitech.hrm.service.PushNotificationService;
import com.sweetitech.hrm.service.TourService;
import com.sweetitech.hrm.service.mapping.NotificationMapper;
import com.sweetitech.hrm.service.mapping.TourMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class TourServiceImpl implements TourService {

    private TourRepository tourRepository;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TourMapper tourMapper;

    @Autowired
    private MobileLogsServiceImpl mobileLogsService;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public Tour create(Tour tour) throws Exception {
        return tourRepository.save(tour);
    }

    @Override
    public TourResponseDTO create(TourDTO dto) throws Exception {
        Tour tour = tourMapper.map(dto);
        TourResponseDTO created = tourMapper.map(this.create(tour));

        List<User> users = userService.readAllAdminsAndAccounts(tour.getRequestedBy().getCompany().getId());

        NotificationResponseDTO notification;
        if (users != null && users.size() > 0) {
            for (User user: users) {
                notification  = notificationService.create(notificationMapper.map(tour, user.getId()));

                pushNotificationService.pushNotification(notification, user.getId());
            }
        }

        return created;
    }

    @Override
    public Tour update(Long id, Tour tour) throws Exception {
        if (this.read(id) == null) {
            throw new EntityNotFoundException("No tours with id: " + id);
        }

        tour.setId(id);
        return tourRepository.save(tour);
    }

    @Override
    public TourResponseDTO update(Long id, TourDTO dto, boolean fromRequest) throws Exception {
        if (this.read(id) == null) {
            throw new EntityNotFoundException("No tours with id: " + id);
        }

        Tour tour = this.read(id);
        if (fromRequest
                && !tour.getStatus().equals(Constants.RequestStatus.REQUESTED)) {
            throw new EntityNotFoundException("Tour cannot be edited now!");
        }

        return tourMapper.map(this.update(id, tourMapper.map(dto)));
    }

    @Override
    public Tour read(Long id) throws Exception {
        return tourRepository.getFirstById(id);
    }

    @Override
    public TourResponseDTO readDTO(Long id) throws Exception {
        if (this.read(id) == null) {
            throw new EntityNotFoundException("No tours with id: " + id);
        }

        return tourMapper.map(this.read(id));
    }

    @Override
    public TourResponseDTO updateStatus(Long id, Long userId, String status) throws Exception {
        Tour tour = this.read(id);
        if (tour == null) {
            throw new EntityNotFoundException("No tours with id: " + id);
        }

        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status found!");
        }

        tour.setStatusBy(user);
        tour.setStatus(status);

        if (status.equals(Constants.RequestStatus.APPROVED)) {
            Calendar calStart = Calendar.getInstance();
            calStart.setTime(tour.getFromDate());

            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(tour.getToDate());

            if (calStart.equals(calEnd)) {
                // Check in
                mobileLogsService.create(this.prepareMobileLog(tour, calStart, true));

                // Check out
                mobileLogsService.create(this.prepareMobileLog(tour, calStart, false));
            } else {
                calEnd.add(Calendar.DATE, 1);
                while (calStart.before(calEnd)) {
                    // Check in
                    mobileLogsService.create(this.prepareMobileLog(tour, calStart, true));

                    // Check out
                    mobileLogsService.create(this.prepareMobileLog(tour, calStart, false));

                    calStart.add(Calendar.DATE, 1);
                }
            }
        }

        TourResponseDTO updated = tourMapper.map(this.update(id, tour));

        if (status.equals(Constants.RequestStatus.APPROVED)
                || status.equals(Constants.RequestStatus.DECLINED)) {
            NotificationResponseDTO notification  = notificationService.create(notificationMapper.map(tour, status));

            pushNotificationService.pushNotification(notification, tour.getRequestedBy().getId());
        }

        return updated;
    }

    private MobileLogs prepareMobileLog(Tour tour, Calendar calStart, boolean checkIn) throws Exception {
        MobileLogs mobileLogs = new MobileLogs();
        mobileLogs.setLat(0.0);
        mobileLogs.setLng(0.0);
        mobileLogs.setManualEntry(true);
        mobileLogs.setName(tour.getLocation());
        mobileLogs.setVicinity(tour.getLocation());
        mobileLogs.setUser(tour.getRequestedBy());

        if (checkIn) {
            calStart.set(Calendar.HOUR_OF_DAY, 10);
        } else {
            calStart.set(Calendar.HOUR_OF_DAY, 18);
        }
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        mobileLogs.setTimestamp(calStart.getTimeInMillis());

        return mobileLogs;
    }

    @Override
    public List<TourResponseDTO> readByCompanyAndStatusAndMonth(Long companyId, String status, Integer month, Integer year) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status found!");
        }

        if (!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        List<TourResponseDTO> dtos = new ArrayList<>();

        List<Tour> tours = tourRepository.findAllByCompanyStatusAndMonth(companyId, status, month, year);
        if (tours != null && tours.size() > 0) {
            for (Tour tour: tours) {
                dtos.add(tourMapper.map(tour));
            }
        }

        return dtos;
    }

    @Override
    public List<TourResponseDTO> readForUser(Long userId, Integer year) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        if (!DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid year found!");
        }

        List<TourResponseDTO> dtos = new ArrayList<>();

        List<Tour> tours = tourRepository.findAllByUserAndYear(userId, year, Constants.RequestStatus.APPROVED);
        if (tours != null && tours.size() > 0) {
            for (Tour tour: tours) {
                dtos.add(tourMapper.map(tour));
            }
        }

        return dtos;
    }

    @Override
    public void cancel(Long id) throws Exception {
        Tour tour = this.read(id);
        if (tour == null) {
            throw new EntityNotFoundException("No tours with id: " + id);
        }

        if (!tour.getStatus().equals(Constants.RequestStatus.REQUESTED)) {
            throw new EntityNotFoundException("Tour cannot be cancelled now!");
        }

        tour.setStatus(Constants.RequestStatus.CANCELLED);
        tour.setStatusBy(tour.getRequestedBy());
        this.update(id, tour);
    }

    @Override
    public String checkStatus(Long id) throws Exception {
        Tour tour = this.read(id);
        if (tour == null) {
            throw new EntityNotFoundException("No tours with id: " + id);
        }

        return tour.getStatus();
    }

    @Override
    public List<TourResponseDTO> readByUserAndStatusAndMonth(Long userId, String status, Integer month, Integer year) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No companies with id: " + userId);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status found!");
        }

        if (!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        List<TourResponseDTO> dtos = new ArrayList<>();

        List<Tour> tours = tourRepository.findAllByUserStatusAndMonth(userId, status, month, year);
        if (tours != null && tours.size() > 0) {
            for (Tour tour: tours) {
                dtos.add(tourMapper.map(tour));
            }
        }

        return dtos;
    }

    @Override
    public List<TourResponseDTO> readByUserAndStatus(Long userId, String status) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No companies with id: " + userId);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status found!");
        }

        List<TourResponseDTO> dtos = new ArrayList<>();

        List<Tour> tours = tourRepository.findAllByUserAndStatus(userId, status);
        if (tours != null && tours.size() > 0) {
            for (Tour tour: tours) {
                dtos.add(tourMapper.map(tour));
            }
        }

        return dtos;
    }
}
