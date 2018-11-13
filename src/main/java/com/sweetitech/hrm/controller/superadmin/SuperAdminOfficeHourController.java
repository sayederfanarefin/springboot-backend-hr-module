package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.OfficeHour;
import com.sweetitech.hrm.service.implementation.OfficeHourServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/super-admin/office-hours")
public class SuperAdminOfficeHourController {

    private OfficeHourServiceImpl officeHourService;

    @Autowired
    public SuperAdminOfficeHourController(OfficeHourServiceImpl officeHourService) {
        this.officeHourService = officeHourService;
    }

    // Edit
    @RequestMapping(value = "/{dayNumber}", method = RequestMethod.PUT)
    public OfficeHour edit(@PathVariable Integer dayNumber, @RequestBody OfficeHour officeHour) throws Exception {
        if (dayNumber == null) {
            throw new EntityNotFoundException("Null day number found!");
        }

        return officeHourService.update(dayNumber, officeHour);
    }

    // Get All
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<OfficeHour> getAll() throws Exception {
        return officeHourService.readAll();
    }
}
