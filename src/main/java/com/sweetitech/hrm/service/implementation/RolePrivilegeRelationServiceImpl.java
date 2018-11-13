package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.relation.RolePrivilegeRelation;
import com.sweetitech.hrm.repository.RolePrivilegeRelationRepository;
import com.sweetitech.hrm.service.RolePrivilegeRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePrivilegeRelationServiceImpl implements RolePrivilegeRelationService {

    private RolePrivilegeRelationRepository rolePrivilegeRelationRepository;

    @Autowired
    public RolePrivilegeRelationServiceImpl(RolePrivilegeRelationRepository rolePrivilegeRelationRepository) {
        this.rolePrivilegeRelationRepository = rolePrivilegeRelationRepository;
    }

    @Override
    public RolePrivilegeRelation create(RolePrivilegeRelation relation) {
        return rolePrivilegeRelationRepository.save(relation);
    }

    @Override
    public RolePrivilegeRelation remove(Long id) throws Exception {
        RolePrivilegeRelation relation = rolePrivilegeRelationRepository.getFirstByIdAndIsDeletedFalse(id);
        if (relation == null) {
            throw new EntityNotFoundException("No relation with id: " + id);
        }

        relation.setDeleted(true);
        return rolePrivilegeRelationRepository.save(relation);
    }

    @Override
    public RolePrivilegeRelation read(Long id) {
        return rolePrivilegeRelationRepository.getFirstByIdAndIsDeletedFalse(id);
    }

    @Override
    public List<RolePrivilegeRelation> readByRoleId(Long id) {
        return rolePrivilegeRelationRepository.getAllByRoleIdAndIsDeletedFalse(id);
    }

    @Override
    public List<RolePrivilegeRelation> readByPrivilegeId(Long id) {
        return rolePrivilegeRelationRepository.getAllByPrivilegeIdAndIsDeletedFalse(id);
    }
}
