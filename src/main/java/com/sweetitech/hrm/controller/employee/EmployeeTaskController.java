package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.TaskComparator;
import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.service.PushNotificationService;
import com.sweetitech.hrm.service.implementation.TaskServiceImpl;
import com.sweetitech.hrm.service.mapping.TaskMapper;
import com.sweetitech.hrm.service.mapping.TaskResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/employee/tasks")
public class EmployeeTaskController {

    private TaskServiceImpl taskService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskResponseMapper taskResponseMapper;


    @Autowired
    public EmployeeTaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    // Create
    @RequestMapping(value = "", method = RequestMethod.POST)
    public List<TaskResponseDTO> create(@RequestBody TaskDTO dto) throws Exception {
        if (dto == null || dto.getAssignedToUserIds() == null) {
            throw new EntityNotFoundException("Invalid values found!");
        }

        List<Task> tasks = taskMapper.map(dto);
        List<Long> users = dto.getAssignedToUserIds();

        List<TaskResponseDTO> dtos = new ArrayList<>();

        if (tasks != null && tasks.size() > 0) {
            if (users.size() > 0) {
                for (Task task: tasks) {
                    dtos.add(taskResponseMapper.map(taskService.create(task, users)));
                }
            } else {
                for (Task task: tasks) {
                    dtos.add(taskResponseMapper.map(taskService.create(task, null)));
                }
            }
        }

        return dtos;
    }

    // Dashboard
    @RequestMapping(value = "/dashboard/{userId}", method = RequestMethod.GET)
    public List<TaskResponseDTO> getAllTasksByUserId(@PathVariable Long userId,
                                                     @RequestParam Integer month,
                                                     @RequestParam Integer year) throws Exception {
        if (userId == null || month == null || year == null) {
            throw new EntityNotFoundException("Invalid data found!");
        }

        List<TaskResponseDTO> dtos = new ArrayList<>();

        List<Task> tasks = taskService.readAllByUserId(userId, month, year);
        if (tasks != null && tasks.size() > 0) {
            Collections.sort(tasks, new TaskComparator());

            for (Task task: tasks) {
                dtos.add(taskResponseMapper.map(task));
            }
        }

        return dtos;
    }

    // Update
    @RequestMapping(value = "/{taskId}", method = RequestMethod.PUT)
    public TaskResponseDTO update(@PathVariable Long taskId, @RequestBody TaskDTO dto) throws Exception {
        if (taskId == null || dto == null || dto.getAssignedToUserIds() == null) {
            throw new EntityNotFoundException("Invalid values found!");
        }

        Task task = taskMapper.map(dto).get(0);
        List<Long> users = dto.getAssignedToUserIds();

        if (users.size() > 0) {
            return taskResponseMapper.map(taskService.update(taskId, task, users));
        } else {
            return taskResponseMapper.map(taskService.update(taskId, task, null));
        }
    }

    // Cancel
    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    public void cancel(@PathVariable Long taskId) throws Exception {
        if (taskId == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        taskService.cancel(taskId);
    }

    // Update Status
    @RequestMapping(value = "/status/{taskId}", method = RequestMethod.PUT)
    public TaskResponseDTO updateStatus(@PathVariable Long taskId, @RequestBody SingleStringObjectDTO dto) throws Exception {
        if (taskId == null || dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return taskResponseMapper.map(taskService.updateStatus(taskId, dto.getObject()));
    }

    // Get by id
    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public TaskUserResponseDTO getById(@PathVariable Long taskId) throws Exception {
        if (taskId == null) {
            throw new EntityNotFoundException("Null task id found!");
        }

        return taskService.read(taskId);
    }

    // Get all tasks assigned to user by month and year
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public List<TaskResponseDTO> getAllAssignedToUserForMonth(@PathVariable Long userId,
                                                              @RequestParam Integer month,
                                                              @RequestParam Integer year) throws Exception {
        if (userId == null || month == null || year == null) {
            throw new EntityNotFoundException("Invalid data found!");
        }

        return taskService.readAllAssignedToUser(userId, month, year);
    }

    // Get all tasks assigned by me sorted by month and year
    @RequestMapping(value = "/user/from/list/{userId}", method = RequestMethod.GET)
    public List<TaskResponseDTO> getAllAssignedByMe(@PathVariable Long userId,
                                                    @RequestParam Integer month,
                                                    @RequestParam Integer year) throws Exception {
        if (userId == null || month == null || year == null) {
            throw new EntityNotFoundException("Invalid data found!");
        }

        return taskService.readAllAssignedByUser(userId, month, year);
    }

    // Get all tasks assigned by me sorted by month and year
    @RequestMapping(value = "/user/from/{userId}", method = RequestMethod.GET)
    public Iterable<Task> getAllAssignedByMe(@PathVariable Long userId,
                                                        @RequestParam Integer month,
                                                        @RequestParam Integer year,
                                                        @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (userId == null || month == null || year == null) {
            throw new EntityNotFoundException("Invalid data found!");
        }

        return taskService.readAllAssignedByUser(userId, month, year, page);
    }

}
