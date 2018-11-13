package com.sweetitech.hrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "departments")
public class Department extends BaseEntity {

    @NotNull
    @Size(min = 2, max = 100, message = "Department name should be between 2 to 100 characters!")
    private String name;

    @ManyToOne
    private Company company;

    @JsonIgnore
    private boolean isDeleted = false;

    public Department() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", company=" + company +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
