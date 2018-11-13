package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.domain.User;
import io.swagger.annotations.ApiModel;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TourDTO {

    @JsonFormat(pattern = Constants.Dates.DATE_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date createdOn;

    private Long requestedByUserId;

    private String location;

    private String customer;

    private String modeOfTransport;

    @JsonFormat(pattern = Constants.Dates.DATE_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date fromDate;

    @JsonFormat(pattern = Constants.Dates.DATE_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date toDate;

    private Integer duration;

    private String status;

    private Long statusByUserId;

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Long getRequestedByUserId() {
        return requestedByUserId;
    }

    public void setRequestedByUserId(Long requestedByUserId) {
        this.requestedByUserId = requestedByUserId;
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

    public Long getStatusByUserId() {
        return statusByUserId;
    }

    public void setStatusByUserId(Long statusByUserId) {
        this.statusByUserId = statusByUserId;
    }

    @Override
    public String toString() {
        return "TourDTO{" +
                "createdOn=" + createdOn +
                ", requestedByUserId=" + requestedByUserId +
                ", location='" + location + '\'' +
                ", customer='" + customer + '\'' +
                ", modeOfTransport='" + modeOfTransport + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", duration=" + duration +
                ", status='" + status + '\'' +
                ", statusByUserId=" + statusByUserId +
                '}';
    }
}
