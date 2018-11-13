package com.sweetitech.hrm.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "notification_count")
public class NotificationCount {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @OneToOne
    private User user;

    private Integer count = 0;

    public NotificationCount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "NotificationCount{" +
                "id=" + id +
                ", user=" + user +
                ", count=" + count +
                '}';
    }
}
