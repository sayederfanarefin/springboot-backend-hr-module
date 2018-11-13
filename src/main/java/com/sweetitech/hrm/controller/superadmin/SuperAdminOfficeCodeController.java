package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.OfficeCode;
import com.sweetitech.hrm.domain.dto.OfficeCodeDTO;
import com.sweetitech.hrm.service.implementation.OfficeCodeServiceImpl;
import com.sweetitech.hrm.service.mapping.OfficeCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("super-admin/office-codes")
public class SuperAdminOfficeCodeController {

    private OfficeCodeServiceImpl officeCodeService;

    @Autowired
    private OfficeCodeMapper officeCodeMapper;

    @Autowired
    public SuperAdminOfficeCodeController(OfficeCodeServiceImpl officeCodeService) {
        this.officeCodeService = officeCodeService;
    }

    // Create
    @RequestMapping(value = "", method = RequestMethod.POST)
    public OfficeCode create(@RequestBody OfficeCodeDTO dto) throws Exception {
        return  officeCodeService.create(officeCodeMapper.map(dto));
    }

    // Update
    @RequestMapping(value = "/{officeCodeId}", method = RequestMethod.PUT)
    public OfficeCode update(@PathVariable Long officeCodeId, @RequestBody OfficeCodeDTO dto) throws Exception {
        if (officeCodeId == null) {
            throw new EntityNotFoundException("Null office code id found!");
        }

        return officeCodeService.update(officeCodeId, officeCodeMapper.map(dto));
    }

    // Delete
    @RequestMapping(value = "/{officeCodeId}", method = RequestMethod.DELETE)
    public void remove(@PathVariable Long officeCodeId) throws Exception {
        if (officeCodeId == null) {
            throw new EntityNotFoundException("Null office code id found!");
        }

        officeCodeService.remove(officeCodeId);
    }

    // Check unique
    @RequestMapping(value = "/check/{companyId}", method = RequestMethod.GET)
    public boolean check(@PathVariable Long companyId, @RequestParam String code) throws Exception {
        if (companyId == null || code == null) {
            throw new EntityNotFoundException("Null code value found!");
        }

        return officeCodeService.exists(companyId, code);
    }

}
