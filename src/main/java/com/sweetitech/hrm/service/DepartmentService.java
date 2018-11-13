package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Department;

import java.util.List;

public interface DepartmentService {

    Department create(Department department);

    List<Department> readAllByCompanyId(long id);

    Department read(long id);

    Department update(Long id, Department department) throws Exception;

    void removeActiveState(Long id) throws Exception;

    boolean checkName(Long id, String name) throws Exception;

    Long getIdOfAccounts(Long companyId) throws Exception;

}
