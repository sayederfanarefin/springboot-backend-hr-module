package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.LeaveStatus;
import com.sweetitech.hrm.domain.dto.LeaveDTO;
import com.sweetitech.hrm.domain.dto.LeaveResponseDTO;
import com.sweetitech.hrm.service.implementation.LeaveServiceImpl;
import com.sweetitech.hrm.service.mapping.LeaveCreateMapper;
import com.sweetitech.hrm.service.mapping.LeaveResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/super-admin/leaves")
public class SuperAdminLeaveController {

    private LeaveServiceImpl leaveService;

    @Autowired
    private LeaveCreateMapper leaveCreateMapper;

    @Autowired
    private LeaveResponseMapper leaveResponseMapper;

    @Autowired
    public SuperAdminLeaveController(LeaveServiceImpl leaveService) {
        this.leaveService = leaveService;
    }

    // Update Leave
    @RequestMapping(value = "/{leaveId}", method = RequestMethod.PUT)
    public LeaveResponseDTO updateLeave(@PathVariable Long leaveId,
                                        @RequestBody LeaveDTO dto) throws Exception {
        if(leaveId == null) {
            throw new EntityNotFoundException("Null leave id found!");
        }
        if (dto == null) {
            throw new EntityNotFoundException("No leave details found!");
        }

        return leaveResponseMapper.map(leaveService.update(leaveId, leaveCreateMapper.map(dto)));
    }

    @RequestMapping(value = "/status/{leaveId}", method = RequestMethod.PUT)
    public LeaveResponseDTO updateStatus(@PathVariable Long leaveId,
                                         @RequestParam Long userId,
                                         @RequestParam String status,
                                         @RequestParam String reason) throws Exception {
        if(leaveId == null) {
            throw new EntityNotFoundException("Null leave id found!");
        }
        if(userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        LeaveStatus leaveStatus = leaveService.prepareStatus(status, userId, reason);

        return leaveResponseMapper.map(leaveService.updateStatus(leaveId, leaveStatus));
    }
}
