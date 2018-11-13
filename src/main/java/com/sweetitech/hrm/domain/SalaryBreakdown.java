package com.sweetitech.hrm.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "salary_breakdown")
public class SalaryBreakdown extends BaseEntity {

    @NotNull
    private String title;

    @NotNull
    private Double percentage;

    public SalaryBreakdown() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "SalaryBreakdown{" +
                "title='" + title + '\'' +
                ", percentage=" + percentage +
                '}';
    }
}
