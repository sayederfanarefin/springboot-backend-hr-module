package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Driver;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.DriverDTO;
import com.sweetitech.hrm.service.UserService;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverMapper {

    @Autowired
    private UserServiceImpl userService;

    /**
     *
     * @param dto
     * @return entity
     */
    public Driver map(DriverDTO dto) throws Exception {
        if (dto.getUserId() == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        User user = userService.read(dto.getUserId());
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + dto.getUserId());
        }

        Driver entity = new Driver();

        entity.setUser(user);

        if (dto.getLicenseNo() != null)
            entity.setLicenseNo(dto.getLicenseNo());
        if (dto.getLicenseExpiryDate() != null)
            entity.setLicenseExpiryDate(dto.getLicenseExpiryDate());

        return entity;
    }

}
