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
@Table(name = "car_requests")
public class CarRequest extends BaseEntity {

    @JsonFormat(pattern = Constants.Dates.DATE_FORMAT, timezone = Constants.Dates.TIMEZONE)
    @NotNull
    private Date requestedOn;

    @JsonFormat(pattern = Constants.Dates.DATE_FORMAT, timezone = Constants.Dates.TIMEZONE)
    @NotNull
    private Date requestedFrom;

    @NotNull
    private Integer fromHour;

    @JsonFormat(pattern = Constants.Dates.DATE_FORMAT, timezone = Constants.Dates.TIMEZONE)
    @NotNull
    private Date requestedTo;

    @NotNull
    private Integer toHour;

    @NotNull
    @OneToOne
    private User requestedBy;

    @NotNull
    private String purpose;

    @NotNull
    private String status;

    @NotNull
    @Size(min = 2)
    private String destination;

    private Boolean roundTrip = false;

    public CarRequest() {
    }

    public Date getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(Date requestedOn) {
        this.requestedOn = requestedOn;
    }

    public Date getRequestedFrom() {
        return requestedFrom;
    }

    public void setRequestedFrom(Date requestedFrom) {
        this.requestedFrom = requestedFrom;
    }

    public Date getRequestedTo() {
        return requestedTo;
    }

    public void setRequestedTo(Date requestedTo) {
        this.requestedTo = requestedTo;
    }

    public Integer getFromHour() {
        return fromHour;
    }

    public void setFromHour(Integer fromHour) {
        this.fromHour = fromHour;
    }

    public Integer getToHour() {
        return toHour;
    }

    public void setToHour(Integer toHour) {
        this.toHour = toHour;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Boolean isRoundTrip() {
        return roundTrip;
    }

    public void setRoundTrip(Boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    @Override
    public String toString() {
        return "CarRequest{" +
                "requestedOn=" + requestedOn +
                ", requestedFrom=" + requestedFrom +
                ", fromHour=" + fromHour +
                ", requestedTo=" + requestedTo +
                ", toHour=" + toHour +
                ", requestedBy=" + requestedBy +
                ", purpose='" + purpose + '\'' +
                ", status='" + status + '\'' +
                ", destination='" + destination + '\'' +
                ", roundTrip=" + roundTrip +
                '}';
    }
}
