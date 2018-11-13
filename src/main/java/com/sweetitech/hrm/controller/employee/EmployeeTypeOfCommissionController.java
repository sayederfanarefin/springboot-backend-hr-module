package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.TypeOfCommission;
import com.sweetitech.hrm.domain.dto.SingleStringObjectDTO;
import com.sweetitech.hrm.service.implementation.TypeOfCommissionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee/type-of-commissions")
public class EmployeeTypeOfCommissionController {

    private TypeOfCommissionServiceImpl typeOfCommissionService;

    @Autowired
    public EmployeeTypeOfCommissionController(TypeOfCommissionServiceImpl typeOfCommissionService) {
        this.typeOfCommissionService = typeOfCommissionService;
    }

    // Get all
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<TypeOfCommission> getAll() throws Exception {
        return typeOfCommissionService.readAll();
    }

    // Search by name
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<TypeOfCommission> searchByName(@RequestBody SingleStringObjectDTO dto) throws Exception {
        if (dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return typeOfCommissionService.searchByName(dto.getObject());
    }
}
