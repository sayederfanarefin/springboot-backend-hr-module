package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Leave;
import com.sweetitech.hrm.domain.dto.LeaveResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveResponseMapper {

    @Autowired
    private UserResponseMapper userResponseMapper;

    @Autowired
    private LeaveStatusResponseMapper leaveStatusResponseMapper;

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public LeaveResponseDTO map(Leave entity) throws Exception {
        if (entity.getFromDate() == null || entity.getToDate() == null) {
            throw new EntityNotFoundException("Invalid date found!");
        }
        if (entity.getRequestedBy() == null || entity.getLeaveStatus().getApprovedBy() == null) {
            throw new EntityNotFoundException("Invalid requesting or approving user!");
        }

        LeaveResponseDTO dto = new LeaveResponseDTO();

        dto.setLeaveId(entity.getId());
        dto.setReason(entity.getReason());
        dto.setFromDate(entity.getFromDate());
        dto.setToDate(entity.getToDate());
        dto.setTotalDays(entity.getTotalDays());
        dto.setCasualLeave(entity.getCasualLeave());
        dto.setEarnLeave(entity.getEarnLeave());
        dto.setSickLeave(entity.getSickLeave());
        dto.setLeaveWithoutPay(entity.getLeaveWithoutPay());
        dto.setSpecialLeave(entity.getSpecialLeave());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        dto.setRequestedOn(entity.getRequestedOn());

        dto.setRequestedByUser(userResponseMapper.map(entity.getRequestedBy()));

        dto.setLeaveStatus(leaveStatusResponseMapper.map(entity.getLeaveStatus()));

        return dto;
    }

}
