package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.service.implementation.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee/companies")
public class EmployeeCompanyController {

    private CompanyServiceImpl companyService;

    @Autowired
    public EmployeeCompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    // Get companies as list
    @RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
    public List<Company> getAllCompanies(@PathVariable Long userId) throws  Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return companyService.readAllActiveByRole(userId);
    }
}
