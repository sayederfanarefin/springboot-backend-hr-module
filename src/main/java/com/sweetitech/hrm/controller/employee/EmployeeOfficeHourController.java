package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.domain.OfficeHour;
import com.sweetitech.hrm.service.implementation.OfficeHourServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee/office-hours")
public class EmployeeOfficeHourController {

    private OfficeHourServiceImpl officeHourService;

    @Autowired
    public EmployeeOfficeHourController(OfficeHourServiceImpl officeHourService) {
        this.officeHourService = officeHourService;
    }

    // Get All
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<OfficeHour> getAll() throws Exception {
        return officeHourService.readAll();
    }
}
