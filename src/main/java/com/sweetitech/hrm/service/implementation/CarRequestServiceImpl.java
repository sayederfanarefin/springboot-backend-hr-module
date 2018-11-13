package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.ConstantsUtil;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.CarRequest;
import com.sweetitech.hrm.domain.CarSchedule;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.domain.dto.page.CarRequestPage;
import com.sweetitech.hrm.repository.CarRequestRepository;
import com.sweetitech.hrm.service.CarRequestService;
import com.sweetitech.hrm.service.PushNotificationService;
import com.sweetitech.hrm.service.mapping.CarRequestResponseMapper;
import com.sweetitech.hrm.service.mapping.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class CarRequestServiceImpl implements CarRequestService {

    private CarRequestRepository carRequestRepository;

    @Autowired
    private CarRequestResponseMapper carRequestResponseMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private CarScheduleServiceImpl carScheduleService;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    public CarRequestServiceImpl(CarRequestRepository carRequestRepository) {
        this.carRequestRepository = carRequestRepository;
    }

    public CarRequestResponseDTO checkIfRequestedAndUpdate(Long id, CarRequest carRequest) throws Exception {
        CarRequest carRequestOld = carRequestRepository.getFirstById(id);
        if (carRequestOld == null) {
            throw new EntityNotFoundException("No car requests with id: " + id);
        }

        if (carRequestOld.getStatus().equals(Constants.RequestStatus.REQUESTED)) {
            return this.update(id, carRequest);
        } else {
            throw new EntityNotFoundException("Not allowed to update request now!");
        }
    }

    public void checkIfRequestedAndCancel(Long id) throws Exception {
        CarRequest carRequest = carRequestRepository.getFirstById(id);
        if (carRequest == null) {
            throw new EntityNotFoundException("No car requests with id: " + id);
        }

        if (carRequest.getStatus().equals(Constants.RequestStatus.REQUESTED)) {
            this.cancel(id);
        } else if (carScheduleService.readByRequest(id) != null && carScheduleService.readByRequest(id).getCompletedOn() == null) {
            CarScheduleResponseDTO carScheduleResponseDTO = this.deleteScheduleAndCancel(id);

            //TODO notify admin
            List<User> users = userService.readAllAdminsByCompany(carRequest.getRequestedBy().getCompany().getId());
            if (users != null && users.size() > 0) {
                NotificationResponseDTO notification;
                for (User user: users) {
                    notification = notificationService.create(notificationMapper.map(carScheduleResponseDTO, user.getId()));

                    pushNotificationService.pushNotification(notification, user.getId());
                }
            }
        } else {
            throw new EntityNotFoundException("Not allowed to cancel request now!");
        }
    }

    public CarRequestResponseDTO checkIfNotCancelledAndUpdate(Long id, CarRequest carRequest) throws Exception {
        CarRequest carRequestOld = carRequestRepository.getFirstById(id);
        if (carRequestOld == null) {
            throw new EntityNotFoundException("No car requests with id: " + id);
        }

        if (!carRequestOld.getStatus().equals(Constants.RequestStatus.CANCELLED)) {
            return this.update(id, carRequest);
        } else {
            throw new EntityNotFoundException("Request was already cancelled!");
        }
    }

    @Override
    public CarRequest read(Long id) throws Exception {
        return carRequestRepository.getFirstById(id);
    }

    @Override
    public CarRequestResponseDTO readDTO(Long id) throws Exception {
        if (this.read(id) == null) {
            throw new EntityNotFoundException("No requests with id: " + id);
        }

        return carRequestResponseMapper.map(this.read(id));
    }

    @Override
    public CarRequestResponseDTO create(CarRequest carRequest) throws Exception {
        CarRequestResponseDTO created = carRequestResponseMapper.map(carRequestRepository.save(carRequest));

        List<User> users = userService.readAllAdminsAndAccounts(carRequest.getRequestedBy().getCompany().getId());

        NotificationResponseDTO notification;
        if (users != null && users.size() > 0) {
            for (User user: users) {
                notification  = notificationService.create(notificationMapper.map(created, user.getId()));

                pushNotificationService.pushNotification(notification, user.getId());
            }
        }

        return created;
    }

    @Override
    public CarRequestResponseDTO update(Long id, CarRequest carRequest) throws Exception {
        CarRequest carRequestOld = carRequestRepository.getFirstById(id);
        if (carRequestOld == null) {
            throw new EntityNotFoundException("No car requests with id: " + id);
        }

        carRequest.setId(id);
        return carRequestResponseMapper.map(carRequestRepository.save(carRequest));
    }

    @Override
    public void cancel(Long id) throws Exception {
        CarRequest carRequest = carRequestRepository.getFirstById(id);
        if (carRequest == null) {
            throw new EntityNotFoundException("No car requests with id: " + id);
        }

        carRequest.setStatus(Constants.RequestStatus.CANCELLED);
        carRequestRepository.save(carRequest);
    }

    @Override
    public CarRequestPage readAllByRequestedByUserId(Long userId, String status, Integer page) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status format found!");
        }

        Page<CarRequest> requests = carRequestRepository.findAllByRequestedByIdAndStatusOrderByRequestedFromDesc(userId, status, new PageRequest(page, PageAttr.PAGE_SIZE));

        List<CarRequestResponseDTO> dtos = new ArrayList<>();
        if (requests.getContent() != null && requests.getContent().size() > 0) {
            for (CarRequest request: requests.getContent()) {
                dtos.add(carRequestResponseMapper.map(request));
            }
        }

        return new CarRequestPage(dtos, requests);
    }

    @Override
    public CarRequestPage readAllByCompanyIdAndDate(Long companyId, Date date, String status, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        if (!DateTimeUtils.isValidDate(date.toString(), Constants.Dates.VALID_FORMAT)) {
            throw new EntityNotFoundException("Invalid date found!");
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status format found!");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Page<CarRequest> requests = carRequestRepository.getAllByCompanyIdAndRequestedFrom(companyId, calendar.get(Calendar.YEAR),
                (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH), status, new PageRequest(page, PageAttr.PAGE_SIZE));

        List<CarRequestResponseDTO> dtos = new ArrayList<>();
        if (requests.getContent() != null && requests.getContent().size() > 0) {
            for (CarRequest request: requests.getContent()) {
                dtos.add(carRequestResponseMapper.map(request));
            }
        }

        return new CarRequestPage(dtos, requests);
    }

    @Override
    public CarRequestPage readAllByCompanyIdAndMonth(Long companyId, Integer year, Integer month, String status, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        if (!DateValidator.isValidYear(year) || !DateValidator.isValidMonth(month)) {
            throw new EntityNotFoundException("Invalid year or month!");
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status format found!");
        }

        Page<CarRequest> requests = carRequestRepository.getAllMonthlyByCompanyIdandStatus(companyId, year, month, status, new PageRequest(page, PageAttr.PAGE_SIZE));

        List<CarRequestResponseDTO> dtos = new ArrayList<>();
        if (requests.getContent() != null && requests.getContent().size() > 0) {
            for (CarRequest request: requests.getContent()) {
                dtos.add(carRequestResponseMapper.map(request));
            }
        }

        return new CarRequestPage(dtos, requests);
    }

    @Override
    public CarRequestResponseDTO updateStatus(Long id, String status) throws Exception {
        CarRequest carRequest = carRequestRepository.getFirstById(id);
        if (carRequest == null) {
            throw new EntityNotFoundException("No car requests with id: " + id);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status format found!");
        }

        carRequest.setStatus(status);
        return carRequestResponseMapper.map(carRequestRepository.save(carRequest));
    }

    @Override
    public CarScheduleResponseDTO deleteScheduleAndCancel(Long requestId) throws Exception {
        CarRequest carRequest = this.read(requestId);
        if (carRequest == null) {
            throw new EntityNotFoundException("No requests with id: " + requestId);
        }

        CarScheduleResponseDTO carSchedule = carScheduleService.readByRequest(requestId);
        if (carSchedule == null) {
            throw new EntityNotFoundException("No schedules for request with id: " + requestId);
        }

        if (carSchedule.getCompletedOn() != null) {
            throw new EntityNotFoundException("Not permitted to cancel request now!");
        }

        carScheduleService.remove(carSchedule.getCarScheduleId());

        this.cancel(carRequest.getId());

        return carSchedule;
    }
}
