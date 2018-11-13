package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.DeviceLocation;
import com.sweetitech.hrm.domain.UserInfo;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDeviceResponseDTO {

    private Long relationId;

    private UserSmallResponseDTO userSmallResponseDTO;

    private String enrollmentNumber;

    private DeviceLocation location;

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public UserSmallResponseDTO getUserSmallResponseDTO() {
        return userSmallResponseDTO;
    }

    public void setUserSmallResponseDTO(UserSmallResponseDTO userSmallResponseDTO) {
        this.userSmallResponseDTO = userSmallResponseDTO;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public DeviceLocation getLocation() {
        return location;
    }

    public void setLocation(DeviceLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "UserDeviceResponseDTO{" +
                "relationId=" + relationId +
                ", userSmallResponseDTO=" + userSmallResponseDTO +
                ", enrollmentNumber='" + enrollmentNumber + '\'' +
                ", location=" + location +
                '}';
    }
}
