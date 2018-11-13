package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.domain.Leave;
import com.sweetitech.hrm.domain.dto.LeaveSummaryDTO;
import com.sweetitech.hrm.domain.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class LeaveSummaryMapper {

    @Autowired
    private UserResponseMapper userResponseMapper;

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public LeaveSummaryDTO map(Leave entity, Date date) throws Exception {
        LeaveSummaryDTO dto = new LeaveSummaryDTO();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dto.setDayNumber(calendar.get(Calendar.DAY_OF_MONTH));

        dto.setLeaveId(entity.getId());
        dto.setLeaveDate(date);
        dto.setReason(entity.getReason());

        if (entity.getCasualLeave() != null && entity.getCasualLeave() > 0) {
            dto.setLeaveType(Constants.LeaveTypes.CASUAL_LEAVE);
        } else if (entity.getEarnLeave() != null && entity.getEarnLeave() > 0) {
            dto.setLeaveType(Constants.LeaveTypes.EARN_LEAVE);
        } else if (entity.getSickLeave() != null && entity.getSickLeave() > 0) {
            dto.setLeaveType(Constants.LeaveTypes.SICK_LEAVE);
        } else if (entity.getLeaveWithoutPay() != null && entity.getLeaveWithoutPay() > 0) {
            dto.setLeaveType(Constants.LeaveTypes.LEAVE_WITHOUT_PAY);
        } else if (entity.getSpecialLeave() != null && entity.getSpecialLeave() > 0) {
            dto.setLeaveType(Constants.LeaveTypes.SPECIAL_LEAVE);
        }

        dto.setRequestedOn(entity.getRequestedOn());

        dto.setRequestedBy(userResponseMapper.map(entity.getRequestedBy()));

        return dto;
    }

}
