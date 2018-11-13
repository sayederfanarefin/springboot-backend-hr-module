package com.sweetitech.hrm.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "leaves")
public class Leave extends BaseEntity {

    private String reason;

    @NotNull
    private Date fromDate;

    @NotNull
    private Date toDate;

    @NotNull
    private Integer totalDays;

    private Integer casualLeave;

    private Integer earnLeave;

    private Integer sickLeave;

    private Integer leaveWithoutPay;

    private Integer specialLeave;

    @Size(min = 2, max = 100)
    private String address;

    @Size(min = 6)
    private String phone;

    @NotNull
    private Date requestedOn = new Date();

    @NotNull
    @OneToOne
    private User requestedBy;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private LeaveStatus leaveStatus;

    public Leave() {
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Integer getCasualLeave() {
        return casualLeave;
    }

    public void setCasualLeave(Integer casualLeave) {
        this.casualLeave = casualLeave;
    }

    public Integer getEarnLeave() {
        return earnLeave;
    }

    public void setEarnLeave(Integer earnLeave) {
        this.earnLeave = earnLeave;
    }

    public Integer getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(Integer sickLeave) {
        this.sickLeave = sickLeave;
    }

    public Integer getLeaveWithoutPay() {
        return leaveWithoutPay;
    }

    public void setLeaveWithoutPay(Integer leaveWithoutPay) {
        this.leaveWithoutPay = leaveWithoutPay;
    }

    public Integer getSpecialLeave() {
        return specialLeave;
    }

    public void setSpecialLeave(Integer specialLeave) {
        this.specialLeave = specialLeave;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(Date requestedOn) {
        this.requestedOn = requestedOn;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public LeaveStatus getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatus leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "reason='" + reason + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", totalDays=" + totalDays +
                ", casualLeave=" + casualLeave +
                ", earnLeave=" + earnLeave +
                ", sickLeave=" + sickLeave +
                ", leaveWithoutPay=" + leaveWithoutPay +
                ", specialLeave=" + specialLeave +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", requestedOn=" + requestedOn +
                ", requestedBy=" + requestedBy +
                ", leaveStatus=" + leaveStatus +
                '}';
    }
}
