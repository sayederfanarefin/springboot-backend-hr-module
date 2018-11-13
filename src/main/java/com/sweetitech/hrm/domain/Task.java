package com.sweetitech.hrm.domain;

import com.sweetitech.hrm.common.Constants;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    @NotNull
    private Date scheduledDate = new Date();

    private String description;

    private String dependency;

    @OneToOne
    private User assignedTo;

    @NotNull
    @OneToOne
    private User assignedBy;

    private String status;

    @NotNull
    private String category = Constants.Tasks.TODO;

    private boolean forAll = false;

    @NotNull
    @OneToOne
    private Company company;

    @OneToOne
    private Image file;

    public Task() {
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

    public User getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(User assignedTo) {
        this.assignedTo = assignedTo;
    }

    public User getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(User assignedBy) {
        this.assignedBy = assignedBy;
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

    public Image getFile() {
        return file;
    }

    public void setFile(Image file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Task{" +
                "scheduledDate=" + scheduledDate +
                ", description='" + description + '\'' +
                ", dependency='" + dependency + '\'' +
                ", assignedTo=" + assignedTo +
                ", assignedBy=" + assignedBy +
                ", status='" + status + '\'' +
                ", category='" + category + '\'' +
                ", forAll=" + forAll +
                ", company=" + company +
                ", file=" + file +
                '}';
    }
}
