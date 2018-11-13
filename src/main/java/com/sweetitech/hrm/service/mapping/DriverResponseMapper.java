package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.Driver;
import com.sweetitech.hrm.domain.dto.DriverResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverResponseMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    /**
     *
     * @param entity
     * @return dto
     */
    public DriverResponseDTO map(Driver entity) throws Exception {
        DriverResponseDTO dto = new DriverResponseDTO();

        dto.setDriverId(entity.getId());
        dto.setUser(userSmallResponseMapper.map(entity.getUser()));
        dto.setLicenseNo(entity.getLicenseNo());
        dto.setLicenseExpiryDate(entity.getLicenseExpiryDate());

        return dto;
    }
}
