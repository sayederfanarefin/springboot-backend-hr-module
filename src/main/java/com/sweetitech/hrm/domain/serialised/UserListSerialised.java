package com.sweetitech.hrm.domain.serialised;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.User;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserListSerialised {

    private Integer serial;

    private User user;

    public UserListSerialised(Integer serial, User user) {
        this.serial = serial;
        this.user = user;
    }

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserListSerialised{" +
                "serial=" + serial +
                ", user=" + user +
                '}';
    }
}
