package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.OfficeHour;
import com.sweetitech.hrm.domain.Task;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.TaskDTO;
import com.sweetitech.hrm.service.implementation.CompanyServiceImpl;
import com.sweetitech.hrm.service.implementation.OfficeHourServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class HolidayMapper {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CompanyServiceImpl companyService;

    /**
     * Maps DTO to Entity
     *
     * @param dto
     * @return List
     */
    public List<Task> map(TaskDTO dto) throws Exception {
        List<Task> entities = new ArrayList<>();

        Task entity;

        if (dto.getFromDate() == null || dto.getToDate() == null) {
            throw new EntityNotFoundException("Null date found!");
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
            if (assignedBy == null) {
                throw new EntityNotFoundException("No user with id: " + dto.getAssignedByUserId());
            }

            entity.setAssignedBy(assignedBy);
            entity.setStatus(Constants.RequestStatus.APPROVED);
            entity.setCategory(Constants.Tasks.HOLIDAY);
            entity.setForAll(true);

            if (company == null)
                entity.setCompany(assignedBy.getCompany());
            else
                entity.setCompany(company);

            entities.add(entity);

            temp.add(Calendar.DATE, 1);
        }

        return entities;
    }

}
