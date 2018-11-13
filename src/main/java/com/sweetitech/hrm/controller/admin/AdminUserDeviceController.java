package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.DeviceLocation;
import com.sweetitech.hrm.domain.dto.UserDeviceDTO;
import com.sweetitech.hrm.domain.dto.UserDeviceResponseDTO;
import com.sweetitech.hrm.domain.relation.UserDeviceRelation;
import com.sweetitech.hrm.service.implementation.DeviceLocationServiceImpl;
import com.sweetitech.hrm.service.implementation.UserDeviceServiceImpl;
import com.sweetitech.hrm.service.implementation.UserInfoServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import com.sweetitech.hrm.service.mapping.UserDeviceMapper;
import com.sweetitech.hrm.service.mapping.UserDeviceResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/user-devices")
public class AdminUserDeviceController {

    private UserDeviceServiceImpl userDeviceService;
    private UserServiceImpl userService;
    private UserInfoServiceImpl userInfoService;
    private DeviceLocationServiceImpl deviceLocationService;

    @Autowired
    private UserDeviceMapper userDeviceMapper;

    @Autowired
    private UserDeviceResponseMapper userDeviceResponseMapper;

    @Autowired
    public AdminUserDeviceController(UserDeviceServiceImpl userDeviceService,
                                     UserServiceImpl userService,
                                     UserInfoServiceImpl userInfoService,
                                     DeviceLocationServiceImpl deviceLocationService) {
        this.userDeviceService = userDeviceService;
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.deviceLocationService = deviceLocationService;
    }

    // Create
    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserDeviceResponseDTO create(@RequestBody UserDeviceDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return userDeviceResponseMapper.map(userDeviceService.create(userDeviceMapper.map(dto)));
    }

    // Update
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public UserDeviceResponseDTO update(@RequestBody UserDeviceDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return userDeviceResponseMapper.map(userDeviceService.update(dto.getUserId(), dto.getDeviceLocationId(), userDeviceMapper.map(dto)));
    }

    // Permanent Delete
    @RequestMapping(value = "/{relationId}", method = RequestMethod.DELETE)
    public void permanentDelete(@PathVariable Long relationId) throws Exception {
        if (relationId == null) {
            throw new EntityNotFoundException("Null id found!");
        }

        userDeviceService.remove(relationId);
    }

    // Get by user and location
    @RequestMapping(value = "", method = RequestMethod.GET)
    public UserDeviceResponseDTO getByUserAndLocation(@RequestParam Long userId, @RequestParam Long locationId) throws Exception {
        if (userId == null || locationId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return userDeviceResponseMapper.map(userDeviceService.exists(userId, locationId));
    }

    // Get all enrollment numbers by user id
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public List<UserDeviceResponseDTO> getAllByUser(@PathVariable Long userId) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        List<UserDeviceResponseDTO> dtos = new ArrayList<>();
        List<UserDeviceRelation> relations = userDeviceService.readAllByUserId(userId);

        for (UserDeviceRelation relation: relations) {
            dtos.add(userDeviceResponseMapper.map(relation));
        }

        return dtos;
    }
}
