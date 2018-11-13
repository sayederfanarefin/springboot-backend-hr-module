package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.relation.RolePrivilegeRelation;
import com.sweetitech.hrm.service.implementation.RolePrivilegeRelationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee/role_privilege")
public class EmployeeRolePrivilegeRelationController {

    private RolePrivilegeRelationServiceImpl relationService;

    @Autowired
    public EmployeeRolePrivilegeRelationController(RolePrivilegeRelationServiceImpl relationService) {
        this.relationService = relationService;
    }

    // Get Privileges By Role Id
    @RequestMapping(value = "/{roleId}", method = RequestMethod.GET)
    public List<RolePrivilegeRelation> getPrivileges(@PathVariable Long roleId) throws Exception {
        if (roleId == null) {
            throw new EntityNotFoundException("Null role id found!");
        }

        return relationService.readByRoleId(roleId);
    }
}
