package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Bonus;
import com.sweetitech.hrm.domain.dto.BonusResponseDTO;
import com.sweetitech.hrm.service.implementation.BonusServiceImpl;
import com.sweetitech.hrm.service.mapping.BonusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/bonuses")
public class EmployeeBonusController {

    private BonusServiceImpl bonusService;

    @Autowired
    private BonusMapper bonusMapper;

    @Autowired
    public EmployeeBonusController(BonusServiceImpl bonusService) {
        this.bonusService = bonusService;
    }

    // Get by id
    @RequestMapping(value = "/{bonusId}", method = RequestMethod.GET)
    public BonusResponseDTO getById(@PathVariable Long bonusId) throws Exception {
        return bonusService.read(bonusId);
    }

    // Get yearly by paid to user
    @RequestMapping(value = "/to/{userId}", method = RequestMethod.GET)
    public Iterable<Bonus> getByPaidToUser(@PathVariable Long userId,
                                           @RequestParam Integer year,
                                           @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (userId == null || year == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return bonusService.readAllByPaidToAndYear(userId, year, page);
    }
}
