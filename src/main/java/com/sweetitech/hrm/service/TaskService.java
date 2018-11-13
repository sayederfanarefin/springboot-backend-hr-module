package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;
import com.sweetitech.hrm.domain.dto.TaskUserResponseDTO;
import com.sweetitech.hrm.domain.relation.UserTaskRelation;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {

    Task create(Task task);

    Task update(Long id, Task task) throws Exception;

    Task update(Long id, Task task, List<Long> users) throws Exception;

    void cancel(Long id) throws Exception;

    Task create(Task task, List<Long> users) throws Exception;

    List<Task> readAllByUserId(Long userId, Integer month, Integer year) throws Exception;

    List<TaskResponseDTO> readAllAssignedToUser(Long userId, Integer month, Integer year) throws Exception;

    List<UserTaskRelation> addAssignedUsersToTask(Task task, List<Long> users) throws Exception;

    void removeAllAssignedUsers(Task task) throws Exception;

    Task updateStatus(Long id, String status) throws Exception;

    TaskUserResponseDTO read(Long id) throws Exception;

    List<TaskResponseDTO> readAllAssignedByUser(Long assignedByUserId, Integer month, Integer year) throws Exception;

    Page<Task> readAllAssignedByUser(Long assignedByUserId, Integer month, Integer year, Integer page) throws Exception;

}
