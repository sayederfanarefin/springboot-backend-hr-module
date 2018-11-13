package com.sweetitech.hrm.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "weekends")
public class Weekend extends BaseEntity {

    @NotNull
    private Integer dayNumber;

    @NotNull
    private String dayName;

    public Weekend() {
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    @Override
    public String toString() {
        return "Weekend{" +
                "dayNumber=" + dayNumber +
                ", dayName='" + dayName + '\'' +
                '}';
    }
}
