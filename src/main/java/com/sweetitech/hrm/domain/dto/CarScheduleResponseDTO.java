package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.common.Constants;
import io.swagger.annotations.ApiModel;

import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarScheduleResponseDTO {

    private Long carScheduleId;

    private DriverResponseDTO driver;

    private CarRequestResponseDTO carRequest;

    private CarResponseDTO car;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date completedOn;

    private Integer occupiedFor = 0;

    public Long getCarScheduleId() {
        return carScheduleId;
    }

    public void setCarScheduleId(Long carScheduleId) {
        this.carScheduleId = carScheduleId;
    }

    public DriverResponseDTO getDriver() {
        return driver;
    }

    public void setDriver(DriverResponseDTO driver) {
        this.driver = driver;
    }

    public CarRequestResponseDTO getCarRequest() {
        return carRequest;
    }

    public void setCarRequest(CarRequestResponseDTO carRequest) {
        this.carRequest = carRequest;
    }

    public CarResponseDTO getCar() {
        return car;
    }

    public void setCar(CarResponseDTO car) {
        this.car = car;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }

    public Integer getOccupiedFor() {
        return occupiedFor;
    }

    public void setOccupiedFor(Integer occupiedFor) {
        this.occupiedFor = occupiedFor;
    }

    @Override
    public String toString() {
        return "CarScheduleResponseDTO{" +
                "carScheduleId=" + carScheduleId +
                ", driver=" + driver +
                ", carRequest=" + carRequest +
                ", car=" + car +
                ", completedOn=" + completedOn +
                ", occupiedFor=" + occupiedFor +
                '}';
    }
}
