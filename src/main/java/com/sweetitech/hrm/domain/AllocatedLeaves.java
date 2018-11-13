package com.sweetitech.hrm.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "allocated_leaves")
public class AllocatedLeaves extends BaseEntity {

    @NotNull
    @Size(min = 2, max = 100, message = "Must be between 2 to 100 characters!")
    private String typeOfEmployee;

    private Integer earnLeave;

    private Integer casualLeave;

    private Integer sickLeave;

    private String remarks;

    public AllocatedLeaves() {
    }

    public String getTypeOfEmployee() {
        return typeOfEmployee;
    }

    public void setTypeOfEmployee(String typeOfEmployee) {
        this.typeOfEmployee = typeOfEmployee;
    }

    public Integer getEarnLeave() {
        return earnLeave;
    }

    public void setEarnLeave(Integer earnLeave) {
        this.earnLeave = earnLeave;
    }

    public Integer getCasualLeave() {
        return casualLeave;
    }

    public void setCasualLeave(Integer casualLeave) {
        this.casualLeave = casualLeave;
    }

    public Integer getSickLeave() {
        return sickLeave;
    }

    public void setSickLeave(Integer sickLeave) {
        this.sickLeave = sickLeave;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "AllocatedLeaves{" +
                "typeOfEmployee='" + typeOfEmployee + '\'' +
                ", earnLeave=" + earnLeave +
                ", casualLeave=" + casualLeave +
                ", sickLeave=" + sickLeave +
                ", remarks='" + remarks + '\'' +
                '}';
    }
}
