package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfficeHourDateDTO {

    @NotNull
    private Integer dayNumber;

    private String dayName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date date = new Date();

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "OfficeHourDateDTO{" +
                "dayNumber=" + dayNumber +
                ", dayName='" + dayName + '\'' +
                ", date=" + date +
                '}';
    }
}
