package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.Allowance;
import com.sweetitech.hrm.domain.dto.AllowanceListObjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class AllowanceListObjectMapper {

    @Autowired
    private AllowanceUserMapper allowanceUserMapper;

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public AllowanceListObjectDTO map(Allowance entity) throws Exception {
        AllowanceListObjectDTO dto = new AllowanceListObjectDTO();

        dto.setAllowanceId(entity.getId());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(entity.getCreatedOn());
        dto.setDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));

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
        dto.setStatusDate(entity.getAllowanceStatus().getStatusDate());
        dto.setStatus(entity.getAllowanceStatus().getStatus());

        if (entity.getAllowanceSummary() != null)
            dto.setSummaryId(entity.getAllowanceSummary().getId());

        dto.setRequestingUser(allowanceUserMapper.map(entity.getRequestedBy()));
        dto.setDecidingUser(allowanceUserMapper.map(entity.getAllowanceStatus().getDecisionBy()));

        return dto;
    }

}
