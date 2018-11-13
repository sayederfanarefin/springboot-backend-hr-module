package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.Date;
import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDTO {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date fromDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date toDate;

    private String description;

    private String dependency;

    private List<Long> assignedToUserIds;

    private Long assignedByUserId;

    private String status;

    private String category;

    private boolean forAll;

    private Long companyId;

    private Long fileId;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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

    public List<Long> getAssignedToUserIds() {
        return assignedToUserIds;
    }

    public void setAssignedToUserIds(List<Long> assignedToUserIds) {
        this.assignedToUserIds = assignedToUserIds;
    }

    public Long getAssignedByUserId() {
        return assignedByUserId;
    }

    public void setAssignedByUserId(Long assignedByUserId) {
        this.assignedByUserId = assignedByUserId;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", description='" + description + '\'' +
                ", dependency='" + dependency + '\'' +
                ", assignedToUserIds=" + assignedToUserIds +
                ", assignedByUserId=" + assignedByUserId +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", forAll=" + forAll +
                ", companyId=" + companyId +
                ", fileId=" + fileId +
                '}';
    }
}
