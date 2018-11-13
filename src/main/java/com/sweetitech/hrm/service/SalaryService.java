package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Salary;

public interface SalaryService {

    Salary create(Salary salary);

    Salary update(Long id, Salary salary) throws Exception;

    Salary read(Long id) throws Exception;

}
