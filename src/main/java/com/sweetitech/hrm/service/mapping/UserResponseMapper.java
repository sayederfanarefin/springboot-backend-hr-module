package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.UserResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class UserResponseMapper {

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public UserResponseDTO map(User entity) throws Exception {
        if (entity == null) {
            return null;
        }

        UserResponseDTO dto = new UserResponseDTO();

        dto.setUserId(entity.getId());
        dto.setRole(entity.getRole());
        dto.setCompany(entity.getCompany());
        dto.setFirstName(entity.getBasicInfo().getFirstName());
        dto.setMiddleName(entity.getBasicInfo().getMiddleName());
        dto.setLastName(entity.getBasicInfo().getLastName());
        dto.setProfessionalInfo(entity.getProfessionalInfo());

        if (entity.getOfficeCode() != null)
            dto.setOfficeCode(entity.getOfficeCode());

        return dto;
    }

}
