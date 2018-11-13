package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.SalaryBreakdown;
import com.sweetitech.hrm.domain.dto.SalaryBreakdownCheckListDTO;
import com.sweetitech.hrm.domain.dto.SalaryBreakdownRelationDTO;
import com.sweetitech.hrm.domain.relation.SalaryBreakdownRelation;

import java.util.List;

public interface SalaryBreakdownService {

    SalaryBreakdown create(SalaryBreakdown salaryBreakdown) throws Exception;

    SalaryBreakdown read(Long id) throws Exception;

    SalaryBreakdown readByTitle(String title) throws Exception;

    List<SalaryBreakdownRelation> readAllBySalary(Long salaryId) throws Exception;

    void removeRelation(Long relationId) throws Exception;

    List<SalaryBreakdownRelation> bulkAddRelations(List<SalaryBreakdownRelationDTO> dtos) throws Exception;

    SalaryBreakdownCheckListDTO readAllRelationsBySalary(Long salaryId) throws Exception;

    SalaryBreakdownRelation createRelation(SalaryBreakdownRelation relation) throws Exception;

    List<SalaryBreakdown> readAll();

}
