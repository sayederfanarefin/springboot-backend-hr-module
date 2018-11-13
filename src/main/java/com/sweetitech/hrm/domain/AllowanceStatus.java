package com.sweetitech.hrm.domain;

import com.sweetitech.hrm.common.Constants;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "allowance_status")
public class AllowanceStatus extends BaseEntity {

    @NotNull
    private Date statusDate = new Date();

    @NotNull
    private String status = Constants.RequestStatus.REQUESTED;

    @NotNull
    @OneToOne
    private User decisionBy;

    public AllowanceStatus() {
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getDecisionBy() {
        return decisionBy;
    }

    public void setDecisionBy(User decisionBy) {
        this.decisionBy = decisionBy;
    }

    @Override
    public String toString() {
        return "AllowanceStatus{" +
                "statusDate=" + statusDate +
                ", status='" + status + '\'' +
                ", decisionBy=" + decisionBy +
                '}';
    }
}
