package com.sweetitech.hrm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contact_info")
public class ContactInfo extends BaseEntity {

    @NotNull
    @Size(min = 6)
    private String mobile;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String presentAddress;

    @Column(columnDefinition = "TEXT")
    private String permanentAddress;

    public ContactInfo() {
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

    @Override
    public String toString() {
        return "ContactInfo{" +
                "mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", presentAddress='" + presentAddress + '\'' +
                ", permanentAddress='" + permanentAddress + '\'' +
                '}';
    }
}
