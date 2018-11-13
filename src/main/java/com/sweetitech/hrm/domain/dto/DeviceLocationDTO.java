package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceLocationDTO {

    private Long locationId;

    private Long companyId;

    private String location;

    private Long deviceId;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "DeviceLocationDTO{" +
                "locationId=" + locationId +
                ", companyId=" + companyId +
                ", location='" + location + '\'' +
                ", deviceId=" + deviceId +
                '}';
    }
}
