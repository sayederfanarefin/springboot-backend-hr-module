package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.ConstantsUtil;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.Notification;
import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.NotificationResponseDTO;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;
import com.sweetitech.hrm.domain.dto.TaskUserResponseDTO;
import com.sweetitech.hrm.domain.dto.UserSmallResponseDTO;
import com.sweetitech.hrm.domain.relation.UserTaskRelation;
import com.sweetitech.hrm.repository.TaskRepository;
import com.sweetitech.hrm.repository.UserTaskRepository;
import com.sweetitech.hrm.service.PushNotificationService;
import com.sweetitech.hrm.service.TaskService;
import com.sweetitech.hrm.service.mapping.NotificationMapper;
import com.sweetitech.hrm.service.mapping.TaskResponseMapper;
import com.sweetitech.hrm.service.mapping.UserSmallResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private UserTaskRepository userTaskRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    private TaskResponseMapper taskResponseMapper;

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserTaskRepository userTaskRepository) {
        this.taskRepository = taskRepository;
        this.userTaskRepository = userTaskRepository;
    }

    @Override
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task update(Long id, Task task) throws Exception {
        Task taskOld = taskRepository.getFirstById(id);
        if (taskOld == null) {
            throw new EntityNotFoundException("No tasks with id: " + id);
        }

        task.setId(id);
        return taskRepository.save(task);
    }

    @Override
    public Task update(Long id, Task task, List<Long> users) throws Exception {
        Task taskNew = this.update(id, task);
        if (taskNew == null) {
            throw new EntityNotFoundException("Failed to update task!");
        }

        this.removeAllAssignedUsers(taskNew);

        if (users != null)
            this.addAssignedUsersToTask(taskNew, users);

        return taskNew;
    }

    @Override
    public void cancel(Long id) throws Exception {
        Task task = taskRepository.getFirstById(id);
        if (task == null) {
            throw new EntityNotFoundException("No tasks with id: " + id);
        }

        task.setStatus(Constants.RequestStatus.CANCELLED);
        taskRepository.save(task);
    }

    @Override
    public Task create(Task task, List<Long> users) throws Exception {
        if (users == null && !task.isForAll()) {
            throw new EntityNotFoundException("Task needs to set for all users!");
        }

        if (task.isForAll() && task.getCompany() == null) {
            throw new EntityNotFoundException("Company cannot be null!");
        }

        Task taskCreated = this.create(task);

        if (!task.isForAll()) {
            this.addAssignedUsersToTask(taskCreated, users);

            if (userTaskRepository.getFirstByUserIdAndTaskId(taskCreated.getAssignedBy().getId(), taskCreated.getId()) == null) {
                userTaskRepository.save(new UserTaskRelation(task, taskCreated.getAssignedBy()));
            }

            if (users != null && users.size() > 0) {
                NotificationResponseDTO notification = null;

                for (Long user: users) {
                    notification = notificationService.create(notificationMapper.map(task, task.isForAll(), user, task.getCompany().getId()));

                    pushNotificationService.pushNotification(notification, user);
                }
            }

            // pushNotificationService.sendPushTaskByUserIds(taskCreated, users);

        }else{
            NotificationResponseDTO notification = notificationService.create(notificationMapper.map(task, task.isForAll(), null, task.getCompany().getId()));

            pushNotificationService.sendPushNotificationByUsers(notification, userService.readByCompanyId(notification.getCompany().getCompanyId()));

            //send push to all users
            // pushNotificationService.sendPushTaskById(taskCreated.getId(),true);
        }

        return taskCreated;
    }

    @Override
    public List<Task> readAllByUserId(Long userId, Integer month, Integer year) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year");
        }

        List<Task> tasks = new ArrayList<>();

        tasks.addAll(taskRepository.findAllApprovedTasksByMonthAndYear(year, month, Constants.RequestStatus.APPROVED, user.getCompany().getId()));

        List<UserTaskRelation> relations = userTaskRepository
                .findAllByUserIdAndTaskStatusAndTaskScheduledDateBetweenOrderByTaskScheduledDateAsc(userId,
                        Constants.RequestStatus.APPROVED,
                        DateTimeUtils.getStartOfMonth(month, year).getTime(),
                        DateTimeUtils.getEndOfMonth(month, year).getTime());

        if (relations != null && relations.size() > 0) {
            for (UserTaskRelation relation: relations) {
                tasks.add(relation.getTask());
            }
        }

        return tasks;
    }

    @Override
    public List<TaskResponseDTO> readAllAssignedToUser(Long userId, Integer month, Integer year) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year");
        }

        List<TaskResponseDTO> dtos = new ArrayList<>();

        List<UserTaskRelation> relations = userTaskRepository
                .findAllByUserIdAndTaskStatusAndTaskScheduledDateBetweenOrderByTaskScheduledDateAsc(userId,
                        Constants.RequestStatus.APPROVED,
                        DateTimeUtils.getStartOfMonth(month, year).getTime(),
                        DateTimeUtils.getEndOfMonth(month, year).getTime());
        if (relations != null && relations.size() > 0) {
            for (UserTaskRelation relation: relations) {
                dtos.add(taskResponseMapper.map(relation.getTask()));
            }
        }

        return dtos;
    }

    @Override
    public List<UserTaskRelation> addAssignedUsersToTask(Task task, List<Long> users) throws Exception {
        List<UserTaskRelation> relations = new ArrayList<>();

        try {
            // Check if all users in list are genuine
            for (Long id: users) {
                if (userService.read(id) == null) {
                    taskRepository.delete(task);
                    throw new EntityNotFoundException("No user with id: " + id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException("Invalid set of users found!");
        }

        // Add relation entry for user and task
        for (Long id: users) {
            User user = userService.read(id);
            relations.add(userTaskRepository.save(new UserTaskRelation(task, user)));
        }

        return relations;
    }

    @Override
    public void removeAllAssignedUsers(Task task) throws Exception {
        List<UserTaskRelation> relations = new ArrayList<>();

        relations = userTaskRepository.findAllByTaskId(task.getId());

        for (UserTaskRelation relation: relations) {
            userTaskRepository.deleteById(relation.getId());
        }
    }

    @Override
    public Task updateStatus(Long id, String status) throws Exception {
        Task task = taskRepository.getFirstById(id);
        if (task == null) {
            throw new EntityNotFoundException("No tasks with id: " + id);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status text");
        }

        task.setStatus(status);
        return taskRepository.save(task);
    }

    @Override
    public TaskUserResponseDTO read(Long id) throws Exception {
        Task task = taskRepository.getFirstById(id);
        if (task == null) {
            throw new EntityNotFoundException("No tasks with id: " + id);
        }

        TaskUserResponseDTO dto = new TaskUserResponseDTO();

        dto.setTask(taskResponseMapper.map(task));

        List<UserSmallResponseDTO> dtos = new ArrayList<>();
        List<UserTaskRelation> relations = userTaskRepository.findAllByTaskId(id);
        if (relations != null && relations.size() > 0) {
            for (UserTaskRelation relation: relations) {
                dtos.add(userSmallResponseMapper.map(relation.getUser()));
            }
        }

        dto.setUsers(dtos);

        return dto;
    }

    @Override
    public List<TaskResponseDTO> readAllAssignedByUser(Long assignedByUserId, Integer month, Integer year) throws Exception {
        if (userService.read(assignedByUserId) == null) {
            throw new EntityNotFoundException("No user with id: " + assignedByUserId);
        }

        if(!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        List<TaskResponseDTO> dtos = new ArrayList<>();

        List<Task> tasks = taskRepository.findAllApprovedTasksAssignedByMeByMonthAndYear(year, month,
                Constants.RequestStatus.APPROVED, assignedByUserId);

        if (tasks != null && tasks.size() > 0) {
            for (Task task: tasks) {
                dtos.add(taskResponseMapper.map(task));
            }
        }

        return dtos;
    }

    @Override
    public Page<Task> readAllAssignedByUser(Long assignedByUserId, Integer month, Integer year, Integer page) throws Exception {
        if (userService.read(assignedByUserId) == null) {
            throw new EntityNotFoundException("No user with id: " + assignedByUserId);
        }

        if(!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        return taskRepository.findAllApprovedTasksAssignedByMeByMonthAndYear(year, month,
                Constants.RequestStatus.APPROVED, assignedByUserId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }
}
