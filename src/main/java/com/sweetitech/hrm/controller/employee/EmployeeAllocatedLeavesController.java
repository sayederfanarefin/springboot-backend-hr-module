package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.AllocatedLeaves;
import com.sweetitech.hrm.service.implementation.AllocatedLeavesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employee/allocated-leaves")
public class EmployeeAllocatedLeavesController {

    private AllocatedLeavesServiceImpl allocatedLeavesService;

    @Autowired
    public EmployeeAllocatedLeavesController(AllocatedLeavesServiceImpl allocatedLeavesService) {
        this.allocatedLeavesService = allocatedLeavesService;
    }

    @RequestMapping(value = "/{leaveId}", method = RequestMethod.GET)
    public AllocatedLeaves getById(@PathVariable Long leaveId) throws Exception {
        if (leaveId == null) {
            throw new EntityNotFoundException("Null id found!");
        }

        return allocatedLeavesService.read(leaveId);
    }

}
