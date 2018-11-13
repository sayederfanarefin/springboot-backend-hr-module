package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.domain.OfficeHour;
import com.sweetitech.hrm.domain.dto.OfficeHourDateDTO;
import com.sweetitech.hrm.repository.OfficeHourRepository;
import com.sweetitech.hrm.service.OfficeHourService;
import com.sweetitech.hrm.service.mapping.OfficeHourDateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OfficeHourServiceImpl implements OfficeHourService {

    private OfficeHourRepository officeHourRepository;

    @Autowired
    private OfficeHourDateMapper officeHourDateMapper;

    @Autowired
    public OfficeHourServiceImpl(OfficeHourRepository officeHourRepository) {
        this.officeHourRepository = officeHourRepository;
    }

    boolean isValidMonth(int month) {
        return (month > 0 && month <= 12);
    }

    boolean isValidYear(int year) {
        return (year >= 1800 && year <= 2100);
    }

    @Override
    public OfficeHour create(OfficeHour officeHour) {
        return officeHourRepository.save(officeHour);
    }

    @Override
    public OfficeHour update(Integer dayNumber, OfficeHour officeHour) throws Exception {
        OfficeHour officeHourOld = officeHourRepository.getFirstByDayNumber(dayNumber);
        if (officeHourOld == null) {
            throw new EntityNotFoundException("No day with day number: " + dayNumber);
        }

        officeHourOld.setTypeOfDay(officeHour.getTypeOfDay());
        officeHourOld.setInTime(officeHour.getInTime());
        officeHourOld.setOutTime(officeHour.getOutTime());
        officeHourOld.setLastInTime(officeHour.getLastInTime());
        officeHourOld.setFirstOutTime(officeHour.getFirstOutTime());
        return officeHourRepository.save(officeHourOld);
    }

    @Override
    public OfficeHour readByDayNumber(Integer dayNumber) throws Exception {
        return officeHourRepository.getFirstByDayNumber(dayNumber);
    }

    @Override
    public List<OfficeHour> readAll() throws Exception {
        return officeHourRepository.findAllByOrderByDayNumberAsc();
    }

    @Override
    public List<OfficeHour> readAllWeekDays() throws Exception {
        return officeHourRepository.findAllByTypeOfDay(Constants.Days.WEEK_DAY);
    }

    @Override
    public List<OfficeHour> readAllWeekEnds() throws Exception {
        return officeHourRepository.findAllByTypeOfDay(Constants.Days.WEEK_END);
    }

    @Override
    public List<OfficeHourDateDTO> readAllDatesByWeekends(Integer month, Integer year) throws Exception {
        List<OfficeHourDateDTO> dtos = new ArrayList<>();

        List<OfficeHour> weekends = this.readAllWeekEnds();
        if (weekends == null || (weekends != null && weekends.size() == 0)) {
            return dtos;
        }

        if (!isValidMonth(month) || !isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year found!");
        }

        Calendar calStart = Calendar.getInstance();
        Date startOfMonth = calStart.getTime();

        Calendar calEnd = Calendar.getInstance();
        Date endOfMonth = calEnd.getTime();

//        DateTimeFormatter pattern = DateTimeFormat.forPattern("dd/MM/yyyy");
//        DateTime startDate = pattern.parseDateTime(start);
//        DateTime endDate = pattern.parseDateTime(end);


//        while (startOfMonth.before(endOfMonth)){
//            if ( startOfMonth.getDayOfWeek() == DateTimeConstants.FRIDAY ){
//                fridays.add(startDate);
//                reachedAFriday = true;
//            }
//            if ( reachedAFriday ){
//                startDate = startDate.plusWeeks(1);
//            } else {
//                startDate = startDate.plusDays(1);
//            }
//        }

        List<Date> weekendDates;
        boolean reachedWeekend;
        OfficeHourDateDTO dto;

        for (OfficeHour weekend: weekends) {
            weekendDates = new ArrayList<>();
            reachedWeekend = false;

//            calStart.set(Calendar.DATE, 1);
//            calStart.set(Calendar.MONTH, (month - 1));
//            calStart.set(Calendar.YEAR, year);
//
//            calEnd.set(Calendar.DATE, 1);
//            calEnd.set(Calendar.MONTH, (month - 1));
//            calEnd.set(Calendar.YEAR, year);
//            calEnd.set(Calendar.DATE, calEnd.getActualMaximum(Calendar.DATE)); // changed calendar to cal

            calStart = DateTimeUtils.getStartOfMonth(month, year);
            calEnd = DateTimeUtils.getEndOfMonth(month, year);

            while (calStart.getTime().before(calEnd.getTime())) {

                if (calStart.get(Calendar.DAY_OF_WEEK) == weekend.getDayNumber()) {
                    Date date = calStart.getTime();
                    weekendDates.add(date);
                    reachedWeekend = true;
                }

                if (reachedWeekend) {
                    calStart.add(Calendar.DATE, 7);
                } else {
                    calStart.add(Calendar.DATE, 1);
                }
            }

            for (Date weekendDate : weekendDates) {
                dto = new OfficeHourDateDTO();
                dto = officeHourDateMapper.map(weekend, weekendDate);

                dtos.add(dto);
            }
        }

        return dtos;
    }
}
