package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.OfficeHour;
import com.sweetitech.hrm.domain.dto.OfficeHourDateDTO;

import java.util.List;

public interface OfficeHourService {

    OfficeHour create(OfficeHour officeHour);

    OfficeHour update(Integer dayNumber, OfficeHour officeHour) throws Exception;

    List<OfficeHour> readAll() throws Exception;

    List<OfficeHour> readAllWeekDays() throws Exception;

    List<OfficeHour> readAllWeekEnds() throws Exception;

    List<OfficeHourDateDTO> readAllDatesByWeekends(Integer month, Integer year) throws Exception;

    OfficeHour readByDayNumber(Integer dayNumber) throws Exception;

}
