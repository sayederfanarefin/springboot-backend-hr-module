package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.ConstantsUtil;
import com.sweetitech.hrm.domain.Tour;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.TourDTO;
import com.sweetitech.hrm.domain.dto.TourResponseDTO;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class TourMapper {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    /**
     *
     * @param dto
     * @return entity
     */
    public Tour map(TourDTO dto) throws Exception {
        Tour entity = new Tour();

        System.out.println(dto.toString());

        User requestedByUser = userService.read(dto.getRequestedByUserId());
        if (requestedByUser == null) {
            throw new EntityNotFoundException("No users with id: " + dto.getRequestedByUserId());
        }

        User statusByUser = null;
        if (dto.getStatusByUserId() != null)
            statusByUser = userService.read(dto.getStatusByUserId());
        if (statusByUser == null) {
            statusByUser = requestedByUser;
        }

        if (dto.getFromDate() == null
                || dto.getToDate() == null
                || dto.getLocation() == null
                || dto.getLocation().length() < 2) {
            throw new EntityNotFoundException("Null values found!");
        }

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(dto.getFromDate());

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dto.getToDate());

        if (calEnd.before(calStart)) {
            throw new EntityNotFoundException("To date cannot be before start date");
        }

        if (dto.getStatus() == null) {
            entity.setStatus(Constants.RequestStatus.REQUESTED);
        } else if (!ConstantsUtil.isValidStatus(dto.getStatus())) {
            throw new EntityNotFoundException("Invalid status found!");
        } else {
            entity.setStatus(dto.getStatus());
        }

        entity.setRequestedBy(requestedByUser);
        entity.setLocation(dto.getLocation());

        if (dto.getCustomer() != null)
            entity.setCustomer(dto.getCustomer());

        if (dto.getModeOfTransport() != null)
            entity.setModeOfTransport(dto.getModeOfTransport());

        entity.setFromDate(dto.getFromDate());
        entity.setToDate(dto.getToDate());

        if (dto.getDuration() != null)
            entity.setDuration(dto.getDuration());

        entity.setStatusBy(statusByUser);

        return entity;
    }

    /**
     *
     * @param entity
     * @return dto
     */
    public TourResponseDTO map(Tour entity) throws Exception {
        TourResponseDTO dto = new TourResponseDTO();

        dto.setTourId(entity.getId());
        dto.setCreatedOn(entity.getCreatedOn());
        dto.setCustomer(entity.getCustomer());
        dto.setDuration(entity.getDuration());
        dto.setFromDate(entity.getFromDate());
        dto.setLocation(entity.getLocation());
        dto.setModeOfTransport(entity.getModeOfTransport());
        dto.setRequestedByUser(userSmallResponseMapper.map(entity.getRequestedBy()));
        dto.setStatus(entity.getStatus());
        dto.setStatusByUser(userSmallResponseMapper.map(entity.getStatusBy()));
        dto.setToDate(entity.getToDate());

        return dto;
    }

}
