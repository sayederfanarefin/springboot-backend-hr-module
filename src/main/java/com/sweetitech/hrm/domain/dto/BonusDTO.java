package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BonusDTO {

    private Long bonusId;

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

    private Long typeOfBonusId;

    public Long getBonusId() {
        return bonusId;
    }

    public void setBonusId(Long bonusId) {
        this.bonusId = bonusId;
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

    public Long getTypeOfBonusId() {
        return typeOfBonusId;
    }

    public void setTypeOfBonusId(Long typeOfBonusId) {
        this.typeOfBonusId = typeOfBonusId;
    }

    @Override
    public String toString() {
        return "BonusDTO{" +
                "bonusId=" + bonusId +
                ", amount=" + amount +
                ", dateOfOrder=" + dateOfOrder +
                ", month=" + month +
                ", year=" + year +
                ", remarks='" + remarks + '\'' +
                ", paidToUserId=" + paidToUserId +
                ", approvedByUserId=" + approvedByUserId +
                ", dateOfPayment=" + dateOfPayment +
                ", typeOfBonusId=" + typeOfBonusId +
                '}';
    }
}
