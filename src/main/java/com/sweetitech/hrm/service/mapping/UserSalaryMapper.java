package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.UserSalaryDTO;
import com.sweetitech.hrm.domain.relation.UserSalaryRelation;
import org.springframework.stereotype.Service;

@Service
public class UserSalaryMapper {

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public UserSalaryDTO map(UserSalaryRelation entity) throws Exception {
        if (entity == null) {
            throw new EntityNotFoundException("No salaries assigned for this user");
        }

        UserSalaryDTO dto = new UserSalaryDTO();

        dto.setUserId(entity.getUser().getId());
        dto.setMonth(entity.getMonth());
        dto.setYear(entity.getYear());
        dto.setSalary(entity.getSalary());

        return dto;
    }

}
