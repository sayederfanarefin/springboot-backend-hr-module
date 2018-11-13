package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.domain.Salary;
import com.sweetitech.hrm.domain.User;
import io.swagger.annotations.ApiModel;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayrollDTO {

    private Long payrollId;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date createdOn;

    private Integer month;

    private Integer year;

    private Long salaryId;

    private Salary salary;

    private Long paidToUserId;

    private UserSmallResponseDTO paidToUser;

    private Long approvedByUserId;

    private UserSmallResponseDTO approvedByUser;

    private Integer absent = 0;

    private Double absentPenalty = 0.0;

    private Integer late = 0;

    private Double latePenalty = 0.0;

    private Integer leavesTaken = 0; // for month

    private Integer unpaidLeavesTaken = 0; // for month

    private Integer specialLeavesTaken = 0;

    private Integer accountedLeaves = 0;

    private Double leavePenalty = 0.0;

    private Integer accountedEarnedLeaves = 0;

    private Double earnedLeaveBonus = 0.0;

    private Double approvedAllowance = 0.0;

    private Double totalPayable = 0.0;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date payedOn;

    public Long getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(Long payrollId) {
        this.payrollId = payrollId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
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

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public UserSmallResponseDTO getPaidToUser() {
        return paidToUser;
    }

    public void setPaidToUser(UserSmallResponseDTO paidToUser) {
        this.paidToUser = paidToUser;
    }

    public UserSmallResponseDTO getApprovedByUser() {
        return approvedByUser;
    }

    public void setApprovedByUser(UserSmallResponseDTO approvedByUser) {
        this.approvedByUser = approvedByUser;
    }

    public Integer getAbsent() {
        return absent;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }

    public Double getAbsentPenalty() {
        return absentPenalty;
    }

    public void setAbsentPenalty(Double absentPenalty) {
        this.absentPenalty = absentPenalty;
    }

    public Integer getLate() {
        return late;
    }

    public void setLate(Integer late) {
        this.late = late;
    }

    public Double getLatePenalty() {
        return latePenalty;
    }

    public void setLatePenalty(Double latePenalty) {
        this.latePenalty = latePenalty;
    }

    public Integer getLeavesTaken() {
        return leavesTaken;
    }

    public void setLeavesTaken(Integer leavesTaken) {
        this.leavesTaken = leavesTaken;
    }

    public Integer getUnpaidLeavesTaken() {
        return unpaidLeavesTaken;
    }

    public void setUnpaidLeavesTaken(Integer unpaidLeavesTaken) {
        this.unpaidLeavesTaken = unpaidLeavesTaken;
    }

    public Integer getSpecialLeavesTaken() {
        return specialLeavesTaken;
    }

    public void setSpecialLeavesTaken(Integer specialLeavesTaken) {
        this.specialLeavesTaken = specialLeavesTaken;
    }

    public Integer getAccountedLeaves() {
        return accountedLeaves;
    }

    public void setAccountedLeaves(Integer accountedLeaves) {
        this.accountedLeaves = accountedLeaves;
    }

    public Double getLeavePenalty() {
        return leavePenalty;
    }

    public void setLeavePenalty(Double leavePenalty) {
        this.leavePenalty = leavePenalty;
    }

    public Integer getAccountedEarnedLeaves() {
        return accountedEarnedLeaves;
    }

    public void setAccountedEarnedLeaves(Integer accountedEarnedLeaves) {
        this.accountedEarnedLeaves = accountedEarnedLeaves;
    }

    public Double getEarnedLeaveBonus() {
        return earnedLeaveBonus;
    }

    public void setEarnedLeaveBonus(Double earnedLeaveBonus) {
        this.earnedLeaveBonus = earnedLeaveBonus;
    }

    public Double getApprovedAllowance() {
        return approvedAllowance;
    }

    public void setApprovedAllowance(Double approvedAllowance) {
        this.approvedAllowance = approvedAllowance;
    }

    public Double getTotalPayable() {
        return totalPayable;
    }

    public void setTotalPayable(Double totalPayable) {
        this.totalPayable = totalPayable;
    }

    public Date getPayedOn() {
        return payedOn;
    }

    public void setPayedOn(Date payedOn) {
        this.payedOn = payedOn;
    }

    public Long getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Long salaryId) {
        this.salaryId = salaryId;
    }

    public Long getPaidToUserId() {
        return paidToUserId;
    }

    public void setPaidToUserId(Long paidToUserId) {
        this.paidToUserId = paidToUserId;
    }

    public Long getApprovedByUserId() {
        return approvedByUserId;
    }

    public void setApprovedByUserId(Long approvedByUserId) {
        this.approvedByUserId = approvedByUserId;
    }

    @Override
    public String toString() {
        return "PayrollDTO{" +
                "payrollId=" + payrollId +
                ", createdOn=" + createdOn +
                ", month=" + month +
                ", year=" + year +
                ", salaryId=" + salaryId +
                ", salary=" + salary +
                ", paidToUserId=" + paidToUserId +
                ", paidToUser=" + paidToUser +
                ", approvedByUserId=" + approvedByUserId +
                ", approvedByUser=" + approvedByUser +
                ", absent=" + absent +
                ", absentPenalty=" + absentPenalty +
                ", late=" + late +
                ", latePenalty=" + latePenalty +
                ", leavesTaken=" + leavesTaken +
                ", unpaidLeavesTaken=" + unpaidLeavesTaken +
                ", specialLeavesTaken=" + specialLeavesTaken +
                ", accountedLeaves=" + accountedLeaves +
                ", leavePenalty=" + leavePenalty +
                ", accountedEarnedLeaves=" + accountedEarnedLeaves +
                ", earnedLeaveBonus=" + earnedLeaveBonus +
                ", approvedAllowance=" + approvedAllowance +
                ", totalPayable=" + totalPayable +
                ", payedOn=" + payedOn +
                '}';
    }
}
