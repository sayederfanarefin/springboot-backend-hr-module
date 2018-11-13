package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.CarRequest;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.CarRequestDTO;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class CarRequestMapper {

    @Autowired
    private UserServiceImpl userService;

    /**
     *
     * @param dto
     * @return entity
     */
    public CarRequest map(CarRequestDTO dto) throws Exception {
        if (dto.getRequestedOn() == null
                || dto.getRequestedFrom() == null
                || dto.getRequestedTo() == null
                || dto.getDestination() == null
                || dto.getFromHour() == null
                || dto.getToHour() == null
                || dto.getPurpose() == null
                || dto.getRequestedByUserId() == null) {
            throw new EntityNotFoundException("Values missing!");
        }

        if (!DateValidator.isValidHour(dto.getFromHour()) || !DateValidator.isValidHour(dto.getToHour())) {
            throw new EntityNotFoundException("Invalid hours found!");
        }

        if (!DateTimeUtils.isValidDate(dto.getRequestedFrom().toString(), Constants.Dates.VALID_FORMAT)
                || !DateTimeUtils.isValidDate(dto.getRequestedTo().toString(), Constants.Dates.VALID_FORMAT)) {
            throw new EntityNotFoundException("Invalid date formats found!");
        }

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(dto.getRequestedFrom());
        calStart.set(Calendar.HOUR_OF_DAY, dto.getFromHour());

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dto.getRequestedTo());
        calEnd.set(Calendar.HOUR_OF_DAY, dto.getToHour());

        if (calEnd.before(calStart)) {
            throw new EntityNotFoundException("To time cannot be before from time!");
        }

        User user = userService.read(dto.getRequestedByUserId());
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + dto.getRequestedByUserId());
        }

        CarRequest entity = new CarRequest();

        if (dto.getRequestedOn() == null) {
            entity.setRequestedOn(new Date());
        } else {
            entity.setRequestedOn(dto.getRequestedOn());
        }

        entity.setRequestedFrom(dto.getRequestedFrom());
        entity.setFromHour(dto.getFromHour());
        entity.setRequestedTo(dto.getRequestedTo());
        entity.setToHour(dto.getToHour());

        entity.setRequestedBy(user);
        entity.setPurpose(dto.getPurpose());
        entity.setDestination(dto.getDestination());

        entity.setStatus(Constants.RequestStatus.REQUESTED);

        if (dto.isRoundTrip() != null)
            entity.setRoundTrip(dto.isRoundTrip());

        return entity;

    }

}
