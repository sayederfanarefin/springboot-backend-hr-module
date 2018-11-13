package com.sweetitech.hrm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sweetitech.hrm.common.Constants;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "payrolls")
public class Payroll extends BaseEntity {

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date createdOn = new Date();

    @NotNull
    private Integer month;

    @NotNull
    private Integer year;

    @OneToOne
    private Salary salary;

    @NotNull
    @OneToOne
    private User paidToUser;

    @NotNull
    @OneToOne
    private User approvedByUser;

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

    public Payroll() {
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

    public User getPaidToUser() {
        return paidToUser;
    }

    public void setPaidToUser(User paidToUser) {
        this.paidToUser = paidToUser;
    }

    public User getApprovedByUser() {
        return approvedByUser;
    }

    public void setApprovedByUser(User approvedByUser) {
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

    @Override
    public String toString() {
        return "Payroll{" +
                "createdOn=" + createdOn +
                ", month=" + month +
                ", year=" + year +
                ", salary=" + salary +
                ", paidToUser=" + paidToUser +
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
