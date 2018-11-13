package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.MobileLogs;
import com.sweetitech.hrm.domain.dto.MobileLogsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileLogsResponseMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    /**
     *
     * @param entity
     * @return dto
     */
    public MobileLogsResponseDTO map(MobileLogs entity) throws Exception {
        MobileLogsResponseDTO dto = new MobileLogsResponseDTO();

        dto.setLogId(entity.getLogId());
        dto.setLat(entity.getLat());
        dto.setLng(entity.getLng());
        dto.setId(entity.getEntityId());
        dto.setManual_entry(entity.isManualEntry());
        dto.setName(entity.getName());
        dto.setPlace_id(entity.getPlaceId());
        dto.setTimestamp(entity.getTimestamp());
        dto.setVicinity(entity.getVicinity());
        dto.setReference(entity.getReference());

        dto.setUser(userSmallResponseMapper.map(entity.getUser()));

        return dto;
    }

}
