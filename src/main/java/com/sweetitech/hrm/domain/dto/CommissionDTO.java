package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommissionDTO {

    private Long commissionId;

    private Double localCommission;

    private Double foreignCommission;

    private Double amount;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date dateOfOrder = new Date();

    private Integer month;

    private Integer year;

    private String remarks;

    private Long paidToUserId;

    private Long approvedByUserId;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date dateOfPayment;

    private Long typeOfCommissionId;

    public Long getCommissionId() {
        return commissionId;
    }

    public void setCommissionId(Long commissionId) {
        this.commissionId = commissionId;
    }

    public Double getLocalCommission() {
        return localCommission;
    }

    public void setLocalCommission(Double localCommission) {
        this.localCommission = localCommission;
    }

    public Double getForeignCommission() {
        return foreignCommission;
    }

    public void setForeignCommission(Double foreignCommission) {
        this.foreignCommission = foreignCommission;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getPaidToUserId() {
        return paidToUserId;
    }

    public void setPaidToUserId(Long paidToUserId) {
        this.paidToUserId = paidToUserId;
    }

    public Long getApprovedByUserId() {
        return approvedByUserId;
    }

    public void setApprovedByUserId(Long approvedByUserId) {
        this.approvedByUserId = approvedByUserId;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public Long getTypeOfCommissionId() {
        return typeOfCommissionId;
    }

    public void setTypeOfCommissionId(Long typeOfCommissionId) {
        this.typeOfCommissionId = typeOfCommissionId;
    }

    @Override
    public String toString() {
        return "CommissionDTO{" +
                "commissionId=" + commissionId +
                ", localCommission=" + localCommission +
                ", foreignCommission=" + foreignCommission +
                ", amount=" + amount +
                ", dateOfOrder=" + dateOfOrder +
                ", month=" + month +
                ", year=" + year +
                ", remarks='" + remarks + '\'' +
                ", paidToUserId=" + paidToUserId +
                ", approvedByUserId=" + approvedByUserId +
                ", dateOfPayment=" + dateOfPayment +
                ", typeOfCommissionId=" + typeOfCommissionId +
                '}';
    }
}
