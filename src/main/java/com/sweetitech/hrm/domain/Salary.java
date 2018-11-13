package com.sweetitech.hrm.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "salaries")
public class Salary extends BaseEntity {

    @NotNull
    private Double amount;

    @NotNull
    private Double bonus = 50.0;

    public Salary() {
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "amount=" + amount +
                ", bonus=" + bonus +
                '}';
    }
}
