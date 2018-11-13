package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EarnedLeaveDTO {

    private UserSmallResponseDTO user;

    private Integer consumed;

    private Integer allocated;

    private Integer year;

    public UserSmallResponseDTO getUser() {
        return user;
    }

    public void setUser(UserSmallResponseDTO user) {
        this.user = user;
    }

    public Integer getConsumed() {
        return consumed;
    }

    public void setConsumed(Integer consumed) {
        this.consumed = consumed;
    }

    public Integer getAllocated() {
        return allocated;
    }

    public void setAllocated(Integer allocated) {
        this.allocated = allocated;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "EarnedLeaveDTO{" +
                "user=" + user +
                ", consumed=" + consumed +
                ", allocated=" + allocated +
                ", year=" + year +
                '}';
    }
}
