package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.CarSchedule;
import com.sweetitech.hrm.domain.dto.CarScheduleDTO;
import com.sweetitech.hrm.domain.dto.CarScheduleResponseDTO;
import com.sweetitech.hrm.domain.dto.LeaveCountDTO;
import com.sweetitech.hrm.domain.dto.page.CarSchedulePage;
import com.sweetitech.hrm.service.implementation.CarScheduleServiceImpl;
import com.sweetitech.hrm.service.mapping.CarScheduleMapper;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/car-schedules")
public class AdminCarScheduleController {

    private CarScheduleServiceImpl carScheduleService;

    @Autowired
    private CarScheduleMapper carScheduleMapper;

    @Autowired
    public AdminCarScheduleController(CarScheduleServiceImpl carScheduleService) {
        this.carScheduleService = carScheduleService;
    }

    // Create car schedule
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CarScheduleResponseDTO create(@RequestBody CarScheduleDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return carScheduleService.create(carScheduleMapper.map(dto));
    }

    // Update car schedule by id
    @RequestMapping(value = "/{scheduleId}", method = RequestMethod.PUT)
    public CarScheduleResponseDTO update(@PathVariable Long scheduleId, @RequestBody CarScheduleDTO dto) throws Exception {
        if (scheduleId == null || dto == null) {
            throw new EntityNotFoundException("Null objects found!");
        }

        return carScheduleService.update(scheduleId, carScheduleMapper.map(dto));
    }

    // Delete car schedule
    @RequestMapping(value = "/{scheduleId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long scheduleId) throws Exception {
        if (scheduleId == null) {
            throw new EntityNotFoundException("Null schedule id found!");
        }

        carScheduleService.remove(scheduleId);
    }

    // Update completed date
    @RequestMapping(value = "/completed/{scheduleId}", method = RequestMethod.PUT)
    public CarScheduleResponseDTO updateCompletedDate(@PathVariable Long scheduleId,
                                                      @RequestBody LeaveCountDTO dto) throws Exception {
        if (scheduleId == null || dto == null || dto.getFromDate() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return carScheduleService.updateCompletedDate(scheduleId, dto.getFromDate());
    }

    // Get all by company and date
    @RequestMapping(value = "/company/{companyId}/date", method = RequestMethod.POST)
    public CarSchedulePage getAllByCompanyIdAndDate(@PathVariable Long companyId,
                                                    @RequestBody LeaveCountDTO dto,
                                                    @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null || dto == null || dto.getFromDate() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return carScheduleService.readAllByDateAndCompanyId(dto.getFromDate(), companyId, page);
    }

    // Get all by car id
    @RequestMapping(value = "/car/{carId}", method = RequestMethod.GET)
    public CarSchedulePage getAllByCarId(@PathVariable Long carId,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (carId == null) {
            throw new EntityNotFoundException("Null car id found!");
        }

        return carScheduleService.readAllByCar(carId, page);
    }

    // Get all by driver id
    @RequestMapping(value = "/driver/{driverId}", method = RequestMethod.GET)
    public CarSchedulePage getAllByDriverId(@PathVariable Long driverId,
                                            @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (driverId == null) {
            throw new EntityNotFoundException("Null driver id found!");
        }

        return carScheduleService.readAllByDriver(driverId, page);
    }

    // Get all by car request id
    @RequestMapping(value = "/car-request/{requestId}", method = RequestMethod.GET)
    public CarScheduleResponseDTO getAllByCarRequestId(@PathVariable Long requestId) throws Exception {
        if (requestId == null) {
            throw new EntityNotFoundException("Null request id found!");
        }

        return carScheduleService.readByRequest(requestId);
    }

    // Get all completed by company id
    @RequestMapping(value = "/company/{companyId}/completed", method = RequestMethod.GET)
    public CarSchedulePage getAllCompleted(@PathVariable Long companyId,
                                           @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return carScheduleService.readAllCompletedByCompanyId(companyId, page);
    }

    // Get all not completed by company id
    @RequestMapping(value = "/company/{companyId}/incomplete", method = RequestMethod.GET)
    public CarSchedulePage getAllIncomplete(@PathVariable Long companyId,
                                            @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return carScheduleService.readAllNotCompletedByCompanyId(companyId, page);
    }

}
