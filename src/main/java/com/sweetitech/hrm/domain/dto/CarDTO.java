package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {

    private Long carId;

    private String regNo;

    private String taxTokenNo;

    private String insuranceNo;

    private String fitnessTokenNo;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date lastRenewalDate;

    private String model;

    private Integer capacity;

    private Long companyId;

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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "carId=" + carId +
                ", regNo='" + regNo + '\'' +
                ", taxTokenNo='" + taxTokenNo + '\'' +
                ", insuranceNo='" + insuranceNo + '\'' +
                ", fitnessTokenNo='" + fitnessTokenNo + '\'' +
                ", lastRenewalDate=" + lastRenewalDate +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                ", companyId=" + companyId +
                '}';
    }
}
