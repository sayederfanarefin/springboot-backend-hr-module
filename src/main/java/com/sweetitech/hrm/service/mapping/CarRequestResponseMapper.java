package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.CarRequest;
import com.sweetitech.hrm.domain.dto.CarRequestResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarRequestResponseMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    /**
     *
     * @param entity
     * @return dto
     */
    public CarRequestResponseDTO map(CarRequest entity) throws Exception {
        CarRequestResponseDTO dto = new CarRequestResponseDTO();

        dto.setCarRequestId(entity.getId());
        dto.setDestination(entity.getDestination());
        dto.setFromHour(entity.getFromHour());
        dto.setPurpose(entity.getPurpose());
        dto.setRequestedByUser(userSmallResponseMapper.map(entity.getRequestedBy()));
        dto.setRequestedFrom(entity.getRequestedFrom());
        dto.setRequestedOn(entity.getRequestedOn());
        dto.setRequestedTo(entity.getRequestedTo());
        dto.setStatus(entity.getStatus());
        dto.setToHour(entity.getToHour());

        if (entity.isRoundTrip() != null)
            dto.setRoundTrip(entity.isRoundTrip());

        return dto;
    }

}
