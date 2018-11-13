package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.Payroll;
import com.sweetitech.hrm.domain.SalaryBreakdown;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayrollBreakdownDTO {

    private Payroll payroll;

    private PayrollDTO payrollDTO;

    private SalaryBreakdown breakdown;

    private Double amount;

    public Payroll getPayroll() {
        return payroll;
    }

    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }

    public PayrollDTO getPayrollDTO() {
        return payrollDTO;
    }

    public void setPayrollDTO(PayrollDTO payrollDTO) {
        this.payrollDTO = payrollDTO;
    }

    public SalaryBreakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(SalaryBreakdown breakdown) {
        this.breakdown = breakdown;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PayrollBreakdownDTO{" +
                "payroll=" + payroll +
                ", payrollDTO=" + payrollDTO +
                ", breakdown=" + breakdown +
                ", amount=" + amount +
                '}';
    }
}
