package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowanceListObjectDTO {

    private Integer dayOfMonth;

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

    private AllowanceUserDTO requestingUser;

    private AllowanceUserDTO decidingUser;

    private Date statusDate;

    private String status;

    private Long summaryId;

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

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

    public AllowanceUserDTO getRequestingUser() {
        return requestingUser;
    }

    public void setRequestingUser(AllowanceUserDTO requestingUser) {
        this.requestingUser = requestingUser;
    }

    public AllowanceUserDTO getDecidingUser() {
        return decidingUser;
    }

    public void setDecidingUser(AllowanceUserDTO decidingUser) {
        this.decidingUser = decidingUser;
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

    public Long getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(Long summaryId) {
        this.summaryId = summaryId;
    }

    @Override
    public String toString() {
        return "AllowanceListObjectDTO{" +
                "dayOfMonth=" + dayOfMonth +
                ", allowanceId=" + allowanceId +
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
                ", requestingUser=" + requestingUser +
                ", decidingUser=" + decidingUser +
                ", statusDate=" + statusDate +
                ", status='" + status + '\'' +
                ", summaryId=" + summaryId +
                '}';
    }
}
