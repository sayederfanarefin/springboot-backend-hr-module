package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.SalaryBreakdown;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalaryBreakdownCheckDTO {

    private SalaryBreakdown breakdown;

    private boolean isChecked;

    public SalaryBreakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(SalaryBreakdown breakdown) {
        this.breakdown = breakdown;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "SalaryBreakdownCheckDTO{" +
                "breakdown=" + breakdown +
                ", isChecked=" + isChecked +
                '}';
    }
}
