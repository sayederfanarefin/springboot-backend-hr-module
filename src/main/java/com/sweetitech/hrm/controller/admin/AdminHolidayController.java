package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.dto.TaskDTO;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;
import com.sweetitech.hrm.service.implementation.HolidayServiceImpl;
import com.sweetitech.hrm.service.mapping.HolidayMapper;
import com.sweetitech.hrm.service.mapping.TaskResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/holidays")
public class AdminHolidayController {

    private HolidayServiceImpl holidayService;

    @Autowired
    private HolidayMapper holidayMapper;

    @Autowired
    private TaskResponseMapper taskResponseMapper;

    @Autowired
    public AdminHolidayController(HolidayServiceImpl holidayService) {
        this.holidayService = holidayService;
    }

    // Create Holiday
    @RequestMapping(value = "", method = RequestMethod.POST)
    public List<TaskResponseDTO> createHoliday(@RequestBody TaskDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null DTO found!");
        }

        List<Task> tasks = holidayMapper.map(dto);
        List<TaskResponseDTO> dtos = new ArrayList<>();

        for (Task task: tasks) {
            dtos.add(taskResponseMapper.map(holidayService.create(task)));
        }

        return dtos;
    }

    // Cancel Holiday
    @RequestMapping(value = "/cancel/{taskId}", method = RequestMethod.PUT)
    public TaskResponseDTO cancelHoliday(@PathVariable Long taskId) throws Exception {
        if (taskId == null) {
            throw new EntityNotFoundException("Null holiday id found!");
        }

        return taskResponseMapper.map(holidayService.cancel(taskId));
    }

    // Update Holiday
    @RequestMapping(value = "/{taskId}", method = RequestMethod.PUT)
    public TaskResponseDTO updateHoliday(@PathVariable Long taskId, @RequestBody TaskDTO dto) throws Exception {
        if (taskId == null) {
            throw new EntityNotFoundException("Null holiday id found!");
        }

        return taskResponseMapper.map(holidayService.update(taskId, dto));
    }
}
