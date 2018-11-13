package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttendanceDayTrackDTO {

    private UserSmallResponseDTO userSmallResponseDTO;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date forDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date inTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date outTime;

    private Integer lateCount = 0;

    private Integer earlyCount = 0;

    private Integer clockCount = 0;

    private boolean isLate = false;

    private boolean leftEarly = false;

    public UserSmallResponseDTO getUserSmallResponseDTO() {
        return userSmallResponseDTO;
    }

    public void setUserSmallResponseDTO(UserSmallResponseDTO userSmallResponseDTO) {
        this.userSmallResponseDTO = userSmallResponseDTO;
    }

    public Date getForDate() {
        return forDate;
    }

    public void setForDate(Date forDate) {
        this.forDate = forDate;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Integer getLateCount() {
        return lateCount;
    }

    public void setLateCount(Integer lateCount) {
        this.lateCount = lateCount;
    }

    public Integer getEarlyCount() {
        return earlyCount;
    }

    public void setEarlyCount(Integer earlyCount) {
        this.earlyCount = earlyCount;
    }

    public Integer getClockCount() {
        return clockCount;
    }

    public void setClockCount(Integer clockCount) {
        this.clockCount = clockCount;
    }

    public boolean isLate() {
        return isLate;
    }

    public void setLate(boolean late) {
        isLate = late;
    }

    public boolean isLeftEarly() {
        return leftEarly;
    }

    public void setLeftEarly(boolean leftEarly) {
        this.leftEarly = leftEarly;
    }

    @Override
    public String toString() {
        return "AttendanceDayTrackDTO{" +
                "userSmallResponseDTO=" + userSmallResponseDTO +
                ", forDate=" + forDate +
                ", inTime=" + inTime +
                ", outTime=" + outTime +
                ", lateCount=" + lateCount +
                ", earlyCount=" + earlyCount +
                ", clockCount=" + clockCount +
                ", isLate=" + isLate +
                ", leftEarly=" + leftEarly +
                '}';
    }
}
