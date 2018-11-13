package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.relation.RolePrivilegeRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RolePrivilegeRelationRepository extends CrudRepository<RolePrivilegeRelation, Long> {

    RolePrivilegeRelation getFirstByIdAndIsDeletedFalse(Long id);

    List<RolePrivilegeRelation> getAllByRoleIdAndIsDeletedFalse(Long id);

    List<RolePrivilegeRelation> getAllByPrivilegeIdAndIsDeletedFalse(Long id);

}
