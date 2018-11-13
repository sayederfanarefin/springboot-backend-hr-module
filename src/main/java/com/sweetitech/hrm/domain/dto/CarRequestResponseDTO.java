package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude
public class CarRequestResponseDTO {

    private Long carRequestId;

    @JsonFormat(pattern = Constants.Dates.DATE_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date requestedOn;

    @JsonFormat(pattern = Constants.Dates.DATE_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date requestedFrom;

    private Integer fromHour;

    @JsonFormat(pattern = Constants.Dates.DATE_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date requestedTo;

    private Integer toHour;

    private UserSmallResponseDTO requestedByUser;

    private String purpose;

    private String status;

    private String destination;

    private Boolean roundTrip;

    public Long getCarRequestId() {
        return carRequestId;
    }

    public void setCarRequestId(Long carRequestId) {
        this.carRequestId = carRequestId;
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

    public UserSmallResponseDTO getRequestedByUser() {
        return requestedByUser;
    }

    public void setRequestedByUser(UserSmallResponseDTO requestedByUser) {
        this.requestedByUser = requestedByUser;
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

    public void setRoundTrip(boolean roundTrip) {
        this.roundTrip = roundTrip;
    }

    @Override
    public String toString() {
        return "CarRequestResponseDTO{" +
                "carRequestId=" + carRequestId +
                ", requestedOn=" + requestedOn +
                ", requestedFrom=" + requestedFrom +
                ", fromHour=" + fromHour +
                ", requestedTo=" + requestedTo +
                ", toHour=" + toHour +
                ", requestedByUser=" + requestedByUser +
                ", purpose='" + purpose + '\'' +
                ", status='" + status + '\'' +
                ", destination='" + destination + '\'' +
                ", roundTrip=" + roundTrip +
                '}';
    }
}
