package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.TypeOfCommission;
import com.sweetitech.hrm.domain.dto.SingleStringObjectDTO;
import com.sweetitech.hrm.domain.dto.TypeOfCommissionDTO;
import com.sweetitech.hrm.service.implementation.TypeOfCommissionServiceImpl;
import com.sweetitech.hrm.service.mapping.TypeOfCommissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/type-of-commissions")
public class AdminTypeOfCommissionController {

    private TypeOfCommissionServiceImpl typeOfCommissionService;

    @Autowired
    private TypeOfCommissionMapper typeOfCommissionMapper;

    @Autowired
    public AdminTypeOfCommissionController(TypeOfCommissionServiceImpl typeOfCommissionService) {
        this.typeOfCommissionService = typeOfCommissionService;
    }

    // Create
    @RequestMapping(value = "", method = RequestMethod.POST)
    public TypeOfCommission create(@RequestBody TypeOfCommissionDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null values found");
        }

        return typeOfCommissionService.create(typeOfCommissionMapper.map(dto));
    }

    // Update
    @RequestMapping(value = "/{typeId}", method = RequestMethod.PUT)
    public TypeOfCommission update(@PathVariable Long typeId, @RequestBody TypeOfCommissionDTO dto) throws Exception {
        if (typeId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return typeOfCommissionService.update(typeId, typeOfCommissionMapper.map(dto));
    }

    // Delete
    @RequestMapping(value = "/{typeId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long typeId) throws Exception {
        if (typeId == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        typeOfCommissionService.remove(typeId);
    }

    // Get by id
    @RequestMapping(value = "/{typeId}", method = RequestMethod.GET)
    public TypeOfCommission getById(@PathVariable Long typeId) throws Exception {
        if (typeId == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return typeOfCommissionService.read(typeId);
    }
}
