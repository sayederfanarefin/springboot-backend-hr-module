package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.NotificationCount;

public interface NotificationCountService {

    NotificationCount readByUser(Long userId) throws Exception;

    NotificationCount addCount(Long userId);

    void clearCount(Long userId) throws Exception;

    Integer getCountForUser(Long userId) throws Exception;

    NotificationCount updateCount(Long userId) throws Exception;

    NotificationCount increaseCount(Long userId) throws Exception;

    boolean checkIfExists(Long userId) throws Exception;

}
