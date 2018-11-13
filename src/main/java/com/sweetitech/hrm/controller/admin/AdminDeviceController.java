package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.DeviceLocation;
import com.sweetitech.hrm.service.implementation.DeviceLocationServiceImpl;
import com.sweetitech.hrm.service.implementation.DeviceServiceImpl;
import com.sweetitech.hrm.service.mapping.DeviceLocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/devices")
public class AdminDeviceController {

    private DeviceLocationServiceImpl deviceLocationService;
    private DeviceServiceImpl deviceService;

    @Autowired
    private DeviceLocationMapper deviceLocationMapper;

    @Autowired
    public AdminDeviceController(DeviceLocationServiceImpl deviceLocationService, DeviceServiceImpl deviceService) {
        this.deviceLocationService = deviceLocationService;
        this.deviceService = deviceService;
    }

    // Get all by company id
    @RequestMapping(value = "/list/{companyId}", method = RequestMethod.GET)
    public List<DeviceLocation> getAllByCompanyId(@PathVariable Long companyId) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return deviceLocationService.readAllByCompanyId(companyId);
    }

    // Get by id
    @RequestMapping(value = "/{locationId}", method = RequestMethod.GET)
    public DeviceLocation getById(@PathVariable Long locationId) throws Exception {
        if (locationId == null) {
            throw new EntityNotFoundException("Null location id found!");
        }

        return deviceLocationService.read(locationId);
    }

    // Get by device id
    @RequestMapping(value = "/device/{deviceId}", method = RequestMethod.GET)
    public boolean getByDeviceId(@PathVariable String deviceId) throws Exception {
        if (deviceId == null) {
            throw new EntityNotFoundException("Null device id found!");
        }

        return deviceLocationService.readByDeviceId(deviceId);
    }
}
