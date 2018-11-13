package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Privilege;
import com.sweetitech.hrm.domain.Role;
import com.sweetitech.hrm.domain.dto.RolePrivilegeRelationDTO;
import com.sweetitech.hrm.domain.relation.RolePrivilegeRelation;
import com.sweetitech.hrm.service.implementation.PrivilegeServiceImpl;
import com.sweetitech.hrm.service.implementation.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolePrivilegeRelationMapper {

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private PrivilegeServiceImpl privilegeService;

    /**
     * Maps DTO to Entity
     *
     * @param dto
     * @return entity
     */
    public RolePrivilegeRelation map(RolePrivilegeRelationDTO dto) throws Exception {
        Role role = roleService.findRoleById(dto.getRoleId());
        if (role == null) {
            throw new EntityNotFoundException("No role found with id: " + dto.getRoleId());
        }

        Privilege privilege = privilegeService.read(dto.getPrivilegeId());
        if (privilege == null) {
            throw new EntityNotFoundException("No privilege found with id: " + dto.getPrivilegeId());
        }

        RolePrivilegeRelation relation = new RolePrivilegeRelation();
        relation.setRole(role);
        relation.setPrivilege(privilege);

        return relation;
    }

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public RolePrivilegeRelationDTO map(RolePrivilegeRelation entity) throws Exception {
        RolePrivilegeRelationDTO dto = new RolePrivilegeRelationDTO();

        dto.setRoleId(entity.getRole().getId());
        dto.setPrivilegeId(entity.getPrivilege().getId());

        return dto;
    }

}
