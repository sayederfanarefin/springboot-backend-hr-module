package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class TaskResponseMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public TaskResponseDTO map(Task entity) throws Exception {
        TaskResponseDTO dto = new TaskResponseDTO();

        dto.setTaskId(entity.getId());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(entity.getScheduledDate());
        dto.setDayNumber(calendar.get(Calendar.DAY_OF_MONTH));

        dto.setScheduledDate(entity.getScheduledDate());
        dto.setDescription(entity.getDescription());
        dto.setDependency(entity.getDependency());
        dto.setStatus(entity.getStatus());
        dto.setCategory(entity.getCategory());
        dto.setForAll(entity.isForAll());

        if (entity.getCompany() != null)
            dto.setCompany(entity.getCompany());

        if (entity.getAssignedTo() != null) {
            dto.setAssignedToUser(userSmallResponseMapper.map(entity.getAssignedTo()));
        }

        dto.setAssignedByUser(userSmallResponseMapper.map(entity.getAssignedBy()));

        if (entity.getFile() != null)
            dto.setFile(entity.getFile());

        return dto;
    }

}
