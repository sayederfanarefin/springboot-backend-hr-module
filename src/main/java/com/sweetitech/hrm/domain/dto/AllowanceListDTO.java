package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowanceListDTO {

    private List<AllowanceDTO> allowances;

    public List<AllowanceDTO> getAllowances() {
        return allowances;
    }

    public void setAllowances(List<AllowanceDTO> allowances) {
        this.allowances = allowances;
    }

    @Override
    public String toString() {
        return "AllowanceListDTO{" +
                "allowances=" + allowances +
                '}';
    }
}
