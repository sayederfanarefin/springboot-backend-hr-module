package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude
public class TourResponseDTO {

    private Long tourId;

    private Date createdOn;

    private UserSmallResponseDTO requestedByUser;

    private String location;

    private String customer;

    private String modeOfTransport;

    private Date fromDate;

    private Date toDate;

    private Integer duration;

    private String status;

    private UserSmallResponseDTO statusByUser;

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public UserSmallResponseDTO getRequestedByUser() {
        return requestedByUser;
    }

    public void setRequestedByUser(UserSmallResponseDTO requestedByUser) {
        this.requestedByUser = requestedByUser;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getModeOfTransport() {
        return modeOfTransport;
    }

    public void setModeOfTransport(String modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserSmallResponseDTO getStatusByUser() {
        return statusByUser;
    }

    public void setStatusByUser(UserSmallResponseDTO statusByUser) {
        this.statusByUser = statusByUser;
    }

    @Override
    public String toString() {
        return "TourResponseDTO{" +
                "tourId=" + tourId +
                ", createdOn=" + createdOn +
                ", requestedByUser=" + requestedByUser +
                ", location='" + location + '\'' +
                ", customer='" + customer + '\'' +
                ", modeOfTransport='" + modeOfTransport + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", duration=" + duration +
                ", status='" + status + '\'' +
                ", statusByUser=" + statusByUser +
                '}';
    }
}
