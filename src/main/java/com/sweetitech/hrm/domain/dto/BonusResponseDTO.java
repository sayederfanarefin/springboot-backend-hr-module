package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.domain.TypeOfBonus;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BonusResponseDTO {

    private Long bonusId;

    private Double amount;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date dateOfOrder;

    private Integer month;

    private Integer year;

    private String remarks;

    private UserSmallResponseDTO paidToUser;

    private UserSmallResponseDTO approvedByUser;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date dateOfPayment;

    private TypeOfBonus typeOfBonus;

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

    public UserSmallResponseDTO getPaidToUser() {
        return paidToUser;
    }

    public void setPaidToUser(UserSmallResponseDTO paidToUser) {
        this.paidToUser = paidToUser;
    }

    public UserSmallResponseDTO getApprovedByUser() {
        return approvedByUser;
    }

    public void setApprovedByUser(UserSmallResponseDTO approvedByUser) {
        this.approvedByUser = approvedByUser;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public TypeOfBonus getTypeOfBonus() {
        return typeOfBonus;
    }

    public void setTypeOfBonus(TypeOfBonus typeOfBonus) {
        this.typeOfBonus = typeOfBonus;
    }

    @Override
    public String toString() {
        return "BonusResponseDTO{" +
                "bonusId=" + bonusId +
                ", amount=" + amount +
                ", dateOfOrder=" + dateOfOrder +
                ", month=" + month +
                ", year=" + year +
                ", remarks='" + remarks + '\'' +
                ", paidToUser=" + paidToUser +
                ", approvedByUser=" + approvedByUser +
                ", dateOfPayment=" + dateOfPayment +
                ", typeOfBonus=" + typeOfBonus +
                '}';
    }
}
