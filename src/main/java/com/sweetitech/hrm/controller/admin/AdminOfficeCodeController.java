package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.OfficeCode;
import com.sweetitech.hrm.service.implementation.OfficeCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/office-codes")
public class AdminOfficeCodeController {

    private OfficeCodeServiceImpl officeCodeService;

    @Autowired
    public AdminOfficeCodeController(OfficeCodeServiceImpl officeCodeService) {
        this.officeCodeService = officeCodeService;
    }

    // Get All
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<OfficeCode> getAll() throws Exception {
        return officeCodeService.readAll();
    }

    // Get by company id
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public List<OfficeCode> getByCompanyId(@PathVariable Long companyId) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return officeCodeService.readByCompanyId(companyId);
    }
}
