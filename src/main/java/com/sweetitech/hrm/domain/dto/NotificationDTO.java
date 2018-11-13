package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDTO {

    private Long notificationId;

    private Long userId;

    private boolean forAll;

    private Long companyId;

    private String resourceType;

    private Long resourceId;

    private String title;

    private String body;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date createdOn = new Date();

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", forAll=" + forAll +
                ", companyId=" + companyId +
                ", resourceType='" + resourceType + '\'' +
                ", resourceId=" + resourceId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
