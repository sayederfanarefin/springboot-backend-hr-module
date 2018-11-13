package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowanceMonthlyDTO {

    AllowanceSummaryDTO allowanceSummary;

    List<AllowanceListObjectDTO> allowances;

    List<LeaveSummaryDTO> leaves;

    List<OfficeHourDateDTO> officeHours;

    List<TaskResponseDTO> holidays;

    public AllowanceSummaryDTO getAllowanceSummary() {
        return allowanceSummary;
    }

    public void setAllowanceSummary(AllowanceSummaryDTO allowanceSummary) {
        this.allowanceSummary = allowanceSummary;
    }

    public List<AllowanceListObjectDTO> getAllowances() {
        return allowances;
    }

    public void setAllowances(List<AllowanceListObjectDTO> allowances) {
        this.allowances = allowances;
    }

    public List<LeaveSummaryDTO> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<LeaveSummaryDTO> leaves) {
        this.leaves = leaves;
    }

    public List<OfficeHourDateDTO> getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(List<OfficeHourDateDTO> officeHours) {
        this.officeHours = officeHours;
    }

    public List<TaskResponseDTO> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<TaskResponseDTO> holidays) {
        this.holidays = holidays;
    }

    @Override
    public String toString() {
        return "AllowanceMonthlyDTO{" +
                "allowanceSummary=" + allowanceSummary +
                ", allowances=" + allowances +
                ", leaves=" + leaves +
                ", officeHours=" + officeHours +
                ", holidays=" + holidays +
                '}';
    }
}
