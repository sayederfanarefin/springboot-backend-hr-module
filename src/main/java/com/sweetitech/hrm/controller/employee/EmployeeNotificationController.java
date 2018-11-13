package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.page.NotificationPage;
import com.sweetitech.hrm.service.implementation.NotificationCountServiceImpl;
import com.sweetitech.hrm.service.implementation.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/notifications")
public class EmployeeNotificationController {

    private NotificationServiceImpl notificationService;
    private NotificationCountServiceImpl notificationCountService;

    @Autowired
    public EmployeeNotificationController(NotificationServiceImpl notificationService,
                                          NotificationCountServiceImpl notificationCountService) {
        this.notificationService = notificationService;
        this.notificationCountService = notificationCountService;
    }

    @RequestMapping(value = "/count/{userId}", method = RequestMethod.GET)
    public Integer getCount(@PathVariable Long userId) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return notificationCountService.getCountForUser(userId);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public NotificationPage getByUser(@PathVariable Long userId,
                                      @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return notificationService.getByUserId(userId, page);
    }
}
