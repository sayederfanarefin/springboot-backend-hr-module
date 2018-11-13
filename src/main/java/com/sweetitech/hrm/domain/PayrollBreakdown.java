package com.sweetitech.hrm.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "payroll_breakdowns")
public class PayrollBreakdown extends BaseEntity {

    @NotNull
    @OneToOne
    private Payroll payroll;

    @NotNull
    @OneToOne
    private SalaryBreakdown breakdown;

    private Double amount = 0.0;

    public PayrollBreakdown() {
    }

    public Payroll getPayroll() {
        return payroll;
    }

    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }

    public SalaryBreakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(SalaryBreakdown breakdown) {
        this.breakdown = breakdown;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PayrollBreakdown{" +
                "payroll=" + payroll +
                ", breakdown=" + breakdown +
                ", amount=" + amount +
                '}';
    }
}
