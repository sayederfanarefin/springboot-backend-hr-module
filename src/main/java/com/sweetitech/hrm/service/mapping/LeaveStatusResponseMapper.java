package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.LeaveStatus;
import com.sweetitech.hrm.domain.dto.LeaveStatusResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveStatusResponseMapper {

    @Autowired
    UserResponseMapper userResponseMapper;

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public LeaveStatusResponseDTO map(LeaveStatus entity) throws Exception {
        if (entity.getApprovedBy() == null) {
            throw new EntityNotFoundException("No user found!");
        }

        LeaveStatusResponseDTO dto = new LeaveStatusResponseDTO();

        dto.setStatus(entity.getStatus());
        dto.setStatusDate(entity.getStatusDate());
        dto.setStatusReason(entity.getReason());

        dto.setStatusByUser(userResponseMapper.map(entity.getApprovedBy()));

        return dto;
    }

}
