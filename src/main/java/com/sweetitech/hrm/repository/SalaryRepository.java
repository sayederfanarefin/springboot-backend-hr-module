package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Salary;
import org.springframework.data.repository.CrudRepository;

public interface SalaryRepository extends CrudRepository<Salary, Long> {

    Salary getFirstById(Long id);

}
