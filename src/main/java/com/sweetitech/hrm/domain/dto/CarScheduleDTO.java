package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarScheduleDTO {

    private Long carScheduleId;

    private Long driverId;

    private Long carRequestId;

    private Long carId;

    public Long getCarScheduleId() {
        return carScheduleId;
    }

    public void setCarScheduleId(Long carScheduleId) {
        this.carScheduleId = carScheduleId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getCarRequestId() {
        return carRequestId;
    }

    public void setCarRequestId(Long carRequestId) {
        this.carRequestId = carRequestId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    @Override
    public String toString() {
        return "CarScheduleDTO{" +
                "carScheduleId=" + carScheduleId +
                ", driverId=" + driverId +
                ", carRequestId=" + carRequestId +
                ", carId=" + carId +
                '}';
    }
}
