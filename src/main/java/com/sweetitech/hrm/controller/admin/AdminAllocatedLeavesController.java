package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.domain.AllocatedLeaves;
import com.sweetitech.hrm.service.implementation.AllocatedLeavesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/allocated-leaves")
public class AdminAllocatedLeavesController {

    private AllocatedLeavesServiceImpl allocatedLeavesService;

    @Autowired
    public AdminAllocatedLeavesController(AllocatedLeavesServiceImpl allocatedLeavesService) {
        this.allocatedLeavesService = allocatedLeavesService;
    }

    // Get All
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<AllocatedLeaves> getAll() throws Exception {
        return allocatedLeavesService.readAll();
    }

}
