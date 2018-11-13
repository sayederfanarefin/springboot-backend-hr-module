package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.dto.CompanyDTO;
import com.sweetitech.hrm.service.implementation.CompanyServiceImpl;
import com.sweetitech.hrm.service.mapping.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/super-admin/companies")
public class SuperAdminCompanyController {

    private CompanyServiceImpl companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    public SuperAdminCompanyController(CompanyServiceImpl companyService) {
        this.companyService = companyService;
    }

    // Create Company
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Company createCompany(@RequestBody CompanyDTO dto) throws Exception {
        return companyService.create(companyMapper.map(dto));
    }

    // Edit Company
    @RequestMapping(value = "/{companyId}", method = RequestMethod.PUT)
    public Company updateCompany(@PathVariable Long companyId, @RequestBody CompanyDTO dto) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id provided!");
        }

        return companyService.update(companyId, companyMapper.map(dto));
    }

    // Soft Delete Company
    @RequestMapping(value = "/{companyId}", method = RequestMethod.DELETE)
    public void deleteCompany(@PathVariable Long companyId) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id provided!");
        }

        companyService.removeActiveState(companyId);
    }
}
