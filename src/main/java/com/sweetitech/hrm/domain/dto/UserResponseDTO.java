package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.OfficeCode;
import com.sweetitech.hrm.domain.ProfessionalInfo;
import com.sweetitech.hrm.domain.Role;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Role role;

    @NotNull
    private Company company;

    @Size(min = 2, max = 100, message = "First name must be between 2 to 100 characters!")
    private String firstName;

    private String middleName;

    @Size(min = 2, max = 100, message = "Last name must be between 2 to 100 characters!")
    private String lastName;

    @NotNull
    private ProfessionalInfo professionalInfo;

    private OfficeCode officeCode;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ProfessionalInfo getProfessionalInfo() {
        return professionalInfo;
    }

    public void setProfessionalInfo(ProfessionalInfo professionalInfo) {
        this.professionalInfo = professionalInfo;
    }

    public OfficeCode getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(OfficeCode officeCode) {
        this.officeCode = officeCode;
    }

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "userId=" + userId +
                ", role=" + role +
                ", company=" + company +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", professionalInfo=" + professionalInfo +
                ", officeCode=" + officeCode +
                '}';
    }

}
