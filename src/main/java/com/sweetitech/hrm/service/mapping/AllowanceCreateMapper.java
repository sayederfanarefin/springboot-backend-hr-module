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
public class AllowanceCreateMapper {

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

        User user = userService.read(dto.getRequestedBy());
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + dto.getRequestedBy());
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
        status.setStatus(Constants.RequestStatus.REQUESTED);
        status.setDecisionBy(user);
        entity.setAllowanceStatus(status);

        return entity;
    }

}
