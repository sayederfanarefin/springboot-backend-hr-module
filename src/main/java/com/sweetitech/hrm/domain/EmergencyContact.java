package com.sweetitech.hrm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "emergency_contact")
public class EmergencyContact extends BaseEntity {

    @NotNull
    @Size(min = 2, max = 100, message = "Contact name must be between 2 to 100 characters!")
    private String name;

    @NotNull
    @Size(min = 3)
    private String relation;

    private String mobile;

    @Column(columnDefinition = "TEXT")
    private String address;

    public EmergencyContact() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "EmergencyContact{" +
                "name='" + name + '\'' +
                ", relation='" + relation + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
