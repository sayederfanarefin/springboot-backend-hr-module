package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskUserResponseDTO {

    private TaskResponseDTO task;

    private List<UserSmallResponseDTO> users;

    public TaskResponseDTO getTask() {
        return task;
    }

    public void setTask(TaskResponseDTO task) {
        this.task = task;
    }

    public List<UserSmallResponseDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserSmallResponseDTO> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "TaskUserResponseDTO{" +
                "task=" + task +
                ", users=" + users +
                '}';
    }
}
