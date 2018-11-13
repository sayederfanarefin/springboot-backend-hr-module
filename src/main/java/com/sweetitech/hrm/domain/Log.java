package com.sweetitech.hrm.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "logs")
public class Log extends BaseEntity {

    private Long machineNumber;

    private Long indRegId;

    private String dateTimeRecord;

    public Log() {
    }

    public Long getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(Long machineNumber) {
        this.machineNumber = machineNumber;
    }

    public Long getIndRegId() {
        return indRegId;
    }

    public void setIndRegId(Long indRegId) {
        this.indRegId = indRegId;
    }

    public String getDateTimeRecord() {
        return dateTimeRecord;
    }

    public void setDateTimeRecord(String dateTimeRecord) {
        this.dateTimeRecord = dateTimeRecord;
    }

    @Override
    public String toString() {
        return "Log{" +
                "machineNumber=" + machineNumber +
                ", indRegId=" + indRegId +
                ", dateTimeRecord='" + dateTimeRecord + '\'' +
                '}';
    }
}
