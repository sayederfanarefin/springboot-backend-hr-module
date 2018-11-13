package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Commission;
import com.sweetitech.hrm.domain.dto.CommissionResponseDTO;
import com.sweetitech.hrm.service.implementation.CommissionServiceImpl;
import com.sweetitech.hrm.service.mapping.CommissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/commissions")
public class EmployeeCommissionController {

    private CommissionServiceImpl commissionService;

    @Autowired
    private CommissionMapper commissionMapper;

    @Autowired
    public EmployeeCommissionController(CommissionServiceImpl commissionService) {
        this.commissionService = commissionService;
    }

    // Get by id
    @RequestMapping(value = "/{bonusId}", method = RequestMethod.GET)
    public CommissionResponseDTO getById(@PathVariable Long bonusId) throws Exception {
        return commissionService.read(bonusId);
    }

    // Get yearly by paid to user
    @RequestMapping(value = "/to/{userId}", method = RequestMethod.GET)
    public Iterable<Commission> getByPaidToUser(@PathVariable Long userId,
                                                @RequestParam Integer year,
                                                @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (userId == null || year == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return commissionService.readAllByPaidToAndYear(userId, year, page);
    }
}
