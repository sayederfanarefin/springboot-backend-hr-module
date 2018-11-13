package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.relation.SalaryBreakdownRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SalaryBreakdownRelationRepository extends CrudRepository<SalaryBreakdownRelation, Long> {

    SalaryBreakdownRelation getFirstById(Long id);

    List<SalaryBreakdownRelation> findAllBySalaryIdAndIsDeletedFalse(Long salaryId);

    List<SalaryBreakdownRelation> findAllByBreakdownId(Long breakdownId);

}
