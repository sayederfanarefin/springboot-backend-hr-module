package com.sweetitech.hrm.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "allowances")
public class Allowance extends BaseEntity {

    @NotNull
    private Date createdOn = new Date();

    @NotNull
    @Size(min = 2)
    @Column(columnDefinition = "TEXT")
    private String destination;

    private Double hq;

    private Double exHq;

    private Double os;

    private Double hotelFare;

    private String transportWithTicket;

    private String internalMode;

    private Double internalFare;

    private String remarks;

    @ManyToOne
    private AllowanceSummary allowanceSummary;

    @NotNull
    @OneToOne
    private User requestedBy;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private AllowanceStatus allowanceStatus;

    public Allowance() {
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

    public AllowanceSummary getAllowanceSummary() {
        return allowanceSummary;
    }

    public void setAllowanceSummary(AllowanceSummary allowanceSummary) {
        this.allowanceSummary = allowanceSummary;
    }

    public User getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(User requestedBy) {
        this.requestedBy = requestedBy;
    }

    public AllowanceStatus getAllowanceStatus() {
        return allowanceStatus;
    }

    public void setAllowanceStatus(AllowanceStatus allowanceStatus) {
        this.allowanceStatus = allowanceStatus;
    }

    @Override
    public String toString() {
        return "Allowance{" +
                "createdOn=" + createdOn +
                ", destination='" + destination + '\'' +
                ", hq=" + hq +
                ", exHq=" + exHq +
                ", os=" + os +
                ", hotelFare=" + hotelFare +
                ", transportWithTicket=" + transportWithTicket +
                ", internalMode='" + internalMode + '\'' +
                ", internalFare=" + internalFare +
                ", remarks='" + remarks + '\'' +
                ", allowanceSummary=" + allowanceSummary +
                ", requestedBy=" + requestedBy +
                ", allowanceStatus=" + allowanceStatus +
                '}';
    }
}
