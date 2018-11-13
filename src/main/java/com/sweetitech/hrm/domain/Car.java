package com.sweetitech.hrm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sweetitech.hrm.common.Constants;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {

    @NotNull
    private String regNo;

    private String taxTokenNo;

    private String insuranceNo;

    private String fitnessTokenNo;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date lastRenewalDate;

    @NotNull
    private String model;

    @NotNull
    @OneToOne
    private Company company;

    private Integer capacity = 0;

    private boolean maintenance = false;

    private boolean isDeleted = false;

    public Car() {
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getTaxTokenNo() {
        return taxTokenNo;
    }

    public void setTaxTokenNo(String taxTokenNo) {
        this.taxTokenNo = taxTokenNo;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo;
    }

    public String getFitnessTokenNo() {
        return fitnessTokenNo;
    }

    public void setFitnessTokenNo(String fitnessTokenNo) {
        this.fitnessTokenNo = fitnessTokenNo;
    }

    public Date getLastRenewalDate() {
        return lastRenewalDate;
    }

    public void setLastRenewalDate(Date lastRenewalDate) {
        this.lastRenewalDate = lastRenewalDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Car{" +
                "regNo='" + regNo + '\'' +
                ", taxTokenNo='" + taxTokenNo + '\'' +
                ", insuranceNo='" + insuranceNo + '\'' +
                ", fitnessTokenNo='" + fitnessTokenNo + '\'' +
                ", lastRenewalDate=" + lastRenewalDate +
                ", model='" + model + '\'' +
                ", company=" + company +
                ", capacity=" + capacity +
                ", maintenance=" + maintenance +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
