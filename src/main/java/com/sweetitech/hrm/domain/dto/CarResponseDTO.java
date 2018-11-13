package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarResponseDTO {

    private Long carId;

    private String regNo;

    private String taxTokenNo;

    private String insuranceNo;

    private String fitnessTokenNo;

    private Date lastRenewalDate;

    private String model;

    private CompanySmallResponseDTO company;

    private Integer capacity;

    private boolean maintenance;

    private boolean isDeleted;

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
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

    public CompanySmallResponseDTO getCompany() {
        return company;
    }

    public void setCompany(CompanySmallResponseDTO company) {
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
        return "CarResponseDTO{" +
                "carId=" + carId +
                ", regNo='" + regNo + '\'' +
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
