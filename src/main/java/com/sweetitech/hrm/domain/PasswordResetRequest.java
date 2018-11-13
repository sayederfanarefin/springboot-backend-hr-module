package com.sweetitech.hrm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "password_reset_requests")
public class PasswordResetRequest extends BaseEntity {

    @NotNull
    @OneToOne
    private User user;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date requestedOn;

    public PasswordResetRequest() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(Date requestedOn) {
        this.requestedOn = requestedOn;
    }

    @Override
    public String toString() {
        return "PasswordResetRequest{" +
                "user=" + user +
                ", requestedOn=" + requestedOn +
                '}';
    }
}
