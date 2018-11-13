package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Notification;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.NotificationDTO;
import com.sweetitech.hrm.domain.dto.NotificationResponseDTO;
import com.sweetitech.hrm.domain.dto.page.NotificationPage;
import com.sweetitech.hrm.repository.NotificationRepository;
import com.sweetitech.hrm.service.NotificationService;
import com.sweetitech.hrm.service.PushNotificationService;
import com.sweetitech.hrm.service.mapping.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationCountServiceImpl notificationCountService;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationResponseDTO create(Notification notification) throws Exception {
        Notification created = notificationRepository.save(notification);

        if (!created.isForAll())
            notificationCountService.increaseCount(created.getUser().getId());
        else {
            List<User> users = userService.readByCompanyId(created.getCompany().getId());

            if (users != null && users.size() > 0) {
                for (User user: users) {
                    notificationCountService.increaseCount(user.getId());
                }
            }
        }
        return notificationMapper.map(created);
    }

    @Override
    public NotificationPage getByUserId(Long userId, Integer page) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        Page<Notification> notifications = notificationRepository.findAllByUserIdOrForAllTrueAndCompanyIdOrderByCreatedOnDesc(user.getId(),
                user.getCompany().getId(), new PageRequest(page, PageAttr.PAGE_SIZE));

        List<NotificationResponseDTO> dtos = new ArrayList<>();
        if (notifications.getContent() != null && notifications.getContent().size() > 0) {
            for (Notification notification: notifications) {
                dtos.add(notificationMapper.map(notification));
            }
        }

        notificationCountService.clearCount(userId);
        return new NotificationPage(dtos, notifications);
    }

    @Override
    public NotificationResponseDTO read(Long id) throws Exception {
        Notification notification = notificationRepository.getFirstById(id);
        if (notification == null) {
            throw new EntityNotFoundException("No notifications with id: " + id);
        }

        return notificationMapper.map(notification);
    }

    @Override
    public void notifyUser(NotificationDTO dto) throws Exception {
        Notification notification = notificationMapper.map(dto);
        NotificationResponseDTO notificationResponseDTO;

        if (notification.isForAll()) {
            List<User> users = userService.readByCompanyId(dto.getCompanyId());
            if (users != null && users.size() > 0) {
                for (User user: users) {
                    notificationResponseDTO  = this.create(notification);

                    pushNotificationService.pushNotification(notificationResponseDTO, user.getId());
                }
            }
        } else {
            notificationResponseDTO  = this.create(notification);

            pushNotificationService.pushNotification(notificationResponseDTO, notificationResponseDTO.getUser().getUserId());
        }
    }

    @Override
    public Notification readEntity(Long id) {
        return notificationRepository.getFirstById(id);
    }

    @Override
    public void remove(Long id) throws Exception {
        Notification notification = this.readEntity(id);
        if (notification == null) {
            throw new EntityNotFoundException("No notifications with id: " + id);
        }

        notificationRepository.delete(notification);
    }

    @Override
    public Integer getWarningCount(Long userId, String month) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        List<Notification> notifications = notificationRepository.findAllByUserIdAndMonth(userId, month);
        if (notifications != null && notifications.size() > 0) {
            return notifications.size();
        }

        return 0;
    }
}
