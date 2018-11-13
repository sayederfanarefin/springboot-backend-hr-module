package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.relation.RolePrivilegeRelation;

import java.util.List;

public interface RolePrivilegeRelationService {

    RolePrivilegeRelation create(RolePrivilegeRelation relation);

    RolePrivilegeRelation remove(Long id) throws Exception;

    RolePrivilegeRelation read(Long id);

    List<RolePrivilegeRelation> readByRoleId(Long id);

    List<RolePrivilegeRelation> readByPrivilegeId(Long id);

}
