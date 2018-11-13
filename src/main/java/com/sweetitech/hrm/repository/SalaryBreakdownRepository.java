package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.SalaryBreakdown;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SalaryBreakdownRepository extends CrudRepository<SalaryBreakdown, Long> {

    SalaryBreakdown getFirstById(Long id);

    SalaryBreakdown getFirstByTitle(String title);

    List<SalaryBreakdown> findAllByOrderByTitleAsc();

}
