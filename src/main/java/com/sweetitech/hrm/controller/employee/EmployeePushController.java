package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.TaskComparator;
import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.dto.SingleStringObjectDTO;
import com.sweetitech.hrm.domain.dto.TaskDTO;
import com.sweetitech.hrm.domain.dto.TaskDtoPush;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;
import com.sweetitech.hrm.service.PushNotificationService;
import com.sweetitech.hrm.service.implementation.TaskServiceImpl;
import com.sweetitech.hrm.service.mapping.TaskMapper;
import com.sweetitech.hrm.service.mapping.TaskResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/employee/push")
public class EmployeePushController {

    private TaskServiceImpl taskService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskResponseMapper taskResponseMapper;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    public EmployeePushController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }


    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public String pushTask(@RequestParam Long taskId){

        return pushNotificationService.sendPushTaskById(taskId, false);
    }

    @RequestMapping(value = "/all/task", method = RequestMethod.POST)
    public String pushTaskAll(@RequestParam Long taskId){

        return pushNotificationService.sendPushTaskById(taskId, true);
    }
}
