package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEditDTO {

    @Size(min = 2, max = 100, message = "Username character must be between 2 to 100!")
    private String username;

    private String mrCode;

    @NotNull
    private Long roleId;

    @NotNull
    private Long companyId;

    @Size(min = 2, max = 100, message = "First name must be between 2 to 100 characters!")
    private String firstName;

    private String middleName;

    @Size(min = 2, max = 100, message = "Last name must be between 2 to 100 characters!")
    private String lastName;

    private Date dob;

    private String nationality;

    private String nationalId;

    private String passportNumber;

    private String maritalStatus;

    private String gender;

    private String bloodGroup;

    private Date joiningDate;

    private String religion;

    private Long createdByUserId;

    @Size(min = 6, message = "Mobile number must be at least 6 characters!")
    private String mobile;

    private String email;

    private String presentAddress;

    private String permanentAddress;

    @Size(min = 2, max = 100, message = "Emergency contact's name must be between 2 to 100 characters!")
    private String emergencyContactName;

    @Size(min = 4)
    private String relation;

    private String emergencyContactMobile;

    private String emergencyContactAddress;

    @Size(min = 2)
    private String designation;

    private Long departmentId;

    private Long profilePictureId;

    private Long gradeId;

    private Long officeCodeId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMrCode() {
        return mrCode;
    }

    public void setMrCode(String mrCode) {
        this.mrCode = mrCode;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getEmergencyContactMobile() {
        return emergencyContactMobile;
    }

    public void setEmergencyContactMobile(String emergencyContactMobile) {
        this.emergencyContactMobile = emergencyContactMobile;
    }

    public String getEmergencyContactAddress() {
        return emergencyContactAddress;
    }

    public void setEmergencyContactAddress(String emergencyContactAddress) {
        this.emergencyContactAddress = emergencyContactAddress;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getProfilePictureId() {
        return profilePictureId;
    }

    public void setProfilePictureId(Long profilePictureId) {
        this.profilePictureId = profilePictureId;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Long getOfficeCodeId() {
        return officeCodeId;
    }

    public void setOfficeCodeId(Long officeCodeId) {
        this.officeCodeId = officeCodeId;
    }

    @Override
    public String toString() {
        return "UserEditDTO{" +
                "username='" + username + '\'' +
                ", mrCode='" + mrCode + '\'' +
                ", roleId=" + roleId +
                ", companyId=" + companyId +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", nationality='" + nationality + '\'' +
                ", nationalId='" + nationalId + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", gender='" + gender + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", joiningDate=" + joiningDate +
                ", religion='" + religion + '\'' +
                ", createdByUserId=" + createdByUserId +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", presentAddress='" + presentAddress + '\'' +
                ", permanentAddress='" + permanentAddress + '\'' +
                ", emergencyContactName='" + emergencyContactName + '\'' +
                ", relation='" + relation + '\'' +
                ", emergencyContactMobile='" + emergencyContactMobile + '\'' +
                ", emergencyContactAddress='" + emergencyContactAddress + '\'' +
                ", designation='" + designation + '\'' +
                ", departmentId=" + departmentId +
                ", profilePictureId=" + profilePictureId +
                ", gradeId=" + gradeId +
                ", officeCodeId=" + officeCodeId +
                '}';
    }
}
