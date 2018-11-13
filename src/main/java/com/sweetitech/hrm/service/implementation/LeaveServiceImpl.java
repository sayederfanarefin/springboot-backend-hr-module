package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.ConstantsUtil;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.*;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.repository.LeaveRepository;
import com.sweetitech.hrm.repository.LeaveStatusRepository;
import com.sweetitech.hrm.service.LeaveService;
import com.sweetitech.hrm.service.PushNotificationService;
import com.sweetitech.hrm.service.mapping.EarnedLeaveMapper;
import com.sweetitech.hrm.service.mapping.LeaveSummaryMapper;
import com.sweetitech.hrm.service.mapping.NotificationMapper;
import com.sweetitech.hrm.service.mapping.UserSmallResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {

    private LeaveRepository leaveRepository;
    private LeaveStatusRepository leaveStatusRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private OfficeHourServiceImpl officeHourService;

    @Autowired
    private LeaveSummaryMapper leaveSummaryMapper;

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    private HolidayServiceImpl holidayService;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    private EarnedLeaveMapper earnedLeaveMapper;

    @Autowired
    public LeaveServiceImpl(LeaveRepository leaveRepository, LeaveStatusRepository leaveStatusRepository) {
        this.leaveRepository = leaveRepository;
        this.leaveStatusRepository = leaveStatusRepository;
    }

    boolean isValidMonth(int month) {
        return (month > 0 && month <= 12);
    }

    boolean isValidYear(int year) {
        return (year >= 1800 && year <= 2100);
    }

    // Prepare Leave Summary For User
    public LeaveUserSummaryDTO getUserSummary(Long userId, Integer year) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        LeaveUserSummaryDTO dto = new LeaveUserSummaryDTO();

        UserSmallResponseDTO userDTO = new UserSmallResponseDTO();
        userDTO = userSmallResponseMapper.map(user);
        dto.setUser(userDTO);

        dto.setAllocated(user.getGrade().getAllocatedLeaves());

        List<Leave> leaves = this.readAllByYearAndUser(userId, year);

        int casualLeaves = 0, earnedLeaves = 0, sickLeaves = 0, leavesWithoutPay = 0, specialLeaves = 0;

        for (Leave leave: leaves) {
            if (leave.getCasualLeave() != null) {
                casualLeaves += leave.getCasualLeave();
            }
            if (leave.getEarnLeave() != null) {
                earnedLeaves += leave.getEarnLeave();
            }
            if (leave.getSickLeave() != null) {
                sickLeaves += leave.getSickLeave();
            }
            if (leave.getLeaveWithoutPay() != null) {
                leavesWithoutPay += leave.getLeaveWithoutPay();
            }
            if (leave.getSpecialLeave() != null) {
                specialLeaves += leave.getSpecialLeave();
            }
        }

        dto.setCasualLeave(casualLeaves);
        dto.setEarnLeave(earnedLeaves);
        dto.setSickLeave(sickLeaves);
        dto.setLeaveWithoutPay(leavesWithoutPay);
        dto.setSpecialLeave(specialLeaves);

        dto.setYear(year);

        return dto;
    }

    private LeaveMonthlySummaryDTO prepareMonthlyLeaveSummary(User user, Integer month, Integer year) throws Exception {
        List<LeaveSummaryDTO> leaves;

        leaves = new ArrayList<>();
        leaves = this.getAllMonthlyLeaveSummary(user.getId(), year, month);

        LeaveMonthlySummaryDTO dto = new LeaveMonthlySummaryDTO();
        dto.setUser(userSmallResponseMapper.map(user));
        dto.setLeaves(leaves);

        return dto;
    }

    @Override
    public Leave read(Long id) throws Exception {
        Leave leave = leaveRepository.getFirstById(id);
        if (leave == null) {
            throw new EntityNotFoundException("No leave with id: " + id);
        }

        return leave;
    }

    public LeaveStatus prepareStatus(String status, Long userId, String reason) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status value!");
        }

        LeaveStatus statusNew = new LeaveStatus();
        statusNew.setStatus(status);
        statusNew.setReason(reason);
        statusNew.setStatusDate(new Date());
        statusNew.setApprovedBy(user);

        return statusNew;
    }

    @Override
    public Leave create(Leave leave) throws Exception {
        Leave created = leaveRepository.save(leave);

        List<User> users = userService.readAllAdminsAndAccounts(leave.getRequestedBy().getCompany().getId());

        NotificationResponseDTO notification;
        if (users != null && users.size() > 0) {
            for (User user: users) {
                notification  = notificationService.create(notificationMapper.map(created, user.getId()));

                pushNotificationService.pushNotification(notification, user.getId());
            }
        }

        return created;
    }

    @Override
    public Leave update(Long id, Leave leave) throws Exception {
        Leave leaveOld = leaveRepository.getFirstById(id);
        if(leaveOld == null) {
            throw new EntityNotFoundException("No leave with id: " + id);
        }

        leave.setId(id);
        return leaveRepository.save(leave);
    }

    @Override
    public Leave updateStatus(Long id, LeaveStatus status) throws Exception {
        Leave leave = leaveRepository.getFirstById(id);
        if(leave == null) {
            throw new EntityNotFoundException("No leave with id: " + id);
        }

        LeaveStatus statusOld = leaveStatusRepository.getFirstById(leave.getLeaveStatus().getId());
        if (statusOld == null) {
            throw new EntityNotFoundException("The leave does not have any status!");
        }

        status.setId(leave.getLeaveStatus().getId());
        leaveStatusRepository.save(status);

        if (status.getStatus().equals(Constants.RequestStatus.APPROVED)
                || status.getStatus().equals(Constants.RequestStatus.DECLINED)) {
            NotificationResponseDTO notification  = notificationService.create(notificationMapper.map(leave, status.getStatus()));

            pushNotificationService.pushNotification(notification, leave.getRequestedBy().getId());
        }

        return leave;
    }

    @Override
    public String readStatus(Long id) throws Exception {
        Leave leave = leaveRepository.getFirstById(id);
        if(leave == null) {
            throw new EntityNotFoundException("No leave with id: " + id);
        }

        return leave.getLeaveStatus().getStatus();
    }

    @Override
    public Page<Leave> readAllByStatusAndUser(Long userId, String status, Integer page) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status value!");
        }

        return leaveRepository.findAllByStatusAndUser(status, userId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<Leave> readAllByStatusAndCompany(Long companyId, String status, Integer page) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status value!");
        }

        return leaveRepository.findAllByStatusAndCompany(status, companyId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public List<Leave> readAllMonthlyApprovedByUser(Long userId, String status, Integer year, Integer month) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (!isValidMonth(month) || !isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year found!");
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status value!");
        }

        return leaveRepository.findMonthlyApprovedByUser(userId, status, year, month);
    }

    @Override
    public List<LeaveSummaryDTO> getAllMonthlyLeaveSummary(Long userId, Integer year, Integer month) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        List<LeaveSummaryDTO> dtos = new ArrayList<>();
        List<Leave> leaves = this.readAllMonthlyApprovedByUser(userId, Constants.RequestStatus.APPROVED, year, month);
        if (leaves == null || leaves.size() <= 0) {
            return dtos;
        }

        for (Leave leave: leaves) {
            Calendar calStart = Calendar.getInstance();
            calStart.setTime(leave.getFromDate());
            calStart = DateTimeUtils.getStartOfDay(calStart);

            Calendar calEnd = Calendar.getInstance();
            calEnd.setTime(leave.getToDate());
            calEnd = DateTimeUtils.getEndOfDay(calEnd);

            if (calEnd.before(calStart)) {
                throw new EntityNotFoundException("To date is before start date!");
            }

            List<OfficeHour> weekends = officeHourService.readAllWeekEnds();

            Calendar temp = Calendar.getInstance();
            temp = calStart;

            int consideredDays = ((int) Duration.between(calStart.toInstant(), calEnd.toInstant()).toDays() + 1);

            for (int i = 0; i < consideredDays; i++) {
                boolean flag = false;

                for (OfficeHour weekend: weekends) {
                    if (temp.get(Calendar.DAY_OF_WEEK) == weekend.getDayNumber()
                            && (temp.get(Calendar.MONTH) == (month - 1))
                            && (temp.get(Calendar.YEAR) == year)) {
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    LeaveSummaryDTO dto = leaveSummaryMapper.map(leave, temp.getTime());
                    dtos.add(dto);
                }

                temp.add(Calendar.DATE, 1);
            }
        }

        return dtos;
    }

    @Override
    public List<LeaveMonthlySummaryDTO> getMonthlyLeaveSummaryForCompany(Long companyId, Integer year, Integer month) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        List<User> users = userService.readByCompanyId(companyId);
        List<LeaveSummaryDTO> leaves;

        List<LeaveMonthlySummaryDTO> summary = new ArrayList<>();

        for (User user: users) {
            leaves = new ArrayList<>();
            leaves = this.getAllMonthlyLeaveSummary(user.getId(), year, month);

            if (leaves.size() > 0) {
                LeaveMonthlySummaryDTO dto = new LeaveMonthlySummaryDTO();
                dto.setUser(userSmallResponseMapper.map(user));
                dto.setLeaves(leaves);

                summary.add(dto);
            }
        }

        return summary;
    }

    @Override
    public List<LeaveMonthlySummaryDTO> getMonthlyLeaveSummaryForCompany(Long companyId,
                                                                         Long departmentId,
                                                                         Long officeCodeId,
                                                                         Integer month,
                                                                         Integer year) throws Exception {
        if (!DateValidator.isValidYear(year) || !DateValidator.isValidMonth(month)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        List<User> users = userService.listActiveUsers(companyId, departmentId, officeCodeId);

        List<LeaveMonthlySummaryDTO> summary = new ArrayList<>();

        for (User user: users) {
            LeaveMonthlySummaryDTO dto = this.prepareMonthlyLeaveSummary(user, month, year);
            if (dto.getLeaves().size() > 0) {
                summary.add(dto);
            }
        }

        return summary;
    }

    @Override
    public Integer getNumberForMonthAndYear(Long userId, String status, Integer year, Integer month) throws Exception {
        List<Leave> leaves = this.readAllMonthlyApprovedByUser(userId, status, year, month);

        if (leaves == null)
            return 0;

        int leavesTaken = 0;

        for (Leave leave: leaves) {
            leavesTaken += leave.getTotalDays();
        }

        return leavesTaken;
    }

    @Override
    public List<Leave> readAllByYearAndUser(Long userId, Integer year) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (!isValidYear(year)) {
            throw new EntityNotFoundException("Invalid year found!");
        }

        return leaveRepository.findYearlyApprovedByUser(userId, Constants.RequestStatus.APPROVED, year);
    }

    @Override
    public Integer getLeaveCount(Date fromDate, Date toDate, Long companyId) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(fromDate);
        calStart = DateTimeUtils.getStartOfDay(calStart);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(toDate);
        calEnd = DateTimeUtils.getEndOfDay(calEnd);

        if (calEnd.before(calStart)) {
            throw new EntityNotFoundException("To date is before start date!");
        }

        List<OfficeHour> weekends = officeHourService.readAllWeekEnds();

        Calendar temp = Calendar.getInstance();
        temp = calStart;
        long dayToAdd = 0;
        boolean flag = false;

        int consideredDays = ((int) Duration.between(calStart.toInstant(), calEnd.toInstant()).toDays() + 1);

        for (int i = 0; i < consideredDays; i++) {
            flag = false;

            for (OfficeHour weekend: weekends) {
                if (temp.get(Calendar.DAY_OF_WEEK) == weekend.getDayNumber()) {
                    flag = true;
                    break;
                }
            }

            if (holidayService.readHoliday(temp.getTime(), Constants.Tasks.HOLIDAY, Constants.RequestStatus.APPROVED, companyId) != null) {
                flag = true;
            }

            if (!flag) {
                dayToAdd++;
            }

            temp.add(Calendar.DATE, 1);
        }

        return (int) dayToAdd;
    }

    @Override
    public List<LeaveUserSummaryDTO> getYearlySummaryByCompany(Long companyId,
                                                               Long departmentId,
                                                               Long officeCodeId,
                                                               Integer year) throws Exception {
        if (!DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid year found!");
        }
        List<User> users = userService.listActiveUsers(companyId, departmentId, officeCodeId);

        List<LeaveUserSummaryDTO> dtos = new ArrayList<>();

        if (users != null && users.size() > 0) {
            for (User user: users) {
                dtos.add(this.getUserSummary(user.getId(), year));
            }
        }

        return dtos;
    }

    @Override
    public List<EarnedLeaveDTO> getYearlyEarnedLeaveSummaryByCompany(Long companyId,
                                                                     Long departmentId,
                                                                     Long officeCodeId,
                                                                     Integer year) throws Exception {
        List<LeaveUserSummaryDTO> summary = this.getYearlySummaryByCompany(companyId, departmentId, officeCodeId, year);

        List<EarnedLeaveDTO> dtos = new ArrayList<>();
        if (summary != null && summary.size() > 0) {
            for (LeaveUserSummaryDTO userSummary: summary) {
                dtos.add(earnedLeaveMapper.map(userSummary));
            }
        }

        return dtos;
    }
}
