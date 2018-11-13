package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.dto.TaskDTO;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface HolidayService {

    Task create(Task task) throws Exception;

    Task update(Long id, TaskDTO task) throws Exception;

    Task cancel(Long id) throws Exception;

    Task read(Long id) throws Exception;

    List<Task> readByDesc(String description, Integer year) throws Exception;

    Page<Task> readByDesc(String description, Integer year, Long companyId, Integer page) throws Exception;

    List<Task> readByMonthAndYear(Integer month, Integer year, Long companyId) throws Exception;

    List<TaskResponseDTO> readDTOByMonthAndYear(Integer month, Integer year, Long companyId) throws Exception;

    Task readHoliday(Date scheduledDate, String category, String status, Long companyId) throws Exception;

}
