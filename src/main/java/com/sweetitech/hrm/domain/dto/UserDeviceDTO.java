package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDeviceDTO {

    private Long userId;

    private String userInfoEnrollNumber;

    private Long deviceLocationId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserInfoEnrollNumber() {
        return userInfoEnrollNumber;
    }

    public void setUserInfoEnrollNumber(String userInfoEnrollNumber) {
        this.userInfoEnrollNumber = userInfoEnrollNumber;
    }

    public Long getDeviceLocationId() {
        return deviceLocationId;
    }

    public void setDeviceLocationId(Long deviceLocationId) {
        this.deviceLocationId = deviceLocationId;
    }

    @Override
    public String toString() {
        return "UserDeviceDTO{" +
                "userId=" + userId +
                ", userInfoEnrollNumber=" + userInfoEnrollNumber +
                ", deviceLocationId=" + deviceLocationId +
                '}';
    }
}
