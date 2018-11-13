package com.sweetitech.hrm.domain;

import com.sweetitech.hrm.common.Constants;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "office_hours")
public class OfficeHour extends BaseEntity {

    @NotNull
    private Integer dayNumber;

    @NotNull
    private String dayName;

    private String typeOfDay = Constants.Days.WEEK_END;

    private String inTime = "09:30:00";

    private String outTime = "18:00:00";

    private String lastInTime = "10:00:00";

    private String firstOutTime = "17:30:00";

    public OfficeHour() {
    }

    public OfficeHour(@NotNull Integer dayNumber, @NotNull String dayName) {
        this.dayNumber = dayNumber;
        this.dayName = dayName;
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

    public String getTypeOfDay() {
        return typeOfDay;
    }

    public void setTypeOfDay(String typeOfDay) {
        this.typeOfDay = typeOfDay;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getLastInTime() {
        return lastInTime;
    }

    public void setLastInTime(String lastInTime) {
        this.lastInTime = lastInTime;
    }

    public String getFirstOutTime() {
        return firstOutTime;
    }

    public void setFirstOutTime(String firstOutTime) {
        this.firstOutTime = firstOutTime;
    }

    @Override
    public String toString() {
        return "OfficeHour{" +
                "dayNumber=" + dayNumber +
                ", dayName='" + dayName + '\'' +
                ", typeOfDay='" + typeOfDay + '\'' +
                ", inTime='" + inTime + '\'' +
                ", outTime='" + outTime + '\'' +
                ", lastInTime='" + lastInTime + '\'' +
                ", firstOutTime='" + firstOutTime + '\'' +
                '}';
    }
}
