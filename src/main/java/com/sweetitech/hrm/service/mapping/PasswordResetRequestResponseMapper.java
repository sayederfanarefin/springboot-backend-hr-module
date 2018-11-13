package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.PasswordResetRequest;
import com.sweetitech.hrm.domain.dto.PasswordResetRequestResponseDTO;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetRequestResponseMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    private UserServiceImpl userService;

    /**
     *
     * @param entity
     * @return dto
     */
    public PasswordResetRequestResponseDTO map(PasswordResetRequest entity) throws Exception {
        PasswordResetRequestResponseDTO dto = new PasswordResetRequestResponseDTO();

        dto.setRequestId(entity.getId());
        dto.setUserSmallResponseDTO(userSmallResponseMapper.map(entity.getUser()));
        dto.setCompany(entity.getUser().getCompany());
        dto.setRequestedOn(entity.getRequestedOn());

        return dto;
    }

    /**
     *
     * @param dto
     * @return entity
     */
    public PasswordResetRequest map(PasswordResetRequestResponseDTO dto) throws Exception {
        PasswordResetRequest entity = new PasswordResetRequest();

        entity.setUser(userService.read(dto.getUserSmallResponseDTO().getUserId()));
        entity.setRequestedOn(dto.getRequestedOn());

        return entity;
    }

}
