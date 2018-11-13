package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDTO {

    private Long driverId;

    private Long userId;

    private String licenseNo;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date licenseExpiryDate;

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "DriverDTO{" +
                "driverId=" + driverId +
                ", userId=" + userId +
                ", licenseNo='" + licenseNo + '\'' +
                ", licenseExpiryDate=" + licenseExpiryDate +
                '}';
    }
}
