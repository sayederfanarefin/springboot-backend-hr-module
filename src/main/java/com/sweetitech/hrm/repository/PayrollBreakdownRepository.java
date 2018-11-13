package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.PayrollBreakdown;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PayrollBreakdownRepository extends CrudRepository<PayrollBreakdown, Long> {

    List<PayrollBreakdown> findAllByPayrollId(Long payrollId);

}
