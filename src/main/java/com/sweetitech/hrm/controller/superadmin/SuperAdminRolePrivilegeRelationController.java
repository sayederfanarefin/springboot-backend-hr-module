package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.RolePrivilegeRelationDTO;
import com.sweetitech.hrm.domain.relation.RolePrivilegeRelation;
import com.sweetitech.hrm.service.implementation.PrivilegeServiceImpl;
import com.sweetitech.hrm.service.implementation.RolePrivilegeRelationServiceImpl;
import com.sweetitech.hrm.service.implementation.RoleServiceImpl;
import com.sweetitech.hrm.service.mapping.RolePrivilegeRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/super-admin/role_privilege")
public class SuperAdminRolePrivilegeRelationController {

    private RolePrivilegeRelationServiceImpl rolePrivilegeRelationService;

    @Autowired
    private RolePrivilegeRelationMapper rolePrivilegeRelationMapper;

    @Autowired
    public SuperAdminRolePrivilegeRelationController(RolePrivilegeRelationServiceImpl rolePrivilegeRelationService) {
        this.rolePrivilegeRelationService = rolePrivilegeRelationService;
    }

    // Assign Relation
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RolePrivilegeRelation assignRelation(@RequestBody RolePrivilegeRelationDTO dto) throws Exception {
        return rolePrivilegeRelationService.create(rolePrivilegeRelationMapper.map(dto));
    }

    // Destroy Relation
    @RequestMapping(value = "/{relationId}", method = RequestMethod.DELETE)
    public void destroyRelation(@PathVariable Long relationId) throws Exception {
        if (relationId == null) {
            throw new EntityNotFoundException("Null relation id found!");
        }

        rolePrivilegeRelationService.remove(relationId);
    }

    // Get Relation By Id
    @RequestMapping(value = "/{relationId}", method = RequestMethod.GET)
    public RolePrivilegeRelation getRelationById(@PathVariable Long relationId) throws Exception {
        if (relationId == null) {
            throw new EntityNotFoundException("Null relation id found!");
        }

        return rolePrivilegeRelationService.read(relationId);
    }

    // Get Relation By Role Id
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    public List<RolePrivilegeRelation> getByRoleId(@PathVariable Long roleId) throws Exception {
        if (roleId == null) {
            throw new EntityNotFoundException("Null relation id found!");
        }

        return rolePrivilegeRelationService.readByRoleId(roleId);
    }

    // Get Relation By Privilege Id
    @RequestMapping(value = "/privilege/{privilegeId}", method = RequestMethod.GET)
    public List<RolePrivilegeRelation> getByPrivilegeId(@PathVariable Long privilegeId) throws Exception {
        if (privilegeId == null) {
            throw new EntityNotFoundException("Null relation id found!");
        }

        return rolePrivilegeRelationService.readByPrivilegeId(privilegeId);
    }

}
