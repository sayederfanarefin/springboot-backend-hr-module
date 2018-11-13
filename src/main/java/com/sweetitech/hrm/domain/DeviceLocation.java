package com.sweetitech.hrm.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "device_locations")
public class DeviceLocation extends BaseEntity {

    @NotNull
    @OneToOne
    private Company company;

    @Size(min = 2)
    private String location;

    @NotNull
    @OneToOne
    private Device device;

    private boolean isDeleted = false;

    public DeviceLocation() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "DeviceLocation{" +
                "company=" + company +
                ", location='" + location + '\'' +
                ", device=" + device +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
