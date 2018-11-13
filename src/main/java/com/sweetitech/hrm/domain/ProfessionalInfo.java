package com.sweetitech.hrm.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "professional_info")
public class ProfessionalInfo extends BaseEntity {

    @NotNull
    @Size(min = 2, max = 100)
    private String designation;

    @ManyToOne
    private Department department;

    public ProfessionalInfo() {
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "ProfessionalInfo{" +
                "designation='" + designation + '\'' +
                ", department=" + department +
                '}';
    }
}
