package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.domain.PayrollBreakdown;
import io.swagger.annotations.ApiModel;

import java.util.Date;
import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPayrollDTO {

    private UserSmallResponseDTO paidToUser;

    private Integer month;

    private Integer year;

    private Double salaryAmount;

    private Double approvedAllowance;

    private Integer lateCount;

    private Integer absentCount;

    private Integer workingDayCount;

    private Integer workingDayElapsed;

    private Integer leaveCount;

    private Integer unaccountedLeaveCount;

    private Integer unpaidLeaveCount;

    private Integer specialLeaveCount;

    private Integer unaccountedEarnedLeaveCount;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date createdOn;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date paidOn;

    private UserSmallResponseDTO approvedByUser;

    // Real time variables;

    private Double latePenalty = 0.0;

    private Double absentPenalty = 0.0;

    private Double leavePenalty = 0.0;

    private Integer accountedLeaves = 0;

    private Double earnedLeaveBonus = 0.0;

    private Integer accountedEarnedLeaves = 0;

    private Double payable = 0.0;

    private Double perDaySalary = 0.0;

    private List<PayrollBreakdownDTO> breakdowns;

    public UserSmallResponseDTO getPaidToUser() {
        return paidToUser;
    }

    public void setPaidToUser(UserSmallResponseDTO paidToUser) {
        this.paidToUser = paidToUser;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(Double salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public Double getApprovedAllowance() {
        return approvedAllowance;
    }

    public void setApprovedAllowance(Double approvedAllowance) {
        this.approvedAllowance = approvedAllowance;
    }

    public Integer getLateCount() {
        return lateCount;
    }

    public void setLateCount(Integer lateCount) {
        this.lateCount = lateCount;
    }

    public Integer getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(Integer absentCount) {
        this.absentCount = absentCount;
    }

    public Integer getWorkingDayCount() {
        return workingDayCount;
    }

    public void setWorkingDayCount(Integer workingDayCount) {
        this.workingDayCount = workingDayCount;
    }

    public Integer getWorkingDayElapsed() {
        return workingDayElapsed;
    }

    public void setWorkingDayElapsed(Integer workingDayElapsed) {
        this.workingDayElapsed = workingDayElapsed;
    }

    public Integer getLeaveCount() {
        return leaveCount;
    }

    public void setLeaveCount(Integer leaveCount) {
        this.leaveCount = leaveCount;
    }

    public Integer getUnaccountedLeaveCount() {
        return unaccountedLeaveCount;
    }

    public void setUnaccountedLeaveCount(Integer unaccountedLeaveCount) {
        this.unaccountedLeaveCount = unaccountedLeaveCount;
    }

    public Integer getUnpaidLeaveCount() {
        return unpaidLeaveCount;
    }

    public void setUnpaidLeaveCount(Integer unpaidLeaveCount) {
        this.unpaidLeaveCount = unpaidLeaveCount;
    }

    public Integer getSpecialLeaveCount() {
        return specialLeaveCount;
    }

    public void setSpecialLeaveCount(Integer specialLeaveCount) {
        this.specialLeaveCount = specialLeaveCount;
    }

    public Integer getUnaccountedEarnedLeaveCount() {
        return unaccountedEarnedLeaveCount;
    }

    public void setUnaccountedEarnedLeaveCount(Integer unaccountedEarnedLeaveCount) {
        this.unaccountedEarnedLeaveCount = unaccountedEarnedLeaveCount;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getPaidOn() {
        return paidOn;
    }

    public void setPaidOn(Date paidOn) {
        this.paidOn = paidOn;
    }

    public UserSmallResponseDTO getApprovedByUser() {
        return approvedByUser;
    }

    public void setApprovedByUser(UserSmallResponseDTO approvedByUser) {
        this.approvedByUser = approvedByUser;
    }

    public Double getLatePenalty() {
        return latePenalty;
    }

    public void setLatePenalty(Double latePenalty) {
        this.latePenalty = latePenalty;
    }

    public Double getAbsentPenalty() {
        return absentPenalty;
    }

    public void setAbsentPenalty(Double absentPenalty) {
        this.absentPenalty = absentPenalty;
    }

    public Double getLeavePenalty() {
        return leavePenalty;
    }

    public void setLeavePenalty(Double leavePenalty) {
        this.leavePenalty = leavePenalty;
    }

    public Integer getAccountedLeaves() {
        return accountedLeaves;
    }

    public void setAccountedLeaves(Integer accountedLeaves) {
        this.accountedLeaves = accountedLeaves;
    }

    public Double getEarnedLeaveBonus() {
        return earnedLeaveBonus;
    }

    public void setEarnedLeaveBonus(Double earnedLeaveBonus) {
        this.earnedLeaveBonus = earnedLeaveBonus;
    }

    public Integer getAccountedEarnedLeaves() {
        return accountedEarnedLeaves;
    }

    public void setAccountedEarnedLeaves(Integer accountedEarnedLeaves) {
        this.accountedEarnedLeaves = accountedEarnedLeaves;
    }

    public Double getPayable() {
        return payable;
    }

    public void setPayable(Double payable) {
        this.payable = payable;
    }

    public Double getPerDaySalary() {
        return perDaySalary;
    }

    public void setPerDaySalary(Double perDaySalary) {
        this.perDaySalary = perDaySalary;
    }

    public List<PayrollBreakdownDTO> getBreakdowns() {
        return breakdowns;
    }

    public void setBreakdowns(List<PayrollBreakdownDTO> breakdowns) {
        this.breakdowns = breakdowns;
    }

    @Override
    public String toString() {
        return "UserPayrollDTO{" +
                "paidToUser=" + paidToUser +
                ", month=" + month +
                ", year=" + year +
                ", salaryAmount=" + salaryAmount +
                ", approvedAllowance=" + approvedAllowance +
                ", lateCount=" + lateCount +
                ", absentCount=" + absentCount +
                ", workingDayCount=" + workingDayCount +
                ", workingDayElapsed=" + workingDayElapsed +
                ", leaveCount=" + leaveCount +
                ", unaccountedLeaveCount=" + unaccountedLeaveCount +
                ", unpaidLeaveCount=" + unpaidLeaveCount +
                ", specialLeaveCount=" + specialLeaveCount +
                ", unaccountedEarnedLeaveCount=" + unaccountedEarnedLeaveCount +
                ", createdOn=" + createdOn +
                ", paidOn=" + paidOn +
                ", approvedByUser=" + approvedByUser +
                ", latePenalty=" + latePenalty +
                ", absentPenalty=" + absentPenalty +
                ", leavePenalty=" + leavePenalty +
                ", accountedLeaves=" + accountedLeaves +
                ", earnedLeaveBonus=" + earnedLeaveBonus +
                ", accountedEarnedLeaves=" + accountedEarnedLeaves +
                ", payable=" + payable +
                ", perDaySalary=" + perDaySalary +
                ", breakdowns=" + breakdowns +
                '}';
    }
}
