package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Allowance;
import com.sweetitech.hrm.domain.AllowanceStatus;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.AllowanceDTO;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AllowanceEditMapper {

    @Autowired
    private UserServiceImpl userService;

    /**
     * Maps DTO to Entity
     *
     * @param dto
     * @return entity
     */
    public Allowance map(AllowanceDTO dto) throws Exception {
        if (dto.getDestination() == null) {
            throw new EntityNotFoundException("Null destination found!");
        }
        if (dto.getRequestedBy() == null) {
            throw new EntityNotFoundException("No user to request!");
        }

        User user = userService.read(dto.getRequestedBy());
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + dto.getRequestedBy());
        }

        User updatingUser = null;
        if (dto.getStatusBy() == null) {
            updatingUser = user;
        } else {
            updatingUser = userService.read(dto.getStatusBy());
            if (updatingUser == null) {
                throw new EntityNotFoundException("No user with id: " + dto.getStatusBy());
            }
        }

        Allowance entity = new Allowance();

        if (dto.getCreatedOn() == null)
            entity.setCreatedOn(new Date());
        else
            entity.setCreatedOn(dto.getCreatedOn());

        entity.setDestination(dto.getDestination());
        entity.setHq(dto.getHq());
        entity.setExHq(dto.getExHq());
        entity.setOs(dto.getOs());
        entity.setHotelFare(dto.getHotelFare());
        entity.setTransportWithTicket(dto.getTransportWithTicket());
        entity.setInternalMode(dto.getInternalMode());
        entity.setInternalFare(dto.getInternalFare());
        entity.setRemarks(dto.getRemarks());
        entity.setRequestedBy(user);

        AllowanceStatus status = new AllowanceStatus();
        status.setStatusDate(new Date());
        status.setStatus(dto.getStatus());
        status.setDecisionBy(updatingUser);
        entity.setAllowanceStatus(status);

        return entity;
    }

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public AllowanceDTO map(Allowance entity) throws Exception {
        if (entity == null) {
            throw new EntityNotFoundException("Null allowance object found!");
        }

        AllowanceDTO dto = new AllowanceDTO();

        dto.setAllowanceId(entity.getId());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setDestination(entity.getDestination());
        dto.setHq(entity.getHq());
        dto.setExHq(entity.getExHq());
        dto.setOs(entity.getOs());
        dto.setHotelFare(entity.getHotelFare());
        dto.setTransportWithTicket(entity.getTransportWithTicket());
        dto.setInternalMode(entity.getInternalMode());
        dto.setInternalFare(entity.getInternalFare());
        dto.setRemarks(entity.getRemarks());
        dto.setRequestedBy(entity.getRequestedBy().getId());
        dto.setStatusDate(entity.getAllowanceStatus().getStatusDate());
        dto.setStatus(entity.getAllowanceStatus().getStatus());
        dto.setStatusBy(entity.getAllowanceStatus().getDecisionBy().getId());

        return dto;
    }

}
