package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;
import com.sweetitech.hrm.service.implementation.HolidayServiceImpl;
import com.sweetitech.hrm.service.mapping.HolidayMapper;
import com.sweetitech.hrm.service.mapping.TaskResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee/holidays")
public class EmployeeHolidayController {

    private HolidayServiceImpl holidayService;

    @Autowired
    private HolidayMapper holidayMapper;

    @Autowired
    private TaskResponseMapper taskResponseMapper;

    @Autowired
    public EmployeeHolidayController(HolidayServiceImpl holidayService) {
        this.holidayService = holidayService;
    }

    // Get Holiday By Id
    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public Task getEntity(@PathVariable Long taskId) throws Exception {
        if (taskId == null) {
            throw new EntityNotFoundException("Null holiday id found!");
        }

        return holidayService.read(taskId);
    }

    // Get DTO by Id
    @RequestMapping(value = "/dto/{taskId}", method = RequestMethod.GET)
    public TaskResponseDTO getDTO(@PathVariable Long taskId) throws Exception {
        if (taskId == null) {
            throw new EntityNotFoundException("Null holiday id found!");
        }

        return taskResponseMapper.map(holidayService.read(taskId));
    }

    // Get All by Year and Description
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public Iterable<Task> searchByDescription(@RequestParam Integer year,
                                              @RequestParam String description,
                                              @RequestParam Long companyId,
                                              @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (year == null || description == null || companyId == null) {
            throw new EntityNotFoundException("Null parameters found!");
        }

        return holidayService.readByDesc(description, year, companyId, page);
    }

    // Get All by Month and Year
    @RequestMapping(value = "/month/{month}/year/{year}", method = RequestMethod.GET)
    public List<TaskResponseDTO> getAllByMonthAndYear(@PathVariable Integer month,
                                                      @PathVariable Integer year,
                                                      @RequestParam Long companyId) throws Exception {
        if (month == null || year == null) {
            throw new EntityNotFoundException("Null month or year found!");
        }

        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        List<Task> tasks = holidayService.readByMonthAndYear(month, year, companyId);
        List<TaskResponseDTO> dtos = new ArrayList<>();

        for (Task task: tasks) {
            dtos.add(taskResponseMapper.map(task));
        }

        return dtos;
    }
}
