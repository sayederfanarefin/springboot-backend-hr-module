package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponseDTO {

    private Long notificationId;

    private UserSmallResponseDTO user;

    private boolean forAll;

    private CompanySmallResponseDTO company;

    private String resourceType;

    private Long resourceId;

    private String title;

    private String body;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date createdOn;

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public UserSmallResponseDTO getUser() {
        return user;
    }

    public void setUser(UserSmallResponseDTO user) {
        this.user = user;
    }

    public boolean isForAll() {
        return forAll;
    }

    public void setForAll(boolean forAll) {
        this.forAll = forAll;
    }

    public CompanySmallResponseDTO getCompany() {
        return company;
    }

    public void setCompany(CompanySmallResponseDTO company) {
        this.company = company;
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
        return "NotificationResponseDTO{" +
                "notificationId=" + notificationId +
                ", user=" + user +
                ", forAll=" + forAll +
                ", company=" + company +
                ", resourceType='" + resourceType + '\'' +
                ", resourceId=" + resourceId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
