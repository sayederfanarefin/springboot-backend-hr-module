package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.Image;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskResponseDTO {

    private Long taskId;

    private Integer dayNumber;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date scheduledDate;

    private String description;

    private String dependency;

    private String status;

    private String category;

    private UserSmallResponseDTO assignedToUser;

    private UserSmallResponseDTO assignedByUser;

    private boolean forAll;

    private Company company;

    private Image file;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

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

    public UserSmallResponseDTO getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(UserSmallResponseDTO assignedToUser) {
        this.assignedToUser = assignedToUser;
    }

    public UserSmallResponseDTO getAssignedByUser() {
        return assignedByUser;
    }

    public void setAssignedByUser(UserSmallResponseDTO assignedByUser) {
        this.assignedByUser = assignedByUser;
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

    public Image getFile() {
        return file;
    }

    public void setFile(Image file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "TaskResponseDTO{" +
                "taskId=" + taskId +
                ", dayNumber=" + dayNumber +
                ", scheduledDate=" + scheduledDate +
                ", description='" + description + '\'' +
                ", dependency='" + dependency + '\'' +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", assignedToUser=" + assignedToUser +
                ", assignedByUser=" + assignedByUser +
                ", forAll=" + forAll +
                ", company=" + company +
                ", file=" + file +
                '}';
    }
}
