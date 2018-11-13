package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.service.implementation.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/companies")
public class AdminCompanyController {

    private CompanyServiceImpl companyService;

    @Autowired
    public AdminCompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    // Get companies
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Iterable<Company> getCompanies(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                          @PathVariable Long userId) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return companyService.readAllActive(userId, page);
    }

    // Get companies as list
    @RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
    public List<Company> getAllCompanies(@PathVariable Long userId) throws  Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return companyService.readAllActive(userId);
    }

    // Get company by id
    @RequestMapping(value = "/{companyId}/{userId}", method = RequestMethod.GET)
    public Company getCompanyById(@PathVariable Long companyId, @PathVariable Long userId) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("No company id provided!");
        }

        return companyService.read(companyId);
    }
}
