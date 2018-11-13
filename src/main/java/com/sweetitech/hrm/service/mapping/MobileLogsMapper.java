package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.MobileLogs;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.MobileLogsDTO;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileLogsMapper {

    @Autowired
    private UserServiceImpl userService;

    /**
     *
     * @param dto
     * @return entity
     */
    public MobileLogs map(MobileLogsDTO dto, Long userId) throws Exception {
        if (dto.getLat() == null
                || dto.getLng() == null
                || dto.getTimestamp() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        MobileLogs entity = new MobileLogs();

        entity.setLat(dto.getLat());
        entity.setLng(dto.getLng());
        entity.setTimestamp(dto.getTimestamp());

        if (dto.getId() != null)
            entity.setEntityId(dto.getId());
        if (dto.getName() != null)
            entity.setName(dto.getName());
        if (dto.getPlace_id() != null)
            entity.setPlaceId(dto.getPlace_id());
        if (dto.getVicinity() != null)
            entity.setVicinity(dto.getVicinity());
        if (dto.getReference() != null)
            entity.setReference(dto.getReference());

        entity.setManualEntry(dto.isManual_entry());
        entity.setUser(user);

        return entity;
    }

}
