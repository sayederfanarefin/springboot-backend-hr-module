package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalaryBreakdownRelationDTO {

    private Long salaryId;

    private Long breakdownId;

    public Long getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Long salaryId) {
        this.salaryId = salaryId;
    }

    public Long getBreakdownId() {
        return breakdownId;
    }

    public void setBreakdownId(Long breakdownId) {
        this.breakdownId = breakdownId;
    }

    @Override
    public String toString() {
        return "SalaryBreakdownRelationDTO{" +
                "salaryId=" + salaryId +
                ", breakdownId=" + breakdownId +
                '}';
    }
}
