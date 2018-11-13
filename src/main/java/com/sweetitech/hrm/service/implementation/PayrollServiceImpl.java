package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.*;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.domain.relation.SalaryBreakdownRelation;
import com.sweetitech.hrm.domain.relation.UserSalaryRelation;
import com.sweetitech.hrm.repository.PayrollBreakdownRepository;
import com.sweetitech.hrm.repository.PayrollRepository;
import com.sweetitech.hrm.service.PayrollService;
import com.sweetitech.hrm.service.mapping.PayrollBreakdownMapper;
import com.sweetitech.hrm.service.mapping.PayrollMapper;
import com.sweetitech.hrm.service.mapping.UserSmallResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    private PayrollRepository payrollRepository;
    private PayrollBreakdownRepository payrollBreakdownRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    private UserSalaryServiceImpl userSalaryService;

    @Autowired
    private AllowanceServiceImpl allowanceService;

    @Autowired
    private LogServiceImpl logService;

    @Autowired
    private LeaveServiceImpl leaveService;

    @Autowired
    private SalaryBreakdownServiceImpl salaryBreakdownService;

    @Autowired
    private PayrollBreakdownMapper payrollBreakdownMapper;

    @Autowired
    private PayrollMapper payrollMapper;

    @Autowired
    public PayrollServiceImpl(PayrollRepository payrollRepository, PayrollBreakdownRepository payrollBreakdownRepository) {
        this.payrollRepository = payrollRepository;
        this.payrollBreakdownRepository = payrollBreakdownRepository;
    }

    @Override
    public Payroll read(Long userId, Integer month, Integer year) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }
        return payrollRepository.getFirstByMonthAndYearAndPaidToUserId(month, year, userId);
    }

    @Override
    public Payroll read(Long id) throws Exception {
        return payrollRepository.getFirstById(id);
    }

    @Override
    public List<Payroll> readAllByUserAndYear(Integer year, Long userId) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        if (!DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid year found!");
        }

        return payrollRepository.findAllByYearAndPaidToUserIdOrderByMonthAsc(year, userId);
    }

    @Override
    public UserPayrollDTO readPayrollForUser(Long userId, Integer month, Integer year) throws Exception {

        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        if (!DateValidator.isValidMonth(month)
                || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        UserPayrollDTO resultSet = new UserPayrollDTO();

        Payroll payroll = this.read(userId, month, year);
        if (payroll != null) {
            resultSet.setCreatedOn(payroll.getCreatedOn());
            resultSet.setPaidOn(payroll.getPayedOn());
            resultSet.setApprovedByUser(userSmallResponseMapper.map(payroll.getApprovedByUser()));
        }

        Double salaryAmount = 0.0;
        UserSalaryRelation relation = null;
        if (payroll == null) {
            relation = userSalaryService.readLatestByUserId(userId);
            if (relation != null) {
                salaryAmount = relation.getSalary().getAmount();
            }
        } else {
            salaryAmount = payroll.getSalary().getAmount();
        }
        resultSet.setSalaryAmount(salaryAmount);

        Double approvedAllowance = 0.0;
        if (payroll == null) {
            AllowanceSummary allowanceSummary = allowanceService.getSummary(month, year, userId);
            if (allowanceSummary != null) {
                approvedAllowance = allowanceSummary.getTotal();
            }
        } else {
            approvedAllowance = payroll.getApprovedAllowance();
        }
        resultSet.setApprovedAllowance(approvedAllowance);

        int lateCount = 0, absentCount = 0, workingDayCount = 0;
        if (payroll == null) {
            AttendanceMonthlySummaryDTO attendanceMonthlySummaryDTO = logService.generateMonthlySummaryForUser(user, month, year);
            if (attendanceMonthlySummaryDTO != null) {
                lateCount = attendanceMonthlySummaryDTO.getLateCount();
                absentCount = attendanceMonthlySummaryDTO.getAbsentCount();
                workingDayCount = attendanceMonthlySummaryDTO.getWorkingDayCount();
            }
        } else {
            lateCount = payroll.getLate();
            absentCount = payroll.getAbsent();
        }
        resultSet.setLateCount(lateCount);
        resultSet.setAbsentCount(absentCount);
        resultSet.setWorkingDayCount(workingDayCount);

        int leaveCount = 0, unpaidLeaveCount = 0, specialLeaveCount = 0;
        if (payroll == null) {
            leaveCount = leaveService.getNumberForMonthAndYear(userId, Constants.RequestStatus.APPROVED, year, month);
        } else {
            leaveCount = payroll.getLeavesTaken();
        }
        resultSet.setLeaveCount(leaveCount);

        int casualLeaveCount = 0, earnedLeaveCount = 0, sickLeaveCount = 0;
        if (payroll == null) {
            List<LeaveSummaryDTO> leaveSummaryDTOS = leaveService.getAllMonthlyLeaveSummary(userId, year, month);
            if (leaveSummaryDTOS != null && leaveSummaryDTOS.size() > 0) {
                for (LeaveSummaryDTO leaveSummaryDTO: leaveSummaryDTOS) {
                    if (leaveSummaryDTO.getLeaveType().equals(Constants.LeaveTypes.LEAVE_WITHOUT_PAY)) {
                        unpaidLeaveCount++;
                    }
                    if (leaveSummaryDTO.getLeaveType().equals(Constants.LeaveTypes.SPECIAL_LEAVE)) {
                        specialLeaveCount++;
                    }
                    if (leaveSummaryDTO.getLeaveType().equals(Constants.LeaveTypes.CASUAL_LEAVE)) {
                        casualLeaveCount++;
                    }
                    if (leaveSummaryDTO.getLeaveType().equals(Constants.LeaveTypes.EARN_LEAVE)) {
                        earnedLeaveCount++;
                    }
                    if (leaveSummaryDTO.getLeaveType().equals(Constants.LeaveTypes.SICK_LEAVE)) {
                        sickLeaveCount++;
                    }
                }
            }
        } else {
            unpaidLeaveCount = payroll.getUnpaidLeavesTaken();
            specialLeaveCount = payroll.getSpecialLeavesTaken();
        }
        resultSet.setUnpaidLeaveCount(unpaidLeaveCount);
        resultSet.setSpecialLeaveCount(specialLeaveCount);

        int totalUnattendedLeaves = 0;
        if (payroll == null) {
            AllocatedLeaves allocatedLeaves = user.getGrade().getAllocatedLeaves();
            if (allocatedLeaves != null) {
                if (casualLeaveCount > allocatedLeaves.getCasualLeave()) {
                    totalUnattendedLeaves += casualLeaveCount - allocatedLeaves.getCasualLeave();
                }
                if (earnedLeaveCount > allocatedLeaves.getEarnLeave()) {
                    totalUnattendedLeaves += earnedLeaveCount - allocatedLeaves.getEarnLeave();
                }
                if (sickLeaveCount > allocatedLeaves.getSickLeave()) {
                    totalUnattendedLeaves += sickLeaveCount - allocatedLeaves.getSickLeave();
                }
            }
        }

        int accountedLeaves = 0, accountedEarnedLeaves = 0;
        int unaccountedLeaves = 0, unaccountedEarnedLeaves = 0;
        if (payroll == null) {
            List<Payroll> payrolls = this.readAllByUserAndYear(year, userId);
            if (payrolls != null && payrolls.size() > 0) {
                for (Payroll payroll1: payrolls) {
                    accountedLeaves += payroll1.getAccountedLeaves();
                    accountedEarnedLeaves += payroll1.getAccountedEarnedLeaves();
                }
            }
            unaccountedLeaves = totalUnattendedLeaves - accountedLeaves;
            resultSet.setUnaccountedLeaveCount(unaccountedLeaves);
        } else {
            resultSet.setAccountedLeaves(payroll.getAccountedLeaves());
        }

        if (payroll == null) {
            unaccountedEarnedLeaves = earnedLeaveCount - accountedEarnedLeaves;
            resultSet.setUnaccountedEarnedLeaveCount(unaccountedEarnedLeaves);
        } else {
            resultSet.setAccountedEarnedLeaves(payroll.getAccountedEarnedLeaves());
        }

        // Real time values

        double perDaySalary = 0.0;
        YearMonth yearMonth = YearMonth.of(year, month);
        Integer totalDays = yearMonth.lengthOfMonth();
        if (totalDays != null) {
            perDaySalary = salaryAmount / totalDays;
        }

        resultSet.setPerDaySalary(perDaySalary);

        double latePenalty = (lateCount / 3) * perDaySalary;
        if (payroll == null)
            resultSet.setLatePenalty(latePenalty);
        else resultSet.setLatePenalty(payroll.getLatePenalty());

        double absentPenalty = (absentCount - leaveCount) * perDaySalary;
        if (payroll == null)
            resultSet.setAbsentPenalty(absentPenalty);
        else resultSet.setAbsentPenalty(payroll.getAbsentPenalty());

        double leavePenalty = unaccountedLeaves * perDaySalary;
        if (payroll == null)
            resultSet.setLeavePenalty(leavePenalty);
        else resultSet.setLeavePenalty(payroll.getLeavePenalty());

        if (payroll == null)
            resultSet.setAccountedLeaves(unaccountedLeaves);
        else resultSet.setAccountedLeaves(payroll.getAccountedLeaves());

        if (payroll == null)
            resultSet.setAccountedEarnedLeaves(unaccountedEarnedLeaves);
        else resultSet.setAccountedEarnedLeaves(payroll.getAccountedEarnedLeaves());

        double earnedLeaveBonus = unaccountedEarnedLeaves * perDaySalary;
        if (payroll == null)
            resultSet.setEarnedLeaveBonus(earnedLeaveBonus);
        else resultSet.setEarnedLeaveBonus(payroll.getEarnedLeaveBonus());

        if (payroll == null) {
            double payable = salaryAmount
                    - latePenalty
                    - absentPenalty
                    - leavePenalty
                    + earnedLeaveBonus;
            resultSet.setPayable(payable);
        } else {
            resultSet.setPayable(payroll.getTotalPayable());
        }

        if (payroll == null) {
            List<PayrollBreakdownDTO> breakdowns = new ArrayList<>();
            if (relation != null) {
                List<SalaryBreakdownRelation> relations = salaryBreakdownService.readAllRelationsBySalary(relation.getSalary().getId()).getRelations();
                if (relations != null && relations.size() > 0) {
                    for (SalaryBreakdownRelation relation1: relations) {
                        PayrollBreakdown breakdown = new PayrollBreakdown();
                        breakdown.setBreakdown(relation1.getBreakdown());
                        breakdown.setAmount((relation1.getBreakdown().getPercentage() / 100) * salaryAmount);

                        breakdowns.add(payrollBreakdownMapper.map(breakdown));
                    }
                }
            }
            resultSet.setBreakdowns(breakdowns);
        } else {
            List<PayrollBreakdown> breakdowns = payrollBreakdownRepository.findAllByPayrollId(payroll.getId());
            List<PayrollBreakdownDTO> dtos = new ArrayList<>();
            if (breakdowns != null && breakdowns.size() > 0) {
                for (PayrollBreakdown breakdown: breakdowns) {
                    dtos.add(payrollBreakdownMapper.map(breakdown));
                }
            }
            resultSet.setBreakdowns(dtos);
        }

        return resultSet;
    }

    @Override
    public List<UserPayrollListDTO> readAllUserPayrollByMonth(Long companyId,
                                                              Long departmentId,
                                                              Long officeCodeId,
                                                              Integer month,
                                                              Integer year) throws Exception {
        if (!DateValidator.isValidMonth(month)
                || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        List<UserPayrollListDTO> dtos = new ArrayList<>();

        List<User> users = userService.listActiveUsers(companyId, departmentId, officeCodeId);
        if (users != null && users.size() > 0) {
            for (User user: users) {
                UserPayrollListDTO dto = new UserPayrollListDTO();

                Payroll payroll = this.read(user.getId(), month, year);
                if (payroll != null)
                    dto.setPayroll(payrollMapper.map(payroll));

                dto.setUser(userSmallResponseMapper.map(user));

                dtos.add(dto);
            }
        }

        return dtos;
    }

    @Override
    public Payroll create(Payroll payroll) {
        return payrollRepository.save(payroll);
    }

    @Override
    public List<PayrollBreakdown> createPayrollBreakdowns(Payroll payroll, List<SalaryBreakdown> breakdowns) throws Exception {
        List<PayrollBreakdown> payrollBreakdowns = new ArrayList<>();

        if (breakdowns != null && breakdowns.size() > 0) {
            for (SalaryBreakdown breakdown: breakdowns) {
                PayrollBreakdown payrollBreakdown = new PayrollBreakdown();

                payrollBreakdown.setPayroll(payroll);
                payrollBreakdown.setBreakdown(breakdown);
                System.out.println(payroll.getSalary().getAmount() + " " + breakdown.getPercentage());
                payrollBreakdown.setAmount(payroll.getSalary().getAmount() * (breakdown.getPercentage() / 100));

                payrollBreakdowns.add(payrollBreakdownRepository.save(payrollBreakdown));
            }
        }

        return payrollBreakdowns;
    }

    @Override
    public List<PayrollBreakdown> readAllBreakdownsByPayroll(Long payrollId) throws Exception {
        if (this.read(payrollId) == null) {
            throw new EntityNotFoundException("No payrolls with id: " + payrollId);
        }

        return payrollBreakdownRepository.findAllByPayrollId(payrollId);
    }

    @Override
    public void removeBreakdowns(Long payrollId) throws Exception {
        List<PayrollBreakdown> breakdowns = this.readAllBreakdownsByPayroll(payrollId);

        if (breakdowns != null && breakdowns.size() > 0) {
            for (PayrollBreakdown breakdown: breakdowns) {
                payrollBreakdownRepository.delete(breakdown);
            }
        }
    }

    @Override
    public Payroll update(Long payrollId, Payroll payroll) throws Exception {
        if (this.read(payrollId) == null) {
            throw new EntityNotFoundException("No payrolls with id: " + payrollId);
        }

        payroll.setId(payrollId);
        payroll.setCreatedOn(new Date());
        return payrollRepository.save(payroll);
    }

    @Override
    public List<SalaryBreakdown> convert(List<PayrollBreakdown> breakdowns) throws Exception {
        List<SalaryBreakdown> salaryBreakdowns = new ArrayList<>();
        if (breakdowns != null && breakdowns.size() > 0) {
            for (PayrollBreakdown payrollBreakdown: breakdowns) {
                salaryBreakdowns.add(payrollBreakdown.getBreakdown());
            }
        }

        return salaryBreakdowns;
    }

    @Override
    public UserPayrollDTO approve(PayrollDTO dto, List<PayrollBreakdown> breakdowns) throws Exception {
        Payroll payroll = this.read(dto.getPaidToUserId(), dto.getMonth(), dto.getYear());

        if (payroll != null) {
            Payroll oldPayroll = payroll;
            payroll = this.update(payroll.getId(), payrollMapper.map(dto));

            this.removeBreakdowns(oldPayroll.getId());
            this.createPayrollBreakdowns(payroll, this.convert(breakdowns));

            return this.readPayrollForUser(payroll.getPaidToUser().getId(), payroll.getMonth(), payroll.getYear());
        }

        payroll = payrollRepository.save(payrollMapper.map(dto));

        System.out.println(payroll);
        this.createPayrollBreakdowns(payroll, this.convert(breakdowns));

        return this.readPayrollForUser(payroll.getPaidToUser().getId(), payroll.getMonth(), payroll.getYear());
    }

    @Override
    public UserPayrollDTO changeStatusToPaid(Long payrollId, Date paidOn) throws Exception {
        Payroll payroll = this.read(payrollId);
        if (payroll == null) {
            throw new EntityNotFoundException("No payrolls with id: " + payrollId);
        }

        payroll.setPayedOn(paidOn);
        PayrollDTO payrollDTO = payrollMapper.map(payrollRepository.save(payroll));

        return this.readPayrollForUser(payrollDTO.getPaidToUser().getUserId(), payrollDTO.getMonth(), payrollDTO.getYear());
    }
}
