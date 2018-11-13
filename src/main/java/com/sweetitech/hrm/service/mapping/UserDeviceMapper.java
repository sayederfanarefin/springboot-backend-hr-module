package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.DeviceLocation;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.UserInfo;
import com.sweetitech.hrm.domain.dto.UserDeviceDTO;
import com.sweetitech.hrm.domain.relation.UserDeviceRelation;
import com.sweetitech.hrm.service.implementation.DeviceLocationServiceImpl;
import com.sweetitech.hrm.service.implementation.UserDeviceServiceImpl;
import com.sweetitech.hrm.service.implementation.UserInfoServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDeviceMapper {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DeviceLocationServiceImpl deviceLocationService;

    @Autowired
    private UserDeviceServiceImpl userDeviceService;

    @Autowired
    private UserInfoServiceImpl userInfoService;

    /**
     *
     * @param dto
     * @return entity
     */
    public UserDeviceRelation map(UserDeviceDTO dto) throws Exception {
        User user = userService.read(dto.getUserId());
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + dto.getUserId());
        }

        DeviceLocation location = deviceLocationService.read(dto.getDeviceLocationId());
        if (location == null) {
            throw new EntityNotFoundException("No location with id: " + dto.getDeviceLocationId());
        }

        UserDeviceRelation entity = new UserDeviceRelation();
        entity.setUser(user);
        entity.setLocation(location);
        entity.setEnrollmentNumber(dto.getUserInfoEnrollNumber());

        return entity;
    }

}
