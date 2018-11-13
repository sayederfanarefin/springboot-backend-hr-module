package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowanceDTO {

    private Long allowanceId;

    private Date createdOn = new Date();

    @NotNull
    @Size(min = 2, max = 100)
    private String destination;

    private Double hq;

    private Double exHq;

    private Double os;

    private Double hotelFare;

    private String transportWithTicket;

    private String internalMode;

    private Double internalFare;

    private String remarks;

    private Long requestedBy;

    private Long statusBy;

    private Date statusDate;

    private String status;

    public Long getAllowanceId() {
        return allowanceId;
    }

    public void setAllowanceId(Long allowanceId) {
        this.allowanceId = allowanceId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Double getHq() {
        return hq;
    }

    public void setHq(Double hq) {
        this.hq = hq;
    }

    public Double getExHq() {
        return exHq;
    }

    public void setExHq(Double exHq) {
        this.exHq = exHq;
    }

    public Double getOs() {
        return os;
    }

    public void setOs(Double os) {
        this.os = os;
    }

    public Double getHotelFare() {
        return hotelFare;
    }

    public void setHotelFare(Double hotelFare) {
        this.hotelFare = hotelFare;
    }

    public String getTransportWithTicket() {
        return transportWithTicket;
    }

    public void setTransportWithTicket(String transportWithTicket) {
        this.transportWithTicket = transportWithTicket;
    }

    public String getInternalMode() {
        return internalMode;
    }

    public void setInternalMode(String internalMode) {
        this.internalMode = internalMode;
    }

    public Double getInternalFare() {
        return internalFare;
    }

    public void setInternalFare(Double internalFare) {
        this.internalFare = internalFare;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Long requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Long getStatusBy() {
        return statusBy;
    }

    public void setStatusBy(Long statusBy) {
        this.statusBy = statusBy;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AllowanceDTO{" +
                "allowanceId=" + allowanceId +
                ", createdOn=" + createdOn +
                ", destination='" + destination + '\'' +
                ", hq=" + hq +
                ", exHq=" + exHq +
                ", os=" + os +
                ", hotelFare=" + hotelFare +
                ", transportWithTicket='" + transportWithTicket + '\'' +
                ", internalMode='" + internalMode + '\'' +
                ", internalFare=" + internalFare +
                ", remarks='" + remarks + '\'' +
                ", requestedBy=" + requestedBy +
                ", statusBy=" + statusBy +
                ", statusDate=" + statusDate +
                ", status='" + status + '\'' +
                '}';
    }
}
