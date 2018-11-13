package com.sweetitech.hrm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sweetitech.hrm.common.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    private boolean forAll;

    @OneToOne
    private Company company;

    @NotNull
    private String resourceType;

    @NotNull
    private Long resourceId;

    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date createdOn = new Date();

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        return "Notification{" +
                "id=" + id +
                ", user=" + user +
                ", forAll=" + forAll +
                ", company=" + company +
                ", resourceType='" + resourceType + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }
}
