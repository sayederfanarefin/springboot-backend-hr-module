package com.sweetitech.hrm.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.dto.PayrollDTO;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayrollPostDTO {

    private PayrollDTO payrollDTO;

    private List<PayrollBreakdown> breakdowns;

    public PayrollDTO getPayrollDTO() {
        return payrollDTO;
    }

    public void setPayrollDTO(PayrollDTO payrollDTO) {
        this.payrollDTO = payrollDTO;
    }

    public List<PayrollBreakdown> getBreakdowns() {
        return breakdowns;
    }

    public void setBreakdowns(List<PayrollBreakdown> breakdowns) {
        this.breakdowns = breakdowns;
    }

    @Override
    public String toString() {
        return "PayrollPostDTO{" +
                "payrollDTO=" + payrollDTO +
                ", breakdowns=" + breakdowns +
                '}';
    }
}
