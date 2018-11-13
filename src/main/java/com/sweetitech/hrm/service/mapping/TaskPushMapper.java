package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.dto.TaskDtoPush;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class TaskPushMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public TaskDtoPush map(Task entity) throws Exception {
        TaskDtoPush dto = new TaskDtoPush();

        dto.setTaskId(entity.getId());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(entity.getScheduledDate());
        //dto.setDayNumber(calendar.get(Calendar.DAY_OF_MONTH));

        dto.setScheduledDate(entity.getScheduledDate());
        dto.setDescription(entity.getDescription());
        dto.setDependency(entity.getDependency());
        dto.setStatus(entity.getStatus());
        dto.setCategory(entity.getCategory());
        dto.setForAll(entity.isForAll());

        if (entity.getCompany() != null)
            dto.setCompany(entity.getCompany());

        if (entity.getAssignedTo() != null) {
            dto.setAssignedTo(userSmallResponseMapper.map(entity.getAssignedTo()));
        }

        dto.setAssignedBy(userSmallResponseMapper.map(entity.getAssignedBy()));

        return dto;
    }

}
