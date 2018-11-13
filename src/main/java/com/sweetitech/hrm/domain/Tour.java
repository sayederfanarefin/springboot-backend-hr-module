package com.sweetitech.hrm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sweetitech.hrm.common.Constants;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "tours")
public class Tour extends BaseEntity {

    @JsonFormat(pattern = Constants.Dates.DATE_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date createdOn = new Date();

    @OneToOne
    @NotNull
    private User requestedBy;

    @NotNull
    @Size(min = 2)
    private String location;

    private String customer;

    private String modeOfTransport;

    @JsonFormat(pattern = Constants.Dates.DATE_FORMAT, timezone = Constants.Dates.TIMEZONE)
    @NotNull
    private Date fromDate;

    @JsonFormat(pattern = Constants.Dates.DATE_FORMAT, timezone = Constants.Dates.TIMEZONE)
    @NotNull
    private Date toDate;

    private Integer duration;

    @NotNull
    private String status;

    @OneToOne
    private User statusBy;

    public Tour() {
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
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

    public User getStatusBy() {
        return statusBy;
    }

    public void setStatusBy(User statusBy) {
        this.statusBy = statusBy;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "createdOn=" + createdOn +
                ", requestedBy=" + requestedBy +
                ", location='" + location + '\'' +
                ", customer='" + customer + '\'' +
                ", modeOfTransport='" + modeOfTransport + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", duration=" + duration +
                ", status='" + status + '\'' +
                ", statusBy=" + statusBy +
                '}';
    }
}
