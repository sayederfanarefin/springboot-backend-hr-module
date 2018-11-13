package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Salary;
import com.sweetitech.hrm.domain.SalaryBreakdown;
import com.sweetitech.hrm.domain.dto.SalaryBreakdownRelationDTO;
import com.sweetitech.hrm.domain.relation.SalaryBreakdownRelation;
import com.sweetitech.hrm.service.implementation.SalaryBreakdownServiceImpl;
import com.sweetitech.hrm.service.implementation.SalaryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryBreakdownRelationMapper {

    @Autowired
    private SalaryServiceImpl salaryService;

    @Autowired
    private SalaryBreakdownServiceImpl salaryBreakdownService;

    /**
     *
     * @param dto
     * @return entity
     */
    public SalaryBreakdownRelation map(SalaryBreakdownRelationDTO dto) throws Exception {

        Salary salary = salaryService.read(dto.getSalaryId());
        if (salary == null) {
            throw new EntityNotFoundException("No salaries with id: " + dto.getSalaryId());
        }

        SalaryBreakdown salaryBreakdown = salaryBreakdownService.read(dto.getBreakdownId());
        if (salaryBreakdown == null) {
            throw new EntityNotFoundException("No breakdowns with id: " + dto.getBreakdownId());
        }

        SalaryBreakdownRelation entity = new SalaryBreakdownRelation();
        entity.setSalary(salary);
        entity.setBreakdown(salaryBreakdown);

        return entity;
    }

}
