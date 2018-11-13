package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

    List<Department> findAllByCompanyIdAndIsDeletedFalseOrderByNameAsc(long id);

    Department findByIdAndIsDeletedFalse(long id);

    Department getFirstById(long id);

    Department getFirstByCompanyIdAndName(long id, String name);

    Department getFirstByNameAndCompanyIdAndIsDeletedFalse(String departmentName, Long companyId);

}
