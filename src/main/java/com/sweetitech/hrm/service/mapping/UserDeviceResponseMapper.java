package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.dto.UserDeviceResponseDTO;
import com.sweetitech.hrm.domain.relation.UserDeviceRelation;
import com.sweetitech.hrm.service.implementation.DeviceLocationServiceImpl;
import com.sweetitech.hrm.service.implementation.UserInfoServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDeviceResponseMapper {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    private UserInfoServiceImpl userInfoService;

    @Autowired
    private DeviceLocationServiceImpl deviceLocationService;

    /**
     *
     * @param entity
     * @return dto
     */
    public UserDeviceResponseDTO map(UserDeviceRelation entity) throws Exception {
        UserDeviceResponseDTO dto = new UserDeviceResponseDTO();

        dto.setRelationId(entity.getId());

        if (entity.getUser() != null)
            dto.setUserSmallResponseDTO(userSmallResponseMapper.map(entity.getUser()));

        if (entity.getLocation() != null) {
            dto.setLocation(entity.getLocation());
        }

        if (entity.getEnrollmentNumber() != null) {
            dto.setEnrollmentNumber(entity.getEnrollmentNumber());
        }

        return dto;
    }

}
