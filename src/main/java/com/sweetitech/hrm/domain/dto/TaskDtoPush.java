package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.User;
import io.swagger.annotations.ApiModel;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDtoPush {

    private Long TaskId;

    private Date scheduledDate ;

    private String description;

    private String dependency;

    private String status;

    private String category = Constants.Tasks.TODO;

    private boolean forAll ;

    private Company company;

    private UserSmallResponseDTO assignedTo;

    private UserSmallResponseDTO assignedBy;

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isForAll() {
        return forAll;
    }

    public void setForAll(boolean forAll) {
        this.forAll = forAll;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public UserSmallResponseDTO getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(UserSmallResponseDTO assignedTo) {
        this.assignedTo = assignedTo;
    }

    public UserSmallResponseDTO getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(UserSmallResponseDTO assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Long getTaskId() {
        return TaskId;
    }

    public void setTaskId(Long taskId) {
        TaskId = taskId;
    }
}
