package com.sweetitech.hrm.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sweetitech.hrm.common.Constants;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "car_schedules")
public class CarSchedule extends BaseEntity {

    @NotNull
    @OneToOne
    private Driver driver;

    @NotNull
    @OneToOne
    private CarRequest carRequest;

    @NotNull
    @OneToOne
    private Car car;

    @JsonFormat(pattern = Constants.Dates.ACCEPTED_FORMAT, timezone = Constants.Dates.TIMEZONE)
    private Date completedOn;

    private Integer occupiedFor = 0;

    public CarSchedule() {
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public CarRequest getCarRequest() {
        return carRequest;
    }

    public void setCarRequest(CarRequest carRequest) {
        this.carRequest = carRequest;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
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
        return "CarSchedule{" +
                "driver=" + driver +
                ", carRequest=" + carRequest +
                ", car=" + car +
                ", completedOn=" + completedOn +
                ", occupiedFor=" + occupiedFor +
                '}';
    }
}
