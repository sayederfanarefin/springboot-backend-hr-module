package com.sweetitech.hrm.service.implementation;

import com.pusher.rest.Pusher;
import com.pusher.rest.data.Result;
//import com.squareup.okhttp.*;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Notification;
import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.NotificationResponseDTO;
import com.sweetitech.hrm.domain.dto.TaskDtoPush;
import com.sweetitech.hrm.repository.TaskRepository;
import com.sweetitech.hrm.service.PushNotificationService;
import com.sweetitech.hrm.service.mapping.NotificationMapper;
import com.sweetitech.hrm.service.mapping.TaskPushMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PushNotificationImpl implements PushNotificationService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskPushMapper taskPushMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    private Pusher initiatePusher() {
        // Pusher pusher = new Pusher("570415", "e7cfefd6b3c117fd66da", "8a7c508b87ec4f914bca");
        Pusher pusher = new Pusher("621220", "f9cd9e6f5353bbcfc529", "6a0c243ae69dceec5521");
        pusher.setCluster("ap1");
        pusher.setEncrypted(true);

        return pusher;
    }

    @Override
    public String sendPushTaskByUserIds(Task task, List<Long> users) {

        Pusher pusher = this.initiatePusher();
        String result = "Nothing";

        try {
            for (Long user : users) {
                Result trigger = pusher.trigger(String.valueOf(user) + "-channel", String.valueOf(user) + "-event", taskPushMapper.map(task));
                if (!trigger.getHttpStatus().equals(200)) {
                    result = "Some push failed";
                }
            }
        }catch(Exception e){
            result = e.getMessage();
        }

        return result;
    }

    @Override
    public String sendPushTaskByUsers(Task task, List<User> users) {
        List<Long> userIds = new ArrayList<>();

        for (User user : users) {
            userIds.add(user.getId());
        }
        return sendPushTaskByUserIds(task, userIds);
    }

    @Override
    public String sendPushTaskById(Long taskId, boolean toAll) {

        Task task = taskRepository.findById(taskId).get();
        try {
            List<User> users = userService.readByCompanyId(task.getCompany().getId());
            return sendPushTaskByUsers(task, users);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException("Failed to generate notification");
        }

    }

    @Override
    public String sendPushNotificationByUsers(NotificationResponseDTO notification, List<User> users) throws Exception {
        String result = "Nothing";

        try {
            for (User user : users) {
                result = this.pushNotification(notification, user.getId());
            }
        }catch(Exception e){
            result = e.getMessage();
        }

        return result;
    }

    @Override
    public String pushNotification(NotificationResponseDTO notification, Long userId) throws Exception {
        Pusher pusher = this.initiatePusher();
        String result = "Nothing";

        Result trigger = pusher.trigger(String.valueOf(userId) + "-channel", String.valueOf(userId) + "-event", notification);

        if (!trigger.getHttpStatus().equals(200)) {
            result = "Push failed for user id: " + userId;
        }

        return result;
    }
}
