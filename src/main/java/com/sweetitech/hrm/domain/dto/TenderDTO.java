package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenderDTO {

    private Long tenderId;

    private String institutionName;

    private String mrCode;

    private String typeOfTender;

    private String eTenderId;

    private String tenderLotNumber;

    private Double costOfTenderSchedule = (double) 0;

    private Double totalTenderValue;

    private String typeOfTenderSecurity;

    private Double amountOfTenderSecurity;

    private String tenderSecurityStatus = Constants.AmountOfTenderSecurityStatus.PENDING;

    private boolean copyOfTenderSecurityInFile = false;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date issueDateOfTenderSecurity;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date tenderSecurityReleaseDate;

    private String typeOfPerformanceSecurity;

    private Double amountOfPerformanceSecurity;

    private boolean copyOfPerformanceSecurityInFile;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date issueDateOfPerformanceSecurity;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date performanceSecurityReleaseDate;

    private String tenderStatus;

    private String status = Constants.TenderStatus.WHITE;

    private Long createdByUserId;

    public Long getTenderId() {
        return tenderId;
    }

    public void setTenderId(Long tenderId) {
        this.tenderId = tenderId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getMrCode() {
        return mrCode;
    }

    public void setMrCode(String mrCode) {
        this.mrCode = mrCode;
    }

    public String getTypeOfTender() {
        return typeOfTender;
    }

    public void setTypeOfTender(String typeOfTender) {
        this.typeOfTender = typeOfTender;
    }

    public String geteTenderId() {
        return eTenderId;
    }

    public void seteTenderId(String eTenderId) {
        this.eTenderId = eTenderId;
    }

    public String getTenderLotNumber() {
        return tenderLotNumber;
    }

    public void setTenderLotNumber(String tenderLotNumber) {
        this.tenderLotNumber = tenderLotNumber;
    }

    public Double getCostOfTenderSchedule() {
        return costOfTenderSchedule;
    }

    public void setCostOfTenderSchedule(Double costOfTenderSchedule) {
        this.costOfTenderSchedule = costOfTenderSchedule;
    }

    public Double getTotalTenderValue() {
        return totalTenderValue;
    }

    public void setTotalTenderValue(Double totalTenderValue) {
        this.totalTenderValue = totalTenderValue;
    }

    public String getTypeOfTenderSecurity() {
        return typeOfTenderSecurity;
    }

    public void setTypeOfTenderSecurity(String typeOfTenderSecurity) {
        this.typeOfTenderSecurity = typeOfTenderSecurity;
    }

    public Double getAmountOfTenderSecurity() {
        return amountOfTenderSecurity;
    }

    public void setAmountOfTenderSecurity(Double amountOfTenderSecurity) {
        this.amountOfTenderSecurity = amountOfTenderSecurity;
    }

    public String getTenderSecurityStatus() {
        return tenderSecurityStatus;
    }

    public void setTenderSecurityStatus(String tenderSecurityStatus) {
        this.tenderSecurityStatus = tenderSecurityStatus;
    }

    public boolean isCopyOfTenderSecurityInFile() {
        return copyOfTenderSecurityInFile;
    }

    public void setCopyOfTenderSecurityInFile(boolean copyOfTenderSecurityInFile) {
        this.copyOfTenderSecurityInFile = copyOfTenderSecurityInFile;
    }

    public Date getIssueDateOfTenderSecurity() {
        return issueDateOfTenderSecurity;
    }

    public void setIssueDateOfTenderSecurity(Date issueDateOfTenderSecurity) {
        this.issueDateOfTenderSecurity = issueDateOfTenderSecurity;
    }

    public Date getTenderSecurityReleaseDate() {
        return tenderSecurityReleaseDate;
    }

    public void setTenderSecurityReleaseDate(Date tenderSecurityReleaseDate) {
        this.tenderSecurityReleaseDate = tenderSecurityReleaseDate;
    }

    public String getTypeOfPerformanceSecurity() {
        return typeOfPerformanceSecurity;
    }

    public void setTypeOfPerformanceSecurity(String typeOfPerformanceSecurity) {
        this.typeOfPerformanceSecurity = typeOfPerformanceSecurity;
    }

    public Double getAmountOfPerformanceSecurity() {
        return amountOfPerformanceSecurity;
    }

    public void setAmountOfPerformanceSecurity(Double amountOfPerformanceSecurity) {
        this.amountOfPerformanceSecurity = amountOfPerformanceSecurity;
    }

    public boolean isCopyOfPerformanceSecurityInFile() {
        return copyOfPerformanceSecurityInFile;
    }

    public void setCopyOfPerformanceSecurityInFile(boolean copyOfPerformanceSecurityInFile) {
        this.copyOfPerformanceSecurityInFile = copyOfPerformanceSecurityInFile;
    }

    public Date getIssueDateOfPerformanceSecurity() {
        return issueDateOfPerformanceSecurity;
    }

    public void setIssueDateOfPerformanceSecurity(Date issueDateOfPerformanceSecurity) {
        this.issueDateOfPerformanceSecurity = issueDateOfPerformanceSecurity;
    }

    public Date getPerformanceSecurityReleaseDate() {
        return performanceSecurityReleaseDate;
    }

    public void setPerformanceSecurityReleaseDate(Date performanceSecurityReleaseDate) {
        this.performanceSecurityReleaseDate = performanceSecurityReleaseDate;
    }

    public String getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(String tenderStatus) {
        this.tenderStatus = tenderStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    @Override
    public String toString() {
        return "TenderDTO{" +
                "tenderId=" + tenderId +
                ", institutionName='" + institutionName + '\'' +
                ", mrCode='" + mrCode + '\'' +
                ", typeOfTender='" + typeOfTender + '\'' +
                ", eTenderId='" + eTenderId + '\'' +
                ", tenderLotNumber='" + tenderLotNumber + '\'' +
                ", costOfTenderSchedule=" + costOfTenderSchedule +
                ", totalTenderValue=" + totalTenderValue +
                ", typeOfTenderSecurity='" + typeOfTenderSecurity + '\'' +
                ", amountOfTenderSecurity=" + amountOfTenderSecurity +
                ", tenderSecurityStatus='" + tenderSecurityStatus + '\'' +
                ", copyOfTenderSecurityInFile=" + copyOfTenderSecurityInFile +
                ", issueDateOfTenderSecurity=" + issueDateOfTenderSecurity +
                ", tenderSecurityReleaseDate=" + tenderSecurityReleaseDate +
                ", typeOfPerformanceSecurity='" + typeOfPerformanceSecurity + '\'' +
                ", amountOfPerformanceSecurity=" + amountOfPerformanceSecurity +
                ", copyOfPerformanceSecurityInFile=" + copyOfPerformanceSecurityInFile +
                ", issueDateOfPerformanceSecurity=" + issueDateOfPerformanceSecurity +
                ", performanceSecurityReleaseDate=" + performanceSecurityReleaseDate +
                ", tenderStatus='" + tenderStatus + '\'' +
                ", status='" + status + '\'' +
                ", createdByUserId=" + createdByUserId +
                '}';
    }
}
