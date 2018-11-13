package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.Department;
import com.sweetitech.hrm.domain.dto.DepartmentDTO;
import com.sweetitech.hrm.service.implementation.CompanyServiceImpl;
import com.sweetitech.hrm.service.implementation.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentMapper {

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private CompanyServiceImpl companyService;

    /**
     * Maps DTO to Entity
     *
     * @param dto
     * @return entity
     */
    public Department map(DepartmentDTO dto) throws Exception {
        Company company = companyService.read(dto.getCompanyId());
        if (company == null) {
            throw new EntityNotFoundException("Company does not exist!");
        }

        Department department = new Department();
        department.setName(dto.getName());
        department.setCompany(company);

        return department;
    }

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public DepartmentDTO map(Department entity) throws Exception {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setName(entity.getName());
        dto.setCompanyId(entity.getCompany().getId());
        dto.setDeleted(entity.isDeleted());

        return dto;
    }

}
