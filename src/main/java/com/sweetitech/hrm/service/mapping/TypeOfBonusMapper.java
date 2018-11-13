package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.TypeOfBonus;
import com.sweetitech.hrm.domain.dto.TypeOfBonusDTO;
import org.springframework.stereotype.Service;

@Service
public class TypeOfBonusMapper {

    /**
     *
     * @param dto
     * @return entity
     */
    public TypeOfBonus map(TypeOfBonusDTO dto) throws Exception {
        TypeOfBonus entity = new TypeOfBonus();

        if (dto.getName() == null) {
            throw new EntityNotFoundException("Null name found!");
        }

        entity.setName(dto.getName());

        return entity;
    }

}
