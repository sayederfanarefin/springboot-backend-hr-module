package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.AllocatedLeaves;
import com.sweetitech.hrm.service.implementation.AllocatedLeavesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/super-admin/allocated-leaves")
public class SuperAdminAllocatedLeavesController {

    private AllocatedLeavesServiceImpl allocatedLeavesService;

    @Autowired
    public SuperAdminAllocatedLeavesController(AllocatedLeavesServiceImpl allocatedLeavesService) {
        this.allocatedLeavesService = allocatedLeavesService;
    }

    // Create Allocated Leave
    @RequestMapping(value = "", method = RequestMethod.POST)
    public AllocatedLeaves create(@RequestBody AllocatedLeaves allocatedLeaves) throws Exception {
        return allocatedLeavesService.create(allocatedLeaves);
    }

    // Edit Allocated Leave
    @RequestMapping(value = "/{leaveId}", method = RequestMethod.PUT)
    public AllocatedLeaves update(@PathVariable Long leaveId, @RequestBody AllocatedLeaves allocatedLeaves) throws Exception {
        if (leaveId == null) {
            throw new EntityNotFoundException("Null id found!");
        }

        return allocatedLeavesService.update(leaveId, allocatedLeaves);
    }

}
