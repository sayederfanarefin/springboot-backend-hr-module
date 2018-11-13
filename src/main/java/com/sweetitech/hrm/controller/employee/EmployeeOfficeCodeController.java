package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.OfficeCode;
import com.sweetitech.hrm.service.implementation.OfficeCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/office-codes")
public class EmployeeOfficeCodeController {

    private OfficeCodeServiceImpl officeCodeService;

    @Autowired
    public EmployeeOfficeCodeController(OfficeCodeServiceImpl officeCodeService) {
        this.officeCodeService = officeCodeService;
    }

    // Get by id
    @RequestMapping(value = "/{officeCodeId}", method = RequestMethod.GET)
    public OfficeCode getById(@PathVariable Long officeCodeId) throws Exception {
        if (officeCodeId == null) {
            throw new EntityNotFoundException("Null office code id found!");
        }

        return officeCodeService.readById(officeCodeId);
    }

    // Get by Code
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public OfficeCode getByCode(@RequestParam String code) throws Exception {
        if (code == null) {
            throw new EntityNotFoundException("Null code found!");
        }

        return officeCodeService.readByCode(code);
    }
}
