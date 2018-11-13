package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.Salary;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSalaryDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Integer month;

    @NotNull
    private Integer year;

    private Salary salary;

    private List<SalaryBreakdownDTO> dtos;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public List<SalaryBreakdownDTO> getDtos() {
        return dtos;
    }

    public void setDtos(List<SalaryBreakdownDTO> dtos) {
        this.dtos = dtos;
    }

    @Override
    public String toString() {
        return "UserSalaryDTO{" +
                "userId=" + userId +
                ", month=" + month +
                ", year=" + year +
                ", salary=" + salary +
                ", dtos=" + dtos +
                '}';
    }
}
