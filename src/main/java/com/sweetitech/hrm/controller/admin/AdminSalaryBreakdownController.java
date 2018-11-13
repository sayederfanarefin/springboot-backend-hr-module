package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.SalaryBreakdown;
import com.sweetitech.hrm.domain.dto.SalaryBreakdownListDTO;
import com.sweetitech.hrm.domain.relation.SalaryBreakdownRelation;
import com.sweetitech.hrm.service.implementation.SalaryBreakdownServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/salary-breakdown")
public class AdminSalaryBreakdownController {

    private SalaryBreakdownServiceImpl salaryBreakdownService;

    @Autowired
    public AdminSalaryBreakdownController(SalaryBreakdownServiceImpl salaryBreakdownService) {
        this.salaryBreakdownService = salaryBreakdownService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public SalaryBreakdown create(@RequestBody SalaryBreakdown salaryBreakdown) throws Exception {
        if (salaryBreakdown == null) {
            throw new EntityNotFoundException("Null object found!");
        }

        return salaryBreakdownService.create(salaryBreakdown);
    }

    @RequestMapping(value = "/{breakdownId}", method = RequestMethod.GET)
    public SalaryBreakdown getById(@PathVariable Long breakdownId) throws Exception {
        if (breakdownId == null) {
            throw new EntityNotFoundException("Null id found!");
        }

        return salaryBreakdownService.read(breakdownId);
    }

    @RequestMapping(value = "/bulk", method = RequestMethod.POST)
    public List<SalaryBreakdownRelation> bulkAddBreakdown(@RequestBody SalaryBreakdownListDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return salaryBreakdownService.bulkAddRelations(dto.getRelations());
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<SalaryBreakdown> getAll() throws Exception {
        return salaryBreakdownService.readAll();
    }
}
