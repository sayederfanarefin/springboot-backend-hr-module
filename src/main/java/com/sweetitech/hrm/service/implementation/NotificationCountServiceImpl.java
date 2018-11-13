package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.NotificationCount;
import com.sweetitech.hrm.repository.NotificationCountRepository;
import com.sweetitech.hrm.service.NotificationCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationCountServiceImpl implements NotificationCountService {

    private NotificationCountRepository notificationCountRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    public NotificationCountServiceImpl(NotificationCountRepository notificationCountRepository) {
        this.notificationCountRepository = notificationCountRepository;
    }

    @Override
    public NotificationCount readByUser(Long userId) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        return notificationCountRepository.getFirstByUserId(userId);
    }

    @Override
    public NotificationCount addCount(Long userId) {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        NotificationCount notificationCount = new NotificationCount();

        notificationCount.setUser(userService.read(userId));
        notificationCount.setCount(1);

        return notificationCountRepository.save(notificationCount);
    }

    @Override
    public void clearCount(Long userId) throws Exception {
        if (!this.checkIfExists(userId)) {
            return;
        }

        NotificationCount count = this.readByUser(userId);

        notificationCountRepository.delete(count);
    }

    @Override
    public Integer getCountForUser(Long userId) throws Exception {
        if (this.readByUser(userId) == null) {
            return 0;
        }

        return this.readByUser(userId).getCount();
    }

    @Override
    public NotificationCount updateCount(Long userId) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        if (this.readByUser(userId) == null) {
            return this.addCount(userId);
        }

        NotificationCount count = this.readByUser(userId);
        count.setCount(count.getCount() + 1);

        return notificationCountRepository.save(count);
    }

    @Override
    public NotificationCount increaseCount(Long userId) throws Exception {
        if (this.checkIfExists(userId)) {
            return this.updateCount(userId);
        }

        return this.addCount(userId);
    }

    @Override
    public boolean checkIfExists(Long userId) throws Exception {
        return this.readByUser(userId) != null;
    }
}
