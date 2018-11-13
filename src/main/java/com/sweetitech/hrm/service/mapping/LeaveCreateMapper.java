package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.common.util.DateUtil;
import com.sweetitech.hrm.domain.Leave;
import com.sweetitech.hrm.domain.LeaveStatus;
import com.sweetitech.hrm.domain.OfficeHour;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.LeaveDTO;
import com.sweetitech.hrm.service.implementation.HolidayServiceImpl;
import com.sweetitech.hrm.service.implementation.OfficeHourServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LeaveCreateMapper {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private OfficeHourServiceImpl officeHourService;

    @Autowired
    private HolidayServiceImpl holidayService;

    /**
     * Maps DTO to Entity
     *
     * @param dto
     * @return entity
     */
    public Leave map(LeaveDTO dto) throws Exception {
        if (dto.getFromDate() == null || dto.getToDate() == null
                || !DateTimeUtils.isValidDate(dto.getFromDate().toString(), "E MMM dd HH:mm:ss z yyyy")
                || !DateTimeUtils.isValidDate(dto.getToDate().toString(), "E MMM dd HH:mm:ss z yyyy")) {
            throw new EntityNotFoundException("Invalid date found!");
        }
        if (dto.getAddress() == null || dto.getAddress().length() < 2) {
            throw new EntityNotFoundException("Invalid address found!");
        }
        if (dto.getPhone() != null && dto.getPhone().length() < 6) {
            throw new EntityNotFoundException("Invalid phone number found!");
        }
        if (dto.getRequestedByUserId() == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        User requestingUser = userService.read(dto.getRequestedByUserId());
        if(requestingUser == null) {
            throw new EntityNotFoundException("No user with id: " + dto.getRequestedByUserId());
        }

        Leave entity = new Leave();
        entity.setReason(dto.getReason());
        entity.setFromDate(dto.getFromDate());
        entity.setToDate(dto.getToDate());

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(dto.getFromDate());
        calStart = DateTimeUtils.getStartOfDay(calStart);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dto.getToDate());
        calEnd = DateTimeUtils.getEndOfDay(calEnd);

        if (calEnd.before(calStart)) {
            throw new EntityNotFoundException("To date is before start date!");
        }

        List<OfficeHour> weekends = officeHourService.readAllWeekEnds();

        Calendar temp = Calendar.getInstance();
        temp = calStart;
        long dayToAdd = 0;

        int consideredDays = ((int) Duration.between(calStart.toInstant(), calEnd.toInstant()).toDays() + 1);

        for (int i = 0; i < consideredDays; i++) {
            boolean flag = false;

            for (OfficeHour weekend: weekends) {
                if (temp.get(Calendar.DAY_OF_WEEK) == weekend.getDayNumber()) {
                    flag = true;
                    break;
                }
            }

            if (holidayService.readHoliday(temp.getTime(), Constants.Tasks.HOLIDAY, Constants.RequestStatus.APPROVED, requestingUser.getCompany().getId()) != null) {
                flag = true;
            }

            if (!flag) {
                dayToAdd++;
            }

            temp.add(Calendar.DATE, 1);
        }

        // long days = Duration.between(calStart.toInstant(), calEnd.toInstant()).toDays() + 1;
        entity.setTotalDays((int) dayToAdd);

        if (dto.getCasualLeave() == null)
            entity.setCasualLeave(0);
        else
            entity.setCasualLeave(dto.getCasualLeave());

        if (dto.getEarnLeave() == null)
            entity.setEarnLeave(0);
        else
            entity.setEarnLeave(dto.getEarnLeave());

        if (dto.getSickLeave() == null)
            entity.setSickLeave(0);
        else
            entity.setSickLeave(dto.getSickLeave());

        if (dto.getLeaveWithoutPay() == null)
            entity.setLeaveWithoutPay(0);
        else
            entity.setLeaveWithoutPay(dto.getLeaveWithoutPay());

        if (dto.getSpecialLeave() == null)
            entity.setSpecialLeave(0);
        else
            entity.setSpecialLeave(dto.getSpecialLeave());

        entity.setAddress(dto.getAddress());

        if (dto.getPhone() != null)
            entity.setPhone(dto.getPhone());

        if (dto.getRequestedOn() == null || !DateTimeUtils.isValidDate(dto.getRequestedOn().toString(), "E MMM dd HH:mm:ss z yyyy")) {
            entity.setRequestedOn(new Date());
        } else {
            entity.setRequestedOn(dto.getRequestedOn());
        }

        entity.setRequestedBy(requestingUser);

        // Leave Status
        LeaveStatus status = new LeaveStatus();

        if (dto.getStatusByUserId() == null) {
            status.setApprovedBy(requestingUser);
            status.setStatus(Constants.RequestStatus.REQUESTED);
            status.setStatusDate(new Date());
            status.setReason(dto.getReason());
        } else {
            User decidingUser = userService.read(dto.getStatusByUserId());
            if (decidingUser == null) {
                throw new EntityNotFoundException("No user with id: " + dto.getStatusByUserId());
            }

            status.setApprovedBy(decidingUser);
            status.setStatusDate(new Date());
            status.setReason(dto.getStatusReason());

            if (dto.getStatus() == null) {
                status.setStatus(Constants.RequestStatus.REQUESTED);
            } else {
                status.setStatus(dto.getStatus());
            }
        }

        entity.setLeaveStatus(status);

        return entity;
    }

}
