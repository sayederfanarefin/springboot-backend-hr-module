package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.DriverDTO;
import com.sweetitech.hrm.domain.dto.DriverResponseDTO;
import com.sweetitech.hrm.domain.dto.LeaveCountDTO;
import com.sweetitech.hrm.domain.dto.page.DriverPage;
import com.sweetitech.hrm.service.implementation.DriverServiceImpl;
import com.sweetitech.hrm.service.mapping.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/drivers")
public class AdminDriverController {

    private DriverServiceImpl driverService;

    @Autowired
    private DriverMapper driverMapper;

    @Autowired
    public AdminDriverController(DriverServiceImpl driverService) {
        this.driverService = driverService;
    }

    // Create entity
    @RequestMapping(value = "", method = RequestMethod.POST)
    public DriverResponseDTO create(@RequestBody DriverDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return driverService.create(driverMapper.map(dto));
    }

    // Update entity
    @RequestMapping(value = "/{driverId}", method = RequestMethod.PUT)
    public DriverResponseDTO update(@PathVariable Long driverId, @RequestBody DriverDTO dto) throws Exception {
        if (driverId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return driverService.update(driverId, driverMapper.map(dto));
    }

    // Get DTO by id
    @RequestMapping(value = "/dto/{driverId}", method = RequestMethod.GET)
    public DriverResponseDTO getDTO(@PathVariable Long driverId) throws Exception {
        if (driverId == null) {
            throw new EntityNotFoundException("Null driver id found!");
        }

        return driverService.read(driverId);
    }

    // Deactivate by driver id
    @RequestMapping(value = "/{driverId}", method = RequestMethod.DELETE)
    public void deactivate(@PathVariable Long driverId) throws Exception {
        if (driverId == null) {
            throw new EntityNotFoundException("Null driver id found!");
        }

        driverService.remove(driverId);
    }

    // Read all active by company id
    @RequestMapping(value = "/active/{companyId}", method = RequestMethod.GET)
    public DriverPage getAllActiveByCompany(@PathVariable Long companyId,
                                            @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return driverService.readAllActiveByCompanyId(companyId, page);
    }

    // Read all inactive by company id
    @RequestMapping(value = "/inactive/{companyId}", method = RequestMethod.GET)
    public DriverPage getAllInActiveByCompany(@PathVariable Long companyId,
                                              @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return driverService.readAllInactiveByCompanyId(companyId, page);
    }

    // Reactivate by driver id
    @RequestMapping(value = "/activate/{driverId}", method = RequestMethod.PUT)
    public DriverResponseDTO reactivate(@PathVariable Long driverId) throws Exception {
        if (driverId == null) {
            throw new EntityNotFoundException("Null driver id found!");
        }

        return driverService.reactivate(driverId);
    }

    // Get all available
    @RequestMapping(value = "/available/{companyId}", method = RequestMethod.POST)
    public List<DriverResponseDTO> getAllAvailableByCompany(@PathVariable Long companyId,
                                                            @RequestBody LeaveCountDTO dto,
                                                            @RequestParam Integer fromHour,
                                                            @RequestParam Integer toHour,
                                                            @RequestParam(value = "carId", defaultValue = "-1") Long carId) throws Exception {
        if (companyId == null || dto == null || dto.getFromDate() == null || dto.getToDate() == null
                || fromHour == null || toHour == null) {
            throw new EntityNotFoundException("Invalid values found!");
        }

        return driverService.readAllAvailable(companyId, dto.getFromDate(), fromHour, dto.getToDate(), toHour, carId);
    }
}
