package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Department;
import com.sweetitech.hrm.repository.DepartmentRepository;
import com.sweetitech.hrm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department create(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> readAllByCompanyId(long id) {
        return departmentRepository.findAllByCompanyIdAndIsDeletedFalseOrderByNameAsc(id);
    }

    @Override
    public Department read(long id) {
        return departmentRepository.getFirstById(id);
    }

    @Override
    public Department update(Long id, Department department) throws Exception {
        Department departmentOld = departmentRepository.getFirstById(id);
        if (departmentOld == null) {
            throw new EntityNotFoundException("No department with id: " + id);
        }

        department.setId(id);
        return departmentRepository.save(department);
    }

    @Override
    public void removeActiveState(Long id) throws Exception {
        Department department = this.read(id);
        if (department == null) {
            throw new EntityNotFoundException("No department with id: " + id);
        }

        department.setDeleted(true);
        departmentRepository.save(department);
    }

    @Override
    public boolean checkName(Long id, String name) throws Exception {
        if (companyService.read(id) == null) {
            throw new EntityNotFoundException("No company with id: " + id);
        }

        Department department = departmentRepository.getFirstByCompanyIdAndName(id, name);

        if (department == null) {
            return true;
        }
        return false;
    }

    @Override
    public Long getIdOfAccounts(Long companyId) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        Department department = departmentRepository.getFirstByNameAndCompanyIdAndIsDeletedFalse(Constants.ACCOUNTS, companyId);

        if (department != null)
            return department.getId();

        return (long) -1;
    }
}
