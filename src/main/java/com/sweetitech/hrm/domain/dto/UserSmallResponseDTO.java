package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSmallResponseDTO {

    private Long userId;

    private String name;

    private Long companyId;

    private String companyName;

    private Long departmentId;

    private String departmentName;

    private String designation;

    private Long gradeId;

    private Integer gradeNumber;

    private String gradeName;

    private String officeCodeName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getGradeNumber() {
        return gradeNumber;
    }

    public void setGradeNumber(Integer gradeNumber) {
        this.gradeNumber = gradeNumber;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getOfficeCodeName() {
        return officeCodeName;
    }

    public void setOfficeCodeName(String officeCodeName) {
        this.officeCodeName = officeCodeName;
    }

    @Override
    public String toString() {
        return "UserSmallResponseDTO{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", designation='" + designation + '\'' +
                ", gradeId=" + gradeId +
                ", gradeNumber=" + gradeNumber +
                ", gradeName='" + gradeName + '\'' +
                ", officeCodeName='" + officeCodeName + '\'' +
                '}';
    }
}
