package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.TypeOfBonus;
import com.sweetitech.hrm.domain.dto.TypeOfBonusDTO;
import com.sweetitech.hrm.service.implementation.TypeOfBonusServiceImpl;
import com.sweetitech.hrm.service.mapping.TypeOfBonusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/type-of-bonuses")
public class AdminTypeOfBonusController {

    private TypeOfBonusServiceImpl typeOfBonusService;

    @Autowired
    private TypeOfBonusMapper typeOfBonusMapper;

    @Autowired
    public AdminTypeOfBonusController(TypeOfBonusServiceImpl typeOfBonusService) {
        this.typeOfBonusService = typeOfBonusService;
    }

    // Create
    @RequestMapping(value = "", method = RequestMethod.POST)
    public TypeOfBonus create(@RequestBody TypeOfBonusDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null values found");
        }

        return typeOfBonusService.create(typeOfBonusMapper.map(dto));
    }

    // Update
    @RequestMapping(value = "/{typeId}", method = RequestMethod.PUT)
    public TypeOfBonus update(@PathVariable Long typeId, @RequestBody TypeOfBonusDTO dto) throws Exception {
        if (typeId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return typeOfBonusService.update(typeId, typeOfBonusMapper.map(dto));
    }

    // Delete
    @RequestMapping(value = "/{typeId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long typeId) throws Exception {
        if (typeId == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        typeOfBonusService.remove(typeId);
    }

    // Get by id
    @RequestMapping(value = "/{typeId}", method = RequestMethod.GET)
    public TypeOfBonus getById(@PathVariable Long typeId) throws Exception {
        if (typeId == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return typeOfBonusService.read(typeId);
    }
}
