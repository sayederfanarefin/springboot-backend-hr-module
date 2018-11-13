package com.sweetitech.hrm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sweetitech.hrm.common.Constants;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "commissions")
public class Commission extends BaseEntity {

    private Double localCommission;

    private Double foreignCommission;

    private Double amount;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date dateOfOrder;

    @NotNull
    private Integer month;

    @NotNull
    private Integer year;

    private String remarks;

    @NotNull
    @OneToOne
    private User paidTo;

    @NotNull
    @OneToOne
    private User approvedBy;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date dateOfPayment;

    @OneToOne
    private TypeOfCommission typeOfCommission;

    public Commission() {
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

    public User getPaidTo() {
        return paidTo;
    }

    public void setPaidTo(User paidTo) {
        this.paidTo = paidTo;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public TypeOfCommission getTypeOfCommission() {
        return typeOfCommission;
    }

    public void setTypeOfCommission(TypeOfCommission typeOfCommission) {
        this.typeOfCommission = typeOfCommission;
    }

    @Override
    public String toString() {
        return "Commission{" +
                "localCommission=" + localCommission +
                ", foreignCommission=" + foreignCommission +
                ", amount=" + amount +
                ", dateOfOrder=" + dateOfOrder +
                ", month=" + month +
                ", year=" + year +
                ", remarks='" + remarks + '\'' +
                ", paidTo=" + paidTo +
                ", approvedBy=" + approvedBy +
                ", dateOfPayment=" + dateOfPayment +
                ", typeOfCommission=" + typeOfCommission +
                '}';
    }
}
