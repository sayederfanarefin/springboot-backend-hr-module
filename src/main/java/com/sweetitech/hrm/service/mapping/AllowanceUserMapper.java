package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.Allowance;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.AllowanceUserDTO;
import org.springframework.stereotype.Service;

@Service
public class AllowanceUserMapper {

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public AllowanceUserDTO map(User entity) throws Exception {
        if (entity == null) {
            return null;
        }

        AllowanceUserDTO dto = new AllowanceUserDTO();

        dto.setUserId(entity.getId());
        dto.setRole(entity.getRole());
        dto.setCompany(entity.getCompany());
        dto.setFirstName(entity.getBasicInfo().getFirstName());
        dto.setMiddleName(entity.getBasicInfo().getMiddleName());
        dto.setLastName(entity.getBasicInfo().getLastName());
        dto.setProfessionalInfo(entity.getProfessionalInfo());
        dto.setOfficeCode(entity.getOfficeCode());

        return dto;
    }

}
