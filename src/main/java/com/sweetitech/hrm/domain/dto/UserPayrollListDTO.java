package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPayrollListDTO {

    private UserSmallResponseDTO user;

    private PayrollDTO payroll;

    public UserSmallResponseDTO getUser() {
        return user;
    }

    public void setUser(UserSmallResponseDTO user) {
        this.user = user;
    }

    public PayrollDTO getPayroll() {
        return payroll;
    }

    public void setPayroll(PayrollDTO payroll) {
        this.payroll = payroll;
    }

    @Override
    public String toString() {
        return "UserPayrollListDTO{" +
                "user=" + user +
                ", payroll=" + payroll +
                '}';
    }
}
