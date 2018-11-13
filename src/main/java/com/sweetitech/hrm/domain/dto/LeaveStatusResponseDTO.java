package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveStatusResponseDTO {

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date statusDate;

    private String statusReason;

    private UserResponseDTO statusByUser;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public UserResponseDTO getStatusByUser() {
        return statusByUser;
    }

    public void setStatusByUser(UserResponseDTO statusByUser) {
        this.statusByUser = statusByUser;
    }

    @Override
    public String toString() {
        return "LeaveStatusResponseDTO{" +
                "status='" + status + '\'' +
                ", statusDate=" + statusDate +
                ", statusReason='" + statusReason + '\'' +
                ", statusByUser=" + statusByUser +
                '}';
    }
}
