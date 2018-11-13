package com.sweetitech.hrm.domain.relation;

import com.sweetitech.hrm.domain.BaseEntity;
import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.User;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_task_relations")
public class UserTaskRelation extends BaseEntity {

    @NotNull
    @OneToOne
    private Task task;

    @NotNull
    @OneToOne
    private User user;

    public UserTaskRelation() {
    }

    public UserTaskRelation(@NotNull Task task, @NotNull User user) {
        this.task = task;
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserTaskRelation{" +
                "task=" + task +
                ", user=" + user +
                '}';
    }
}
