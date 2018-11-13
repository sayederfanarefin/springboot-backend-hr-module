package com.sweetitech.hrm.domain.relation;

import com.sweetitech.hrm.domain.BaseEntity;
import com.sweetitech.hrm.domain.Salary;
import com.sweetitech.hrm.domain.SalaryBreakdown;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "salary_breakdown_relations")
public class SalaryBreakdownRelation extends BaseEntity {

    @NotNull
    @OneToOne
    private Salary salary;

    @OneToOne
    private SalaryBreakdown breakdown;

    private boolean isDeleted = false;

    public SalaryBreakdownRelation() {
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public SalaryBreakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(SalaryBreakdown breakdown) {
        this.breakdown = breakdown;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "SalaryBreakdownRelation{" +
                "salary=" + salary +
                ", breakdown=" + breakdown +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
