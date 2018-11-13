package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.OfficeHour;
import com.sweetitech.hrm.domain.dto.OfficeHourDateDTO;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class OfficeHourDateMapper {

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public OfficeHourDateDTO map(OfficeHour entity, Date date) throws Exception {
        if (date == null) {
            throw new EntityNotFoundException("Date cannot be null!");
        }

        OfficeHourDateDTO dto = new OfficeHourDateDTO();
        dto.setDayName(entity.getDayName());

        dto.setDate(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        dto.setDayNumber(calendar.get(Calendar.DAY_OF_MONTH));

        return dto;
    }

}
