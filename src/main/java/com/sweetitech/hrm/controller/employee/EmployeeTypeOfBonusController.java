package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.TypeOfBonus;
import com.sweetitech.hrm.domain.dto.SingleStringObjectDTO;
import com.sweetitech.hrm.service.implementation.TypeOfBonusServiceImpl;
import com.sweetitech.hrm.service.mapping.TypeOfBonusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee/type-of-bonuses")
public class EmployeeTypeOfBonusController {

    private TypeOfBonusServiceImpl typeOfBonusService;

    @Autowired
    public EmployeeTypeOfBonusController(TypeOfBonusServiceImpl typeOfBonusService) {
        this.typeOfBonusService = typeOfBonusService;
    }

    // Get all
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<TypeOfBonus> getAll() throws Exception {
        return typeOfBonusService.readAll();
    }

    // Search by name
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<TypeOfBonus> searchByName(@RequestBody SingleStringObjectDTO dto) throws Exception {
        if (dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return typeOfBonusService.searchByName(dto.getObject());
    }
}
