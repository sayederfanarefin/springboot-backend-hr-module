package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.domain.*;
import com.sweetitech.hrm.domain.dto.CarScheduleDTO;
import com.sweetitech.hrm.domain.dto.CarScheduleResponseDTO;
import com.sweetitech.hrm.domain.dto.NotificationResponseDTO;
import com.sweetitech.hrm.domain.dto.page.CarSchedulePage;
import com.sweetitech.hrm.repository.CarScheduleRepository;
import com.sweetitech.hrm.service.CarScheduleService;
import com.sweetitech.hrm.service.PushNotificationService;
import com.sweetitech.hrm.service.mapping.CarScheduleResponseMapper;
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
public class CarScheduleServiceImpl implements CarScheduleService {

    private CarScheduleRepository carScheduleRepository;

    @Autowired
    private CarServiceImpl carService;

    @Autowired
    private DriverServiceImpl driverService;

    @Autowired
    private CarRequestServiceImpl carRequestService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CarScheduleResponseMapper carScheduleResponseMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    public CarScheduleServiceImpl(CarScheduleRepository carScheduleRepository) {
        this.carScheduleRepository = carScheduleRepository;
    }

    CarSchedulePage preparePage(Page<CarSchedule> schedules) throws Exception {
        List<CarScheduleResponseDTO> dtos = new ArrayList<>();

        if (schedules.getContent() != null && schedules.getContent().size() > 0) {
            for (CarSchedule schedule: schedules.getContent()) {
                dtos.add(carScheduleResponseMapper.map(schedule));
            }
        }

        return new CarSchedulePage(dtos, schedules);
    }

    public int calculateOccupiedFor(Date fromDate, int fromHour, Date toDate) throws Exception {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(fromDate);
        calStart.set(Calendar.HOUR_OF_DAY, fromHour);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(toDate);

        if (calEnd.before(calStart) || calEnd.equals(calStart)) {
            return 0;
        }

        long seconds = (calEnd.getTimeInMillis() - calStart.getTimeInMillis()) / 1000;
        return (int) (seconds / 3600);
    }

    @Override
    public CarSchedule readEntity(Long id) throws Exception {
        return carScheduleRepository.getFirstById(id);
    }

    @Override
    public CarScheduleResponseDTO read(Long id) throws Exception {
        if (this.readEntity(id) == null) {
            throw new EntityNotFoundException("No schedules with id: " + id);
        }
        return carScheduleResponseMapper.map(this.readEntity(id));
    }

    @Override
    public CarScheduleResponseDTO create(CarSchedule carSchedule) throws Exception {
        carSchedule = carScheduleRepository.save(carSchedule);
        carRequestService.updateStatus(carSchedule.getCarRequest().getId(), Constants.RequestStatus.APPROVED);
        CarScheduleResponseDTO carScheduleResponseDTO = carScheduleResponseMapper.map(carSchedule);

        NotificationResponseDTO notification = notificationService.create(notificationMapper.map(carScheduleResponseDTO));

        pushNotificationService.pushNotification(notification, carScheduleResponseDTO.getCarRequest().getRequestedByUser().getUserId());

        return carScheduleResponseDTO;
    }

    @Override
    public CarScheduleResponseDTO update(Long id, CarSchedule carSchedule) throws Exception {
        CarSchedule carScheduleOld = this.readEntity(id);
        if (carScheduleOld == null) {
            throw new EntityNotFoundException("No schedules with id: " + id);
        }

        carRequestService.updateStatus(carScheduleOld.getCarRequest().getId(), Constants.RequestStatus.REQUESTED);

        carSchedule.setId(id);
        carSchedule = carScheduleRepository.save(carSchedule);
        carRequestService.updateStatus(carSchedule.getCarRequest().getId(), Constants.RequestStatus.APPROVED);
        return carScheduleResponseMapper.map(carSchedule);
    }

    @Override
    public CarScheduleResponseDTO updateCompletedDate(Long id, Date date) throws Exception {
        if (!DateTimeUtils.isValidDate(date.toString(), Constants.Dates.VALID_FORMAT)) {
            throw new EntityNotFoundException("Invalid date found!");
        }

        CarSchedule carSchedule = this.readEntity(id);
        if (carSchedule == null) {
            throw new EntityNotFoundException("No schedules with id: " + id);
        }

        carSchedule.setCompletedOn(date);

        carSchedule.setOccupiedFor(calculateOccupiedFor(carSchedule.getCarRequest().getRequestedFrom(),
                carSchedule.getCarRequest().getFromHour(), date));

        return carScheduleResponseMapper.map(carScheduleRepository.save(carSchedule));
    }

    @Override
    public CarSchedulePage readAllByDateAndCompanyId(Date date, Long companyId, Integer page) throws Exception {
        System.out.println("Date: " + date.toString() + " " + Constants.Dates.DATE_FORMAT);
        if (!DateTimeUtils.isValidDate(date.toString(), Constants.Dates.VALID_FORMAT)) {
            throw new EntityNotFoundException("Invalid date found!");
        }

        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Page<CarSchedule> schedules = carScheduleRepository
                .findAllByCompanyIdAndDate(companyId, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.YEAR), new PageRequest(page, PageAttr.PAGE_SIZE));

        List<CarScheduleResponseDTO> dtos = new ArrayList<>();
        if (schedules.getContent() != null && schedules.getContent().size() > 0) {
            for (CarSchedule schedule: schedules.getContent()) {
                dtos.add(carScheduleResponseMapper.map(schedule));
            }
        }

        return new CarSchedulePage(dtos, schedules);
    }

    @Override
    public CarSchedulePage readAllByCar(Long carId, Integer page) throws Exception {
        if (carService.readEntity(carId) == null) {
            throw new EntityNotFoundException("No cars with id: " + carId);
        }

        return preparePage(carScheduleRepository.findAllByCarIdOrderByCarRequestRequestedFromDesc(carId,
                new PageRequest(page, PageAttr.PAGE_SIZE)));
    }

    @Override
    public CarSchedulePage readAllByDriver(Long driverId, Integer page) throws Exception {
        if (driverService.readEntity(driverId) == null) {
            throw new EntityNotFoundException("No drivers with id: " + driverId);
        }

        return preparePage(carScheduleRepository.findAllByDriverIdOrderByCarRequestRequestedFromDesc(driverId,
                new PageRequest(page, PageAttr.PAGE_SIZE)));
    }

    @Override
    public CarScheduleResponseDTO readByRequest(Long carRequestId) throws Exception {
        if (carRequestService.read(carRequestId) == null) {
            throw new EntityNotFoundException("No requests with id: " + carRequestId);
        }

        return carScheduleResponseMapper.map(carScheduleRepository.getFirstByCarRequestId(carRequestId));
    }

    @Override
    public void remove(Long id) throws Exception {
        CarSchedule carSchedule = this.readEntity(id);
        if (carSchedule == null) {
            throw new EntityNotFoundException("No schedules with id: " + id);
        }

        carRequestService.updateStatus(carSchedule.getCarRequest().getId(), Constants.RequestStatus.REQUESTED);
        carScheduleRepository.delete(carSchedule);
    }

    @Override
    public CarSchedulePage readAllCompletedByCompanyId(Long companyId, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        return preparePage(carScheduleRepository.
                findAllByCarRequestRequestedByCompanyIdAndCompletedOnNotNullOrderByCarRequestRequestedFromDesc(companyId,
                        new PageRequest(page, PageAttr.PAGE_SIZE)));
    }

    @Override
    public CarSchedulePage readAllNotCompletedByCompanyId(Long companyId, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        return preparePage(carScheduleRepository.
                findAllByCarRequestRequestedByCompanyIdAndCompletedOnNullOrderByCarRequestRequestedFromDesc(companyId,
                        new PageRequest(page, PageAttr.PAGE_SIZE)));
    }

    @Override
    public CarSchedulePage readAllByUserId(Long userId, Integer page) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        return preparePage(carScheduleRepository.findAllByCarRequestRequestedByIdOrderByCarRequestRequestedFromDesc(userId,
                new PageRequest(page, PageAttr.PAGE_SIZE)));
    }

    @Override
    public boolean checkCarStatus(Long carId, Date fromDate, Integer fH, Date toDate, Integer tH) throws Exception {
        Car car =  carService.readEntity(carId);

        if (carScheduleRepository.getFirstByCarId(carId) == null) {
            return false;
        }

        int occupant = 0;

        // Initialize dates
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(fromDate);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(toDate);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);

        Date fDate = calStart.getTime();
        Date tDate = calEnd.getTime();

        if (!calStart.equals(calEnd)) {
            if (carScheduleRepository.dateCondition1(carId, fDate, tDate) != null
                    || carScheduleRepository.dateCondition2(carId, fDate, tDate) != null
                    || carScheduleRepository.dateCondition3(carId, fDate, tDate) != null
                    || carScheduleRepository.dateCondition4(carId, fDate, tDate) != null
                    || carScheduleRepository.dateCondition5(carId, fDate, tDate) != null)
                return true;
        } else {
            List<CarSchedule> schedules = carScheduleRepository.findAllByCarAndDate(carId,
                    calStart.get(Calendar.DAY_OF_MONTH), calStart.get(Calendar.MONTH) + 1, calStart.get(Calendar.YEAR));

            if (schedules != null && schedules.size() > 0) {
                for (CarSchedule schedule: schedules) {
                    int fromHour = schedule.getCarRequest().getFromHour();
                    int toHour = schedule.getCarRequest().getToHour();

                    if ((fH < fromHour && toHour < tH)
                            || (fH < fromHour && tH <= toHour)
                            || (fromHour <= fH && toHour < tH)
                            || (fromHour == fH && toHour == tH)
                            || (fromHour < fH && tH < toHour)) {
                        occupant++;
                        //return true;
                    }
                }
            }
        }

        return occupant >= car.getCapacity();
        // return false;
    }

    @Override
    public boolean checkDriverStatus(Long driverId, Date fromDate, Integer fH, Date toDate, Integer tH) throws Exception {
        if (carScheduleRepository.getFirstByDriverId(driverId) == null) {
            System.out.println("Line 251");
            return false;
        }

        // Initialize dates
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(fromDate);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(toDate);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);

        Date fDate = calStart.getTime();
        Date tDate = calEnd.getTime();

        if (!calStart.equals(calEnd)) {
            System.out.println("Line 274");
            if (carScheduleRepository.dateDriverCondition1(driverId, fDate, tDate) != null
                    || carScheduleRepository.dateDriverCondition2(driverId, fDate, tDate) != null
                    || carScheduleRepository.dateDriverCondition3(driverId, fDate, tDate) != null
                    || carScheduleRepository.dateDriverCondition4(driverId, fDate, tDate) != null
                    || carScheduleRepository.dateDriverCondition5(driverId, fDate, tDate) != null)
                return true;
        } else {
            System.out.println("Line 228");
            List<CarSchedule> schedules = carScheduleRepository.findAllByDriverAndDate(driverId,
                    calStart.get(Calendar.DAY_OF_MONTH), calStart.get(Calendar.MONTH) + 1, calStart.get(Calendar.YEAR));

            System.out.println(schedules);

            int fromHour, toHour;
            if (schedules != null && schedules.size() > 0) {
                System.out.println("Line 288");
                for (CarSchedule schedule: schedules) {
                    fromHour = schedule.getCarRequest().getFromHour();
                    toHour = schedule.getCarRequest().getToHour();

                    if ((fH < fromHour && toHour < tH)
                            || (fH < fromHour && tH <= toHour)
                            || (fromHour <= fH && toHour < tH)
                            || (fromHour == fH && toHour == tH)
                            || (fromHour < fH && tH < toHour)) {
                        System.out.println("Line 298");
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public Driver readDriverBySchedule(Long carId, Date fromDate, Integer fH, Date toDate, Integer tH) throws Exception {
        Car car =  carService.readEntity(carId);

        if (car == null) {
            System.out.println("Line 376");
            return null;
        }

        if (carScheduleRepository.getFirstByCarId(carId) == null) {
            System.out.println("Line 381");
            return null;
        }

        // Initialize dates
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(fromDate);
        calStart.set(Calendar.HOUR_OF_DAY, 0);
        calStart.set(Calendar.MINUTE, 0);
        calStart.set(Calendar.SECOND, 0);
        calStart.set(Calendar.MILLISECOND, 0);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(toDate);
        calEnd.set(Calendar.HOUR_OF_DAY, 0);
        calEnd.set(Calendar.MINUTE, 0);
        calEnd.set(Calendar.SECOND, 0);
        calEnd.set(Calendar.MILLISECOND, 0);

        Date fDate = calStart.getTime();
        Date tDate = calEnd.getTime();

        if (!calStart.equals(calEnd)) {
            if (carScheduleRepository.dateCondition1(carId, fDate, tDate) != null
                    || carScheduleRepository.dateCondition2(carId, fDate, tDate) != null
                    || carScheduleRepository.dateCondition3(carId, fDate, tDate) != null
                    || carScheduleRepository.dateCondition4(carId, fDate, tDate) != null
                    || carScheduleRepository.dateCondition5(carId, fDate, tDate) != null)
                System.out.println("Line 409");
                return null;
        } else {
            List<CarSchedule> schedules = carScheduleRepository.findAllByCarAndDate(carId,
                    calStart.get(Calendar.DAY_OF_MONTH), calStart.get(Calendar.MONTH) + 1, calStart.get(Calendar.YEAR));

            if (schedules != null && schedules.size() > 0) {
                for (CarSchedule schedule: schedules) {
                    int fromHour = schedule.getCarRequest().getFromHour();
                    int toHour = schedule.getCarRequest().getToHour();

                    if ((fH < fromHour && toHour < tH)
                            || (fH < fromHour && tH <= toHour)
                            || (fromHour <= fH && toHour < tH)
                            || (fromHour == fH && toHour == tH)
                            || (fromHour < fH && tH < toHour)) {
                        return schedule.getDriver();
                    }
                }
            }
        }

        System.out.println("Line 431");
        return null;
    }
}
