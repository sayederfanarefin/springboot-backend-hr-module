package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.UserSmallResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class UserSmallResponseMapper {

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public UserSmallResponseDTO map(User entity) throws Exception {
        UserSmallResponseDTO dto = new UserSmallResponseDTO();

        dto.setUserId(entity.getId());

        if (entity.getBasicInfo().getMiddleName() != null) {
            dto.setName(entity.getBasicInfo().getFirstName() + " " +
                    "" + entity.getBasicInfo().getMiddleName() + " " +
                    "" + entity.getBasicInfo().getLastName());
        } else {
            dto.setName(entity.getBasicInfo().getFirstName() + " " +
                    "" + entity.getBasicInfo().getLastName());
        }

        dto.setCompanyId(entity.getCompany().getId());
        dto.setCompanyName(entity.getCompany().getName());

        if (entity.getProfessionalInfo() != null) {
            dto.setDesignation(entity.getProfessionalInfo().getDesignation());

            dto.setDepartmentId(entity.getProfessionalInfo().getDepartment().getId());
            dto.setDepartmentName(entity.getProfessionalInfo().getDepartment().getName());
        }

        if (entity.getGrade() != null) {
            dto.setGradeId(entity.getGrade().getId());
            dto.setGradeNumber(entity.getGrade().getGradeNumber());
            dto.setGradeName(entity.getGrade().getTitle());
        }

        if (entity.getOfficeCode() != null)
            dto.setOfficeCodeName(entity.getOfficeCode().getCode());

        return dto;
    }

}
