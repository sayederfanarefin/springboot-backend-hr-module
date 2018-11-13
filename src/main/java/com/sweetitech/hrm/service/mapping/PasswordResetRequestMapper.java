package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.PasswordResetRequest;
import com.sweetitech.hrm.domain.dto.PasswordResetRequestDTO;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetRequestMapper {

    @Autowired
    private UserServiceImpl userService;

    /**
     *
     * @param dto
     * @return entity
     */
    public PasswordResetRequest map(PasswordResetRequestDTO dto) throws Exception {
        PasswordResetRequest entity = new PasswordResetRequest();

        entity.setUser(userService.read(dto.getUserId()));
        entity.setRequestedOn(dto.getRequestedOn());

        return entity;
    }

}
