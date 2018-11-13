package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.TaskDTO;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;
import com.sweetitech.hrm.repository.TaskRepository;
import com.sweetitech.hrm.service.HolidayService;
import com.sweetitech.hrm.service.mapping.NotificationMapper;
import com.sweetitech.hrm.service.mapping.TaskResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HolidayServiceImpl implements HolidayService {

    private TaskRepository taskRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TaskResponseMapper taskResponseMapper;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    public HolidayServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    boolean isValidMonth(int month) {
        return (month > 0 && month <= 12);
    }

    boolean isValidYear(int year) {
        return (year >= 1800 && year <= 2100);
    }

    public List<TaskResponseDTO> fetchDTOByMonthAndYear(Integer month, Integer year, Long userId) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        return this.readDTOByMonthAndYear(month, year, user.getCompany().getId());
    }

    @Override
    public Task create(Task task) throws Exception {
        Task taskCreated = taskRepository.save(task);

        notificationService.create(notificationMapper.map(taskCreated, taskCreated.isForAll(), null, taskCreated.getCompany().getId()));
        return taskCreated;
    }

    @Override
    public Task update(Long id, TaskDTO task) throws Exception {
        Task taskOld = taskRepository.getFirstById(id);
        if (taskOld == null) {
            throw new EntityNotFoundException("No holidays with id: " + id);
        }

        taskOld.setDescription(task.getDescription());
        taskOld.setDependency(task.getDependency());

        if (task.getAssignedByUserId() == null) {
            throw new EntityNotFoundException("Holiday must be assigned by a user!");
        }

        User user = userService.read(task.getAssignedByUserId());
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + task.getAssignedByUserId());
        }

        taskOld.setAssignedBy(user);

        return taskRepository.save(taskOld);
    }

    @Override
    public Task cancel(Long id) throws Exception {
        Task task = taskRepository.getFirstById(id);
        if (task == null) {
            throw new EntityNotFoundException("No holidays with id: " + id);
        }

        task.setStatus(Constants.RequestStatus.CANCELLED);
        return taskRepository.save(task);
    }

    @Override
    public Task read(Long id) throws Exception {
        return taskRepository.getFirstById(id);
    }

    @Override
    public List<Task> readByDesc(String description, Integer year) throws Exception {
        if (!isValidYear(year)) {
            throw new EntityNotFoundException("Invalid year!");
        }

        return taskRepository.findAllApprovedHolidaysByDescriptionAndYear(description, year,
                Constants.RequestStatus.APPROVED, Constants.Tasks.HOLIDAY);
    }

    @Override
    public Page<Task> readByDesc(String description, Integer year, Long companyId, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        if (!isValidYear(year)) {
            throw new EntityNotFoundException("Invalid year!");
        }

        return taskRepository.findAllApprovedHolidaysByDescriptionAndYear(description, year,
                Constants.RequestStatus.APPROVED, Constants.Tasks.HOLIDAY, companyId,
                new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public List<Task> readByMonthAndYear(Integer month, Integer year, Long companyId) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        if (!isValidYear(year) || !isValidMonth(month)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        return taskRepository.findAllHolidaysByMonthAndYear(year, month,
                Constants.RequestStatus.APPROVED, Constants.Tasks.HOLIDAY, companyId);
    }

    @Override
    public List<TaskResponseDTO> readDTOByMonthAndYear(Integer month, Integer year, Long companyId) throws Exception {
        List<Task> tasks = this.readByMonthAndYear(month, year, companyId);
        List<TaskResponseDTO> dtos = new ArrayList<>();

        for (Task task: tasks) {
            dtos.add(taskResponseMapper.map(task));
        }

        return dtos;
    }

    @Override
    public Task readHoliday(Date scheduledDate, String category, String status, Long companyId) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        if (scheduledDate == null) {
            throw new EntityNotFoundException("Null scheduled date found!");
        }

        return taskRepository.getFirstByScheduledDateAndCategoryAndStatusAndCompanyId(scheduledDate, category, status, companyId);
    }
}
