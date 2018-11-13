package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Notification;
import com.sweetitech.hrm.domain.dto.NotificationDTO;
import com.sweetitech.hrm.domain.dto.NotificationResponseDTO;
import com.sweetitech.hrm.domain.dto.page.NotificationPage;

public interface NotificationService {

    NotificationResponseDTO create(Notification notification) throws Exception;

    NotificationPage getByUserId(Long userId, Integer page) throws Exception;

    NotificationResponseDTO read(Long id) throws Exception;

    void notifyUser(NotificationDTO dto) throws Exception;

    Notification readEntity(Long id);

    void remove(Long id) throws Exception;

    Integer getWarningCount(Long userId, String month) throws Exception;

}
