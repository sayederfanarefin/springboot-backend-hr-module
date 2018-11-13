package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommissionListDTO {

    private List<CommissionDTO> commissions;

    private Integer number;

    public List<CommissionDTO> getCommissions() {
        return commissions;
    }

    public void setCommissions(List<CommissionDTO> commissions) {
        this.commissions = commissions;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "CommissionListDTO{" +
                "commissions=" + commissions +
                ", number=" + number +
                '}';
    }
}
