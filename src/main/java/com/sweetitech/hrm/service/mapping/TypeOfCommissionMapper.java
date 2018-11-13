package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.TypeOfCommission;
import com.sweetitech.hrm.domain.dto.TypeOfCommissionDTO;
import org.springframework.stereotype.Service;

@Service
public class TypeOfCommissionMapper {

    /**
     *
     * @param dto
     * @return entity
     */
    public TypeOfCommission map(TypeOfCommissionDTO dto) throws Exception {
        TypeOfCommission entity = new TypeOfCommission();

        if (dto.getName() == null) {
            throw new EntityNotFoundException("Null name found!");
        }

        entity.setName(dto.getName());

        return entity;
    }

}
