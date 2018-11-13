package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.domain.*;
import com.sweetitech.hrm.domain.dto.TaskDTO;
import com.sweetitech.hrm.service.implementation.CompanyServiceImpl;
import com.sweetitech.hrm.service.implementation.ImageServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskMapper {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private ImageServiceImpl imageService;

    /**
     *
     * @param dto
     * @return entity
     */
    public List<Task> map(TaskDTO dto) throws Exception {
        List<Task> entities = new ArrayList<>();

        Task entity;

        if (dto.getFromDate() == null || dto.getToDate() == null) {
            throw new EntityNotFoundException("Null date found!");
        }
        if (!DateTimeUtils.isValidDate(dto.getFromDate().toString(), "E MMM dd HH:mm:ss z yyyy")) {
            throw new EntityNotFoundException("Invalid date found!");
        }
        if (!DateTimeUtils.isValidDate(dto.getToDate().toString(), "E MMM dd HH:mm:ss z yyyy")) {
            throw new EntityNotFoundException("Invalid date found!");
        }

        if (dto.getAssignedByUserId() == null || userService.read(dto.getAssignedByUserId()) == null) {
            throw new EntityNotFoundException("Invalid user for assigned by!");
        }

        Image file = null;
        if (dto.getFileId() != null) {
            file = imageService.findById(dto.getFileId());
            if (file == null) {
                throw new EntityNotFoundException("No such attachments exist!");
            }
        }

        Company company = null;
        if (dto.getCompanyId() != null) {
            company = companyService.read(dto.getCompanyId());
            if (company == null) {
                throw new EntityNotFoundException("No company with id: " + dto.getCompanyId());
            }
        }

        Calendar calStart = Calendar.getInstance();
        calStart.setTime(dto.getFromDate());
        calStart = DateTimeUtils.getStartOfDay(calStart);

        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dto.getToDate());
        calEnd = DateTimeUtils.getEndOfDay(calEnd);

        if (calEnd.before(calStart)) {
            throw new EntityNotFoundException("To date is before start date!");
        }

        Calendar temp = Calendar.getInstance();
        temp = calStart;

        int consideredDays = ((int) Duration.between(calStart.toInstant(), calEnd.toInstant()).toDays() + 1);

        for (int i = 0; i < consideredDays; i++) {
            entity = new Task();

            entity.setScheduledDate(temp.getTime());
            entity.setDescription(dto.getDescription());
            entity.setDependency(dto.getDependency());

            User assignedBy = userService.read(dto.getAssignedByUserId());
            entity.setAssignedBy(assignedBy);

            if (dto.getStatus() == null)
                entity.setStatus(Constants.RequestStatus.APPROVED);
            else
                entity.setStatus(dto.getStatus());

            entity.setCategory(dto.getCategory());
            entity.setForAll(dto.isForAll());

            if (company == null)
                entity.setCompany(assignedBy.getCompany());
            else
                entity.setCompany(company);

            entity.setFile(file);

            entities.add(entity);

            temp.add(Calendar.DATE, 1);
        }

        return entities;
    }

    /**
     *
     * @param dto
     * @return entity
     */
    public Task map(Tender dto, Date date, Long assignedByUserId) throws Exception {
        Task entity = new Task();

        entity.setScheduledDate(date);
        entity.setDescription("Please release tender for " + dto.getInstitutionName());

        String dependency = "";
        entity.setDependency(dependency);

        User assignedByUser = userService.read(assignedByUserId);
        if (assignedByUser == null) {
            throw new EntityNotFoundException("No users with id: " + assignedByUserId);
        }
        entity.setAssignedBy(assignedByUser);

        entity.setStatus(Constants.RequestStatus.APPROVED);
        entity.setCategory(Constants.Tasks.TASK);
        entity.setForAll(false);
        entity.setCompany(assignedByUser.getCompany());

        return entity;
    }

}
