package com.sweetitech.hrm.controller.all;

import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.dto.CompanyDTO;
import com.sweetitech.hrm.service.implementation.CompanyServiceImpl;
import com.sweetitech.hrm.service.mapping.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/all/companies")
public class CompanyController {

    private CompanyServiceImpl companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    public CompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    // Create Company
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Company createCompany(@RequestBody CompanyDTO dto) throws Exception {
        return companyService.create(companyMapper.map(dto));
    }

    // Get companies
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Company> getCompanies(@RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        return companyService.readAll(page);
    }

    // Get companies as list
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Company> getAllCompanies() throws  Exception {
        return companyService.readAllActive();
    }

}
