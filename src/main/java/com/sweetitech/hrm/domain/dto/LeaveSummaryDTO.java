package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveSummaryDTO {

    private Integer dayNumber;

    private Long leaveId;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date leaveDate;

    private String reason;

    private String leaveType;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date requestedOn;

    private UserResponseDTO requestedBy;

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public Date getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(Date requestedOn) {
        this.requestedOn = requestedOn;
    }

    public UserResponseDTO getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(UserResponseDTO requestedBy) {
        this.requestedBy = requestedBy;
    }

    @Override
    public String toString() {
        return "LeaveSummaryDTO{" +
                "dayNumber=" + dayNumber +
                ", leaveId=" + leaveId +
                ", leaveDate=" + leaveDate +
                ", reason='" + reason + '\'' +
                ", leaveType='" + leaveType + '\'' +
                ", requestedOn=" + requestedOn +
                ", requestedBy=" + requestedBy +
                '}';
    }
}
