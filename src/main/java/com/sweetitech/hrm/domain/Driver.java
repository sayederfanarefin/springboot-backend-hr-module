package com.sweetitech.hrm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sweetitech.hrm.common.Constants;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "drivers")
public class Driver extends BaseEntity {

    @NotNull
    @OneToOne
    private User user;

    private String licenseNo;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date licenseExpiryDate;

    private boolean isDeleted = false;

    public Driver() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public Date getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(Date licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "user=" + user +
                ", licenseNo='" + licenseNo + '\'' +
                ", licenseExpiryDate=" + licenseExpiryDate +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
