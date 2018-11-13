package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.SalaryBreakdownCheckListDTO;
import com.sweetitech.hrm.domain.relation.SalaryBreakdownRelation;
import com.sweetitech.hrm.service.implementation.SalaryBreakdownServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee/salary-breakdown")
public class EmployeeSalaryBreakdownController {

    private SalaryBreakdownServiceImpl salaryBreakdownService;

    @Autowired
    public EmployeeSalaryBreakdownController(SalaryBreakdownServiceImpl salaryBreakdownService) {
        this.salaryBreakdownService = salaryBreakdownService;
    }

    @RequestMapping(value = "/salary/{salaryId}", method = RequestMethod.GET)
    public SalaryBreakdownCheckListDTO getAllRelationsBySalary(@PathVariable Long salaryId) throws Exception {
        if (salaryId == null) {
            throw new EntityNotFoundException("Null id found!");
        }

        return salaryBreakdownService.readAllRelationsBySalary(salaryId);
    }

}
