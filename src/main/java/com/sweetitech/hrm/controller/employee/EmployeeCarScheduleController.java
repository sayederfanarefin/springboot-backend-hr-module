package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.CarScheduleResponseDTO;
import com.sweetitech.hrm.domain.dto.page.CarSchedulePage;
import com.sweetitech.hrm.service.implementation.CarScheduleServiceImpl;
import com.sweetitech.hrm.service.mapping.CarScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/car-schedules")
public class EmployeeCarScheduleController {

    private CarScheduleServiceImpl carScheduleService;

    @Autowired
    private CarScheduleMapper carScheduleMapper;

    @Autowired
    public EmployeeCarScheduleController(CarScheduleServiceImpl carScheduleService) {
        this.carScheduleService = carScheduleService;
    }

    // Get dto by schedule id
    @RequestMapping(value = "/dto/{scheduleId}", method = RequestMethod.GET)
    public CarScheduleResponseDTO getDTO(@PathVariable Long scheduleId) throws Exception {
        if (scheduleId == null) {
            throw new EntityNotFoundException("Null schedule id found!");
        }

        return carScheduleService.read(scheduleId);
    }

    // Get all by user id
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public CarSchedulePage getAllByUserId(@PathVariable Long userId,
                                          @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return carScheduleService.readAllByUserId(userId, page);
    }
}
