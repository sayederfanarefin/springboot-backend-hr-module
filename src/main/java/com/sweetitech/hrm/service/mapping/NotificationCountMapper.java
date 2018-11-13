package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.NotificationCount;
import com.sweetitech.hrm.domain.dto.NotificationCountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationCountMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    /**
     *
     * @param entity
     * @return dto
     */
    public NotificationCountDTO map(NotificationCount entity) throws Exception {
        NotificationCountDTO dto = new NotificationCountDTO();

        dto.setCountId(entity.getId());
        dto.setUser(userSmallResponseMapper.map(entity.getUser()));
        dto.setCount(entity.getCount());

        return dto;
    }

}
