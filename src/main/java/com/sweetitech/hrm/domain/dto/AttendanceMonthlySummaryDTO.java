package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttendanceMonthlySummaryDTO {

    private UserSmallResponseDTO user;

    private Integer month;

    private Integer year;

    private Integer leaveCount = 0;

    private Integer offDayCount = 0;

    private Integer lateCount = 0;

    private Integer earlyLeaveCount = 0;

    private Integer workingDayCount = 0;

    private Integer absentCount = 0;

    private Integer totalWorkingDays = 0;

    private Integer totalWeekends = 0;

    private Integer totalHolidays = 0;

    public AttendanceMonthlySummaryDTO(UserSmallResponseDTO user, Integer month, Integer year) {
        this.user = user;
        this.month = month;
        this.year = year;
    }

    public UserSmallResponseDTO getUser() {
        return user;
    }

    public void setUser(UserSmallResponseDTO user) {
        this.user = user;
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

    public Integer getLeaveCount() {
        return leaveCount;
    }

    public void setLeaveCount(Integer leaveCount) {
        this.leaveCount = leaveCount;
    }

    public Integer getOffDayCount() {
        return offDayCount;
    }

    public void setOffDayCount(Integer offDayCount) {
        this.offDayCount = offDayCount;
    }

    public Integer getLateCount() {
        return lateCount;
    }

    public void setLateCount(Integer lateCount) {
        this.lateCount = lateCount;
    }

    public Integer getEarlyLeaveCount() {
        return earlyLeaveCount;
    }

    public void setEarlyLeaveCount(Integer earlyLeaveCount) {
        this.earlyLeaveCount = earlyLeaveCount;
    }

    public Integer getWorkingDayCount() {
        return workingDayCount;
    }

    public void setWorkingDayCount(Integer workingDayCount) {
        this.workingDayCount = workingDayCount;
    }

    public Integer getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(Integer absentCount) {
        this.absentCount = absentCount;
    }

    public Integer getTotalWorkingDays() {
        return totalWorkingDays;
    }

    public void setTotalWorkingDays(Integer totalWorkingDays) {
        this.totalWorkingDays = totalWorkingDays;
    }

    public Integer getTotalWeekends() {
        return totalWeekends;
    }

    public void setTotalWeekends(Integer totalWeekends) {
        this.totalWeekends = totalWeekends;
    }

    public Integer getTotalHolidays() {
        return totalHolidays;
    }

    public void setTotalHolidays(Integer totalHolidays) {
        this.totalHolidays = totalHolidays;
    }

    @Override
    public String toString() {
        return "AttendanceMonthlySummaryDTO{" +
                "user=" + user +
                ", month=" + month +
                ", year=" + year +
                ", leaveCount=" + leaveCount +
                ", offDayCount=" + offDayCount +
                ", lateCount=" + lateCount +
                ", earlyLeaveCount=" + earlyLeaveCount +
                ", workingDayCount=" + workingDayCount +
                ", absentCount=" + absentCount +
                ", totalWorkingDays=" + totalWorkingDays +
                ", totalWeekends=" + totalWeekends +
                ", totalHolidays=" + totalHolidays +
                '}';
    }
}
