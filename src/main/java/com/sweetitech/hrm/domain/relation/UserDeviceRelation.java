package com.sweetitech.hrm.domain.relation;

import com.sweetitech.hrm.domain.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_device_relations")
public class UserDeviceRelation extends BaseEntity {

    @NotNull
    @OneToOne
    private User user;

    @NotNull
    @OneToOne
    private DeviceLocation location;

    @NotNull
    private String enrollmentNumber;

    public UserDeviceRelation() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DeviceLocation getLocation() {
        return location;
    }

    public void setLocation(DeviceLocation location) {
        this.location = location;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    @Override
    public String toString() {
        return "UserDeviceRelation{" +
                "user=" + user +
                ", location=" + location +
                ", enrollmentNumber='" + enrollmentNumber + '\'' +
                '}';
    }
}
