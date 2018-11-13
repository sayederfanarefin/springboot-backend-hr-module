package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.Company;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PasswordResetRequestResponseDTO {

    private Long requestId;

    private UserSmallResponseDTO userSmallResponseDTO;

    private Company company;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date requestedOn;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public UserSmallResponseDTO getUserSmallResponseDTO() {
        return userSmallResponseDTO;
    }

    public void setUserSmallResponseDTO(UserSmallResponseDTO userSmallResponseDTO) {
        this.userSmallResponseDTO = userSmallResponseDTO;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(Date requestedOn) {
        this.requestedOn = requestedOn;
    }

    @Override
    public String toString() {
        return "PasswordResetRequestResponseDTO{" +
                "requestId=" + requestId +
                ", userSmallResponseDTO=" + userSmallResponseDTO +
                ", company=" + company +
                ", requestedOn=" + requestedOn +
                '}';
    }
}
