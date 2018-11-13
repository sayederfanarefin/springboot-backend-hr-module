package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.Privilege;
import com.sweetitech.hrm.domain.dto.PrivilegeDTO;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeMapper {

    /**
     * Maps DTO to Entity
     *
     * @param dto
     * @return entity
     */
    public Privilege map(PrivilegeDTO dto) throws Exception {
        Privilege privilege = new Privilege();

        privilege.setFeature(dto.getFeature());
        privilege.setModule(dto.getModule());

        return privilege;
    }

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public PrivilegeDTO map(Privilege entity) throws Exception {
        PrivilegeDTO dto = new PrivilegeDTO();

        dto.setFeature(entity.getFeature());
        dto.setModule(entity.getModule());

        return dto;
    }
}
