package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Device;
import com.sweetitech.hrm.domain.DeviceLocation;
import com.sweetitech.hrm.domain.dto.DeviceLocationDTO;
import com.sweetitech.hrm.service.implementation.DeviceLocationServiceImpl;
import com.sweetitech.hrm.service.implementation.DeviceServiceImpl;
import com.sweetitech.hrm.service.mapping.DeviceLocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/super-admin/devices")
public class SuperAdminDeviceController {

    private DeviceLocationServiceImpl deviceLocationService;
    private DeviceServiceImpl deviceService;

    @Autowired
    private DeviceLocationMapper deviceLocationMapper;

    @Autowired
    public SuperAdminDeviceController(DeviceLocationServiceImpl deviceLocationService, DeviceServiceImpl deviceService) {
        this.deviceLocationService = deviceLocationService;
        this.deviceService = deviceService;
    }

    // Create
    @RequestMapping(value = "", method = RequestMethod.POST)
    public DeviceLocation create(@RequestBody DeviceLocationDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null object found!");
        }

        return deviceLocationService.create(deviceLocationMapper.map(dto));
    }

    // Update
    @RequestMapping(value = "/{locationId}", method = RequestMethod.PUT)
    public DeviceLocation update(@PathVariable Long locationId, @RequestBody DeviceLocationDTO dto) throws Exception {
        if (locationId == null) {
            throw new EntityNotFoundException("Null location id found!");
        }
        if (dto == null) {
            throw new EntityNotFoundException("Null object found!");
        }

        return deviceLocationService.update(locationId, deviceLocationMapper.map(dto));
    }

    // Get all by company id
    @RequestMapping(value = "/list/{companyId}", method = RequestMethod.GET)
    public List<DeviceLocation> getAllByCompanyId(@PathVariable Long companyId) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return deviceLocationService.readAllByCompanyId(companyId);
    }

    // Delete
    @RequestMapping(value = "/{locationId}", method = RequestMethod.DELETE)
    public void changeToInactive(@PathVariable Long locationId) throws Exception {
        if (locationId == null) {
            throw new EntityNotFoundException("Null location id found!");
        }

        deviceLocationService.remove(locationId);
    }

    // Re-Activate
    @RequestMapping(value = "/activate/{locationId}", method = RequestMethod.PUT)
    public void reActivate(@PathVariable Long locationId) throws Exception {
        if (locationId == null) {
            throw new EntityNotFoundException("Null location id found!");
        }

        deviceLocationService.activate(locationId);
    }

    // Get all inactive
    @RequestMapping(value = "/inactive", method = RequestMethod.GET)
    public List<DeviceLocation> getAllInactive() throws Exception {
        return deviceLocationService.readAllInactive();
    }

    // Get all devices
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Device> getAllDevices() throws Exception {
        return deviceService.readAll();
    }
}
