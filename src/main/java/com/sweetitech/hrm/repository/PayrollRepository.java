package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Payroll;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PayrollRepository extends CrudRepository<Payroll, Long> {

    Payroll getFirstByMonthAndYearAndPaidToUserId(Integer month, Integer year, Long userId);

    List<Payroll> findAllByYearAndPaidToUserIdOrderByMonthAsc(Integer year, Long userId);

    Payroll getFirstById(Long id);

}
