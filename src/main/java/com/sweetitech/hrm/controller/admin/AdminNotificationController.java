package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.NotificationDTO;
import com.sweetitech.hrm.service.PushNotificationService;
import com.sweetitech.hrm.service.implementation.NotificationServiceImpl;
import com.sweetitech.hrm.service.mapping.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/notifications")
public class AdminNotificationController {

    private NotificationMapper notificationMapper;
    private NotificationServiceImpl notificationService;
    private PushNotificationService pushNotificationService;

    @Autowired
    public AdminNotificationController(NotificationMapper notificationMapper, NotificationServiceImpl notificationService, PushNotificationService pushNotificationService) {
        this.notificationMapper = notificationMapper;
        this.notificationService = notificationService;
        this.pushNotificationService = pushNotificationService;
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public void notifyUser(@RequestBody NotificationDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null object found!");
        }

        notificationService.notifyUser(dto);
    }

    @RequestMapping(value = "/{notificationId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long notificationId) throws Exception {
        if (notificationId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        notificationService.remove(notificationId);
    }
}
