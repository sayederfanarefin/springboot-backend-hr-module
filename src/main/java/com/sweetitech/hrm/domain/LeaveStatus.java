package com.sweetitech.hrm.domain;

import com.sweetitech.hrm.common.Constants;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "leave_status")
public class LeaveStatus extends BaseEntity {

    @NotNull
    private String status = Constants.RequestStatus.REQUESTED;

    @NotNull
    private Date statusDate = new Date();

    private String reason;

    @NotNull
    @OneToOne
    private User approvedBy;

    public LeaveStatus() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    @Override
    public String toString() {
        return "LeaveStatus{" +
                "status='" + status + '\'' +
                ", statusDate=" + statusDate +
                ", reason='" + reason + '\'' +
                ", approvedBy=" + approvedBy +
                '}';
    }
}
