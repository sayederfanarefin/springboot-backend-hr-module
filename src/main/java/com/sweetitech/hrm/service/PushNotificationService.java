package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Notification;
import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.NotificationResponseDTO;

import java.util.List;

public interface PushNotificationService {
     String sendPushTaskByUserIds(Task task, List<Long> users);
     String sendPushTaskByUsers(Task task, List<User> users);
     String sendPushTaskById(Long taskId, boolean toAll);

     String sendPushNotificationByUsers(NotificationResponseDTO notification, List<User> users) throws Exception;

     String pushNotification(NotificationResponseDTO notification, Long userId) throws Exception;

}
