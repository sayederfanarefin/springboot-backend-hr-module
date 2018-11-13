package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentDTO {

    @Size(min = 2, max = 100, message = "Department name must be between 2 to 100 characters!")
    private String name;

    @NotNull
    private Long companyId;

    private boolean isDeleted;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "DepartmentDTO{" +
                "name='" + name + '\'' +
                ", companyId=" + companyId +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
