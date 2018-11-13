package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.AllocatedLeaves;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveUserSummaryDTO {

    private UserSmallResponseDTO user;

    private Integer year;

    private Integer casualLeave = 0;

    private Integer earnLeave = 0;

    private Integer sickLeave = 0;

    private Integer leaveWithoutPay = 0;

    private Integer specialLeave = 0;

    private AllocatedLeaves allocated;

    public UserSmallResponseDTO getUser() {
        return user;
    }

    public void setUser(UserSmallResponseDTO user) {
        this.user = user;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public AllocatedLeaves getAllocated() {
        return allocated;
    }

    public void setAllocated(AllocatedLeaves allocated) {
        this.allocated = allocated;
    }

    @Override
    public String toString() {
        return "LeaveUserSummaryDTO{" +
                "user=" + user +
                ", year=" + year +
                ", casualLeave=" + casualLeave +
                ", earnLeave=" + earnLeave +
                ", sickLeave=" + sickLeave +
                ", leaveWithoutPay=" + leaveWithoutPay +
                ", specialLeave=" + specialLeave +
                ", allocated=" + allocated +
                '}';
    }
}
