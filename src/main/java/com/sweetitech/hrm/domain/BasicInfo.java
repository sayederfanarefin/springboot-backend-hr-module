package com.sweetitech.hrm.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "basic_info")
public class BasicInfo extends BaseEntity {

    @NotNull
    @Column(columnDefinition = "TEXT")
    @Size(min = 2, max = 100, message = "First name character must be between 2 to 100!")
    private String firstName;

    private String middleName;

    @NotNull
    @Column(columnDefinition = "TEXT")
    @Size(min = 2, max = 100, message = "First name character must be between 2 to 100!")
    private String lastName;

    private Date dob;

    private String nationality;

    private String nationalId;

    private String passportNumber;

    private String maritalStatus;

    private String gender;

    private String bloodGroup;

    @NotNull
    private Date joiningDate;

    private String religion;

    @ManyToOne
    private User createdBy;

    @OneToOne
    private Image profilePicture;

//    @Column(length = 10000000)
//    @Basic(fetch = FetchType.LAZY, optional = true)
//    private byte[] profilePicture;

    public BasicInfo() {
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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
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

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    //    public byte[] getProfilePicture() {
//        return profilePicture;
//    }
//
//    public void setProfilePicture(byte[] profilePicture) {
//        this.profilePicture = profilePicture;
//    }

    @Override
    public String toString() {
        return "BasicInfo{" +
                "firstName='" + firstName + '\'' +
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
                ", createdBy=" + createdBy +
                ", profilePicture=" + profilePicture +
                '}';
    }
}
