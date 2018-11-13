package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.dto.page.AttendanceMonthlySummaryPage;
import com.sweetitech.hrm.common.util.CustomComparator;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.domain.*;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.domain.relation.UserDeviceRelation;
import com.sweetitech.hrm.repository.LogRepository;
import com.sweetitech.hrm.repository.MobileLogsRepository;
import com.sweetitech.hrm.service.LogService;
import com.sweetitech.hrm.service.mapping.UserSmallResponseMapper;
import com.sweetitech.hrm.service.mapping.custom.MobileLogAndLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LogServiceImpl implements LogService {

    private LogRepository logRepository;
    private MobileLogsRepository mobileLogsRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserDeviceServiceImpl userDeviceService;

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    private OfficeHourServiceImpl officeHourService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private LeaveServiceImpl leaveService;

    @Autowired
    private HolidayServiceImpl holidayService;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private MobileLogAndLogMapper mobileLogAndLogMapper;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, MobileLogsRepository mobileLogsRepository) {
        this.logRepository = logRepository;
        this.mobileLogsRepository = mobileLogsRepository;
    }

    boolean isValidMonth(int month) {
        return (month > 0 && month <= 12);
    }

    boolean isValidYear(int year) {
        return (year >= 1800 && year <= 2100);
    }

    @Override
    public Log read(Long id) throws Exception {
        return logRepository.getFirstById(id);
    }

    @Override
    public List<Log> readAllByUserDeviceAndDay(String enrolNumber, String machineNumber, Date date) throws Exception {
        Long enrolNumberL = Long.parseLong(enrolNumber);
        Long machineNumberL = Long.parseLong(machineNumber);

        if (enrolNumberL == null || machineNumberL == null) {
            throw new EntityNotFoundException("Invalid value found!");
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calStart = DateTimeUtils.getStartOfDay(calendar);
        Calendar calEnd = DateTimeUtils.getEndOfDay(calendar);

        Date fromDate = calStart.getTime();
        Date toDate = calEnd.getTime();

        List<Log> logs = new ArrayList<>();
        logs.addAll(logRepository.findAllByEnrolAndMachineNumberAndDate(enrolNumberL, machineNumberL,
                DateTimeUtils.toString(calStart, "M/d/yyyy")));

        List<Log> temp = logRepository.findAllByEnrolAndMachineNumberAndDate(enrolNumberL, machineNumberL,
                DateTimeUtils.toString(calStart, "dd-MMM-yy"));
        if (temp != null && temp.size() > 0) {
            SimpleDateFormat sdfFrom = new SimpleDateFormat("dd-MMM-yy h:mm:ss a");
            SimpleDateFormat sdfTo = new SimpleDateFormat("M/d/yyyy h:mm:ss a");
            for (Log log: temp) {
                System.out.println(log.toString());
                if (log.getDateTimeRecord() != null) {
                    Date tmpDate;
                    try {
                        tmpDate = sdfFrom.parse(log.getDateTimeRecord());
                    } catch (Exception e) {
                        tmpDate = sdfTo.parse(log.getDateTimeRecord());
                    }
                    log.setDateTimeRecord(sdfTo.format(tmpDate));
                    logs.add(log);
                }
            }
        }
//        logs.addAll(logRepository.findAllByEnrolAndMachineNumberAndDate(enrolNumberL, machineNumberL,
//                DateTimeUtils.toString(calStart, "dd-MMM-yy")));

        return logs;
    }

    @Override
    public Log readByUserDeviceAndDay(Long userId, String enrolNumber, String machineNumber, Date date) throws Exception {
        Log log = null;

        if (enrolNumber == null || machineNumber == null) {
            // throw new EntityNotFoundException("Invalid value found!");

            MobileLogs mobileLogs = mobileLogsRepository
                    .getFirstByUserIdAndTimestampBetweenOrderByTimestampAsc(userId,
                            DateTimeUtils.getStartOfDayTimestamp(date),
                            DateTimeUtils.getEndOfDayTimestamp(date));

            if (mobileLogs != null)
                log = mobileLogAndLogMapper.mobileLogToLog(mobileLogs);
        } else {
            Long enrolNumberL = Long.parseLong(enrolNumber);
            Long machineNumberL = Long.parseLong(machineNumber);

            if (enrolNumberL == null || machineNumberL == null) {
                throw new EntityNotFoundException("Invalid value found!");
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            Calendar calStart = DateTimeUtils.getStartOfDay(calendar);
            Calendar calEnd = DateTimeUtils.getEndOfDay(calendar);

            log = logRepository.getFirstByEnrolAndMachineNumberAndDate(enrolNumberL, machineNumberL,
                    DateTimeUtils.toString(calStart, "M/d/yyyy"));

            if (log == null) {
                log = logRepository.getFirstByEnrolAndMachineNumberAndDate(enrolNumberL, machineNumberL,
                        DateTimeUtils.toString(calStart, "dd-MMM-yy"));
            }

            if (log == null) {
                MobileLogs mobileLogs = mobileLogsRepository
                        .getFirstByUserIdAndTimestampBetweenOrderByTimestampAsc(userId,
                                DateTimeUtils.getStartOfDayTimestamp(date),
                                DateTimeUtils.getEndOfDayTimestamp(date));

                if (mobileLogs != null)
                    log = mobileLogAndLogMapper.mobileLogToLog(mobileLogs);
            }
        }

        // System.out.println("Line 170: " + log);

        return log;
    }

    @Override
    public List<Log> readAllByUserAndDay(Long userId, Date date) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }
        if (!DateTimeUtils.isValidDate(date.toString(), "E MMM dd HH:mm:ss z yyyy")) {
            throw new EntityNotFoundException("Invalid date found!");
        }

        List<UserDeviceRelation> mappings = userDeviceService.readAllByUserId(userId);
        List<Log> logs = new ArrayList<>();
        List<Log> temp = new ArrayList<>();

        if (mappings != null && mappings.size() > 0) {
            for (UserDeviceRelation map: mappings) {
                temp = new ArrayList<>();
                temp = this.readAllByUserDeviceAndDay(map.getEnrollmentNumber(),
                        String.valueOf(map.getLocation().getDevice().getDeviceId()), date);
                logs.addAll(temp);
            }
        }

        List<MobileLogs> mobileLogs = mobileLogsRepository.findAllByUserIdAndTimestampBetweenOrderByTimestampAsc(userId,
                DateTimeUtils.getStartOfDayTimestamp(date), DateTimeUtils.getEndOfDayTimestamp(date));

        if (mobileLogs != null && mobileLogs.size() > 0) {
            for (MobileLogs mobileLog: mobileLogs) {
                logs.add(mobileLogAndLogMapper.mobileLogToLog(mobileLog));
            }
        }

        Collections.sort(logs, new CustomComparator());
        return logs;
    }

    @Override
    public boolean checkAbsent(Long userId, Date date) throws Exception {
        // System.out.println("checkAbsent called!");

        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }
        if (!DateTimeUtils.isValidDate(date.toString(), "E MMM dd HH:mm:ss z yyyy")) {
            throw new EntityNotFoundException("Invalid date found!");
        }

        List<UserDeviceRelation> mappings = userDeviceService.readAllByUserId(userId);
        Log temp = null;

        if (mappings != null && mappings.size() > 0) {
            for (UserDeviceRelation map: mappings) {
                temp = this.readByUserDeviceAndDay(userId, map.getEnrollmentNumber(),
                        String.valueOf(map.getLocation().getDevice().getDeviceId()), date);

                // System.out.println("Line 227: " + temp);

                if (temp != null) {
                    break;
                }
            }
        } else {
            temp = this.readByUserDeviceAndDay(userId, null,null, date);

            //System.out.println("Line 236: " + temp);
        }

        // System.out.println("Line 239: " + temp);

        if (temp != null) {
            return false;
        }

        return true;
    }

    @Override
    public AttendanceDayTrackDTO countLateAndEarly(Long userId, Date date) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }
        if (!DateTimeUtils.isValidDate(date.toString(), "E MMM dd HH:mm:ss z yyyy")) {
            throw new EntityNotFoundException("Invalid date found!");
        }

        List<Log> logs = this.readAllByUserAndDay(userId, date);

        AttendanceDayTrackDTO dayTrackDTO = new AttendanceDayTrackDTO();

        dayTrackDTO.setForDate(date);
        dayTrackDTO.setUserSmallResponseDTO(userSmallResponseMapper.map(userService.read(userId)));

        if (logs != null && logs.size() > 0) {
            Date inDateTime = DateTimeUtils.toDateTime(logs.get(0).getDateTimeRecord(),
                    "M/dd/yyyy h:mm:ss a").getTime();
            dayTrackDTO.setInTime(inDateTime);

            Date outDateTime = DateTimeUtils.toDateTime(logs.get(logs.size() - 1).getDateTimeRecord(),
                    "M/dd/yyyy h:mm:ss a").getTime();
            dayTrackDTO.setOutTime(outDateTime);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            OfficeHour officeHour = officeHourService.readByDayNumber(calendar.get(Calendar.DAY_OF_WEEK));

            if (!Objects.equals(officeHour.getTypeOfDay(), Constants.Days.WEEK_END)) {
                Calendar inTime = Calendar.getInstance();
                inTime.setTime(DateTimeUtils.toTime(logs.get(0).getDateTimeRecord(),
                        "M/dd/yyyy h:mm:ss a").getTime());

                // In Time
                Calendar lastInTime = Calendar.getInstance();
                lastInTime.setTime(DateTimeUtils.toTime(officeHour.getLastInTime(),
                        "HH:mm:ss").getTime());

                if (lastInTime.before(inTime)) {
                    dayTrackDTO.setLate(true);

                    long seconds = (inTime.getTimeInMillis() - lastInTime.getTimeInMillis()) / 1000;
                    int minutes = (int) (seconds / 60);

                    dayTrackDTO.setLateCount(minutes);
                }


                //Out Time
                Calendar outTime = Calendar.getInstance();
                outTime.setTime(DateTimeUtils.toTime(logs.get(logs.size() - 1).getDateTimeRecord(),
                        "M/dd/yyyy h:mm:ss a").getTime());

                Calendar firstOutTime = Calendar.getInstance();
                firstOutTime.setTime(DateTimeUtils.toTime(officeHour.getFirstOutTime(),
                        "HH:mm:ss").getTime());

                if (firstOutTime.after(outTime)) {
                    dayTrackDTO.setLeftEarly(true);

                    long seconds = (firstOutTime.getTimeInMillis() - outTime.getTimeInMillis()) / 1000;
                    int minutes = (int) (seconds / 60);

                    dayTrackDTO.setEarlyCount(minutes);
                }
            }

            int clockCount = logs.size();
            if ((clockCount - 2) > 0) {
                dayTrackDTO.setClockCount(clockCount);
            }
        }

        return dayTrackDTO;
    }

    @Override
    public AttendanceMonthlySummaryDTO summaryByUserAndMonth(User user,
                                                             Integer month,
                                                             Integer year,
                                                             List<LeaveSummaryDTO> leaves,
                                                             List<TaskResponseDTO> holidays,
                                                             List<OfficeHourDateDTO> weekends) throws Exception {

        if (!isValidMonth(month) || !isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year found!");
        }

        AttendanceMonthlySummaryDTO dto = new AttendanceMonthlySummaryDTO(userSmallResponseMapper.map(user), month, year);

        Calendar calStart = DateTimeUtils.getStartOfMonth(month, year);
        Calendar calEnd = DateTimeUtils.getEndOfMonth(month, year);

        boolean isLeave, isWeekendOrHoliday;

        while (calStart.getTime().before(calEnd.getTime())) {
            AttendanceDayTrackDTO dayTrackDTO = this.countLateAndEarly(user.getId(), calStart.getTime());

            isLeave = false;
            isWeekendOrHoliday = false;

//            System.out.println("/******************************************/");
//            System.out.println("Day considered: " + calStart.getTime());

            for (LeaveSummaryDTO leave: leaves) {
                if (leave.getDayNumber() == calStart.get(Calendar.DAY_OF_MONTH)) {
                    System.out.println("Leave: " + leave.getLeaveDate());
                    isLeave = true;
                    dto.setLeaveCount(dto.getLeaveCount() + 1);
                    break;
                }
            }

            for (OfficeHourDateDTO weekend: weekends) {
                if (weekend.getDayNumber() == calStart.get(Calendar.DAY_OF_MONTH)) {
                    System.out.println("Weekend: " + weekend.getDate());
                    isWeekendOrHoliday = true;
                    dto.setTotalWeekends(dto.getTotalWeekends() + 1);
                    break;
                }
            }

            for (TaskResponseDTO holiday: holidays) {
                if (holiday.getDayNumber() == calStart.get(Calendar.DAY_OF_MONTH)) {
                    System.out.println("Holiday: " + holiday.getScheduledDate());
                    if (!isWeekendOrHoliday) {
                        dto.setTotalHolidays(dto.getTotalHolidays() + 1);
                    }
                    isWeekendOrHoliday = true;
                    break;
                }
            }

            if (!isLeave) {

                // Not Leave
                if (dayTrackDTO.getInTime() != null) {

                    if (isWeekendOrHoliday) {
                        // Is weekend or holiday
                        dto.setOffDayCount(dto.getOffDayCount() + 1);
                    }
                    else {
                        // Is working day
                        if (dayTrackDTO.isLate()) {
                            dto.setLateCount(dto.getLateCount() + 1);
                        }
                        if (dayTrackDTO.isLeftEarly()) {
                            dto.setEarlyLeaveCount(dto.getEarlyLeaveCount() + 1);
                        }
                        dto.setWorkingDayCount(dto.getWorkingDayCount() + 1);
                    }

                }
                else {

                    if (!isWeekendOrHoliday) {
                        // Was absent
                        dto.setAbsentCount(dto.getAbsentCount() + 1);
                    }

                }

            }

            if (!isWeekendOrHoliday) {
                dto.setTotalWorkingDays(dto.getTotalWorkingDays() + 1);
            }

//            System.out.println(calStart.getTime() + " " +
//                    "" + dto.getTotalHolidays() + " " +
//                    "" + dto.getTotalWeekends() + " " +
//                    "" + dto.getTotalWorkingDays());
//            System.out.println("/******************************************/");

            calStart.add(Calendar.DATE, 1);
        }

        return dto;
    }

    @Override
    public List<AttendanceMonthlySummaryDTO> summaryByCompanyAndMonth(Long companyId, Integer month, Integer year) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        List<User> users = userService.readByCompanyId(companyId);
        List<AttendanceMonthlySummaryDTO> dtos = new ArrayList<>();

        if (users != null && users.size() > 0) {
            for (User user: users) {
                dtos.add(this.generateMonthlySummaryForUser(user, month, year));
            }
        }

        return dtos;
    }

    // for report
    @Override
    public List<AttendanceMonthlySummaryDTO> summaryByCompanyDepartmentAndMonth(Long companyId,
                                                                                Long departmentId,
                                                                                Integer month,
                                                                                Integer year) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        if (departmentService.read(departmentId) == null) {
            throw new EntityNotFoundException("No departments with id: " + departmentId);
        }

        if (!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year found!");
        }

        List<UserSmallResponseDTO> users = userService.readAllActiveUsersByCompanyAndDepartment(companyId, departmentId);
        List<AttendanceMonthlySummaryDTO> dtos = new ArrayList<>();

        if (users != null && users.size() > 0) {
            for (UserSmallResponseDTO user: users) {
                dtos.add(this.generateMonthlySummaryForUser(userService.read(user.getUserId()), month, year));
            }
        }

        return dtos;
    }

    // for report
    @Override
    public List<AttendanceMonthlySummaryDTO> summaryByCompanyDepartmentOfficeCodeAndMonth(Long companyId,
                                                                                          Long departmentId,
                                                                                          Long officeCodeId,
                                                                                          Integer month,
                                                                                          Integer year) throws Exception {
        if (!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year found!");
        }

        List<UserSmallResponseDTO> users = new ArrayList<>();
        List<AttendanceMonthlySummaryDTO> dtos = new ArrayList<>();

        for (User user: userService.listActiveUsers(companyId, departmentId, officeCodeId)) {
            users.add(userSmallResponseMapper.map(user));
        }

        if (users != null && users.size() > 0) {
            for (UserSmallResponseDTO user: users) {
                dtos.add(this.generateMonthlySummaryForUser(userService.read(user.getUserId()), month, year));
            }
        }

        return dtos;
    }

    @Override
    public AttendanceMonthlySummaryPage paginatedSummaryByCompanyAndMonth(Long companyId,
                                                                               Integer month,
                                                                               Integer year,
                                                                               Integer page) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        //TODO fix this particular call cause it may miss some of the users
        Page<User> pagedUsers = userService.readByCompanyId(companyId, page);

        List<AttendanceMonthlySummaryDTO> dtos = new ArrayList<>();

        if (pagedUsers.getContent().size() > 0) {
            for (User user: pagedUsers.getContent()) {
                dtos.add(this.generateMonthlySummaryForUser(user, month, year));
            }
        }

        AttendanceMonthlySummaryPage resultPage = new AttendanceMonthlySummaryPage(dtos, new PageRequest(page, PageAttr.PAGE_SIZE));
        resultPage.setValues(pagedUsers);

        return resultPage;
    }

    @Override
    public AttendanceMonthlySummaryPage paginatedSummaryByCompanyAndMonth(Long companyId,
                                                                          Integer month,
                                                                          Integer year,
                                                                          String name,
                                                                          Integer page) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        Page<User> pagedUsers;
        if (name.isEmpty())
            pagedUsers  = userService.readByCompanyId(companyId, page);
        else {
            pagedUsers = userService.searchAllActiveByCompanyIdAndName(companyId, name, name, name, page);
        }

        List<AttendanceMonthlySummaryDTO> dtos = new ArrayList<>();

        if (pagedUsers.getContent().size() > 0) {
            for (User user: pagedUsers.getContent()) {
                dtos.add(this.generateMonthlySummaryForUser(user, month, year));
            }
        }

        AttendanceMonthlySummaryPage resultPage = new AttendanceMonthlySummaryPage(dtos, new PageRequest(page, PageAttr.PAGE_SIZE));
        resultPage.setValues(pagedUsers);

        return resultPage;
    }

    public List<AttendanceMonthlySummaryDTO> test(Long companyId, Integer month, Integer year) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        List<User> pagedUsers = userService.readByCompanyId(companyId);

        List<AttendanceMonthlySummaryDTO> dtos = new ArrayList<>();

        if (pagedUsers.size() > 0) {
            for (User user: pagedUsers) {
                dtos.add(this.generateMonthlySummaryForUser(user, month, year));
            }
        }

        return dtos;
    }

    @Override
    public AttendanceMonthlySummaryDTO generateMonthlySummaryForUser(User user, Integer month, Integer year) throws Exception {
        if (!isValidMonth(month) || !isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year found!");
        }

        List<LeaveSummaryDTO> leaves = leaveService.getAllMonthlyLeaveSummary(user.getId(), year, month);
        List<TaskResponseDTO> holidays = holidayService.readDTOByMonthAndYear(month, year, user.getCompany().getId());
        List<OfficeHourDateDTO> weekends = officeHourService.readAllDatesByWeekends(month, year);

        return this.summaryByUserAndMonth(user, month, year, leaves, holidays, weekends);
    }

    @Override
    public List<AttendanceDayTrackDTO> readAllByUserAndMonth(Long userId, Integer month, Integer year) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (!isValidMonth(month) || !isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year found!");
        }

        List<AttendanceDayTrackDTO> dayTrackDTOS = new ArrayList<>();

        Calendar calStart = DateTimeUtils.getStartOfMonth(month, year);
        Calendar calEnd = DateTimeUtils.getEndOfMonth(month, year);

        while (calStart.getTime().before(calEnd.getTime())) {
            dayTrackDTOS.add(this.countLateAndEarly(user.getId(), calStart.getTime()));
            calStart.add(Calendar.DATE, 1);
        }

        return dayTrackDTOS;
    }

    @Override
    public List<AttendanceDayTrackDTO> getAllLateUsersByDate(Long companyId, Date fromDate) throws Exception {
        List<User> users = userService.readByCompanyId(companyId);
        List<AttendanceDayTrackDTO> dayTrackDTOS = new ArrayList<>();
        AttendanceDayTrackDTO dayTrackDTO;

        if (users != null && users.size() > 0) {
            for (User user: users) {
                dayTrackDTO = new AttendanceDayTrackDTO();
                dayTrackDTO = this.countLateAndEarly(user.getId(), fromDate);

                if (dayTrackDTO.isLate() || dayTrackDTO.isLeftEarly()) {
                    dayTrackDTOS.add(dayTrackDTO);
                }
            }
        }

        return dayTrackDTOS;
    }

    @Override
    public List<UserSmallResponseDTO> getAllAbsentUsersByDate(Long companyId, Date fromDate) throws Exception {
        List<User> users = userService.readByCompanyId(companyId);
        List<UserSmallResponseDTO> userSmallResponseDTOS = new ArrayList<>();

        if (users != null && users.size() > 0) {
            for (User user: users) {
                if (this.checkAbsent(user.getId(), fromDate)) {
                    userSmallResponseDTOS.add(userSmallResponseMapper.map(user));
                }
            }
        }

        return userSmallResponseDTOS;
    }

    @Override
    public AttendanceDailySummaryDTO getAttendanceSummaryByDate(Long companyId,
                                                                Long departmentId,
                                                                Long officeCodeId,
                                                                Date fromDate) throws Exception {
        List<User> users = userService.listActiveUsers(companyId, departmentId, officeCodeId);
        List<UserSmallResponseDTO> absent = new ArrayList<>();
        List<AttendanceDayTrackDTO> lateOrEarly = new ArrayList<>();
        List<AttendanceDayTrackDTO> present = new ArrayList<>();

        List<AttendanceDayTrackDTO> isLate = new ArrayList<>();
        List<AttendanceDayTrackDTO> leftEarly = new ArrayList<>();

        AttendanceDayTrackDTO dayTrackDTO;

        if (users != null && users.size() > 0) {
            for (User user: users) {
                if (this.checkAbsent(user.getId(), fromDate)) {
                    absent.add(userSmallResponseMapper.map(user));
                } else {
                    dayTrackDTO = new AttendanceDayTrackDTO();
                    dayTrackDTO = this.countLateAndEarly(user.getId(), fromDate);

                    present.add(dayTrackDTO);

                    if (dayTrackDTO.isLate() || dayTrackDTO.isLeftEarly()) {
                        lateOrEarly.add(dayTrackDTO);

                        if (dayTrackDTO.isLate())
                            isLate.add(dayTrackDTO);

                        if (dayTrackDTO.isLeftEarly())
                            leftEarly.add(dayTrackDTO);
                    }
//                    else {
//                        present.add(dayTrackDTO);
//                    }
                }
            }
        }

        AttendanceDailySummaryDTO dto = new AttendanceDailySummaryDTO();
        dto.setAbsent(absent);
        dto.setLateOrEarly(lateOrEarly);
        dto.setPresent(present);

        dto.setIsLate(isLate);
        dto.setLeftEarly(leftEarly);

        return dto;
    }
}
