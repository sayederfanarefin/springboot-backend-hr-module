package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveResponseDTO {

    private Long leaveId;

    private String reason;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date toDate;

    private Integer totalDays;

    private Integer casualLeave;

    private Integer earnLeave;

    private Integer sickLeave;

    private Integer leaveWithoutPay;

    private Integer specialLeave;

    private String address;

    private String phone;

    private Date requestedOn;

    private UserResponseDTO requestedByUser;

    private LeaveStatusResponseDTO leaveStatus;

    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
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

    public UserResponseDTO getRequestedByUser() {
        return requestedByUser;
    }

    public void setRequestedByUser(UserResponseDTO requestedByUser) {
        this.requestedByUser = requestedByUser;
    }

    public LeaveStatusResponseDTO getLeaveStatus() {
        return leaveStatus;
    }

    public void setLeaveStatus(LeaveStatusResponseDTO leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    @Override
    public String toString() {
        return "LeaveResponseDTO{" +
                "leaveId=" + leaveId +
                ", reason='" + reason + '\'' +
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
                ", requestedByUser=" + requestedByUser +
                ", leaveStatus=" + leaveStatus +
                '}';
    }
}
