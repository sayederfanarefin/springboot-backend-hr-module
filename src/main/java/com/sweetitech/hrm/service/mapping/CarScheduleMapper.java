package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Car;
import com.sweetitech.hrm.domain.CarRequest;
import com.sweetitech.hrm.domain.CarSchedule;
import com.sweetitech.hrm.domain.Driver;
import com.sweetitech.hrm.domain.dto.CarScheduleDTO;
import com.sweetitech.hrm.service.implementation.CarRequestServiceImpl;
import com.sweetitech.hrm.service.implementation.CarServiceImpl;
import com.sweetitech.hrm.service.implementation.DriverServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarScheduleMapper {

    @Autowired
    private CarServiceImpl carService;

    @Autowired
    private CarRequestServiceImpl carRequestService;

    @Autowired
    private DriverServiceImpl driverService;

    /**
     *
     * @param dto
     * @return entity
     */
    public CarSchedule map(CarScheduleDTO dto) throws Exception {
        if (dto.getCarId() == null || dto.getCarRequestId() == null || dto.getDriverId() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        Car car = carService.readEntity(dto.getCarId());
        if (car == null) {
            throw new EntityNotFoundException("No cars with id: " + dto.getCarId());
        }

        CarRequest carRequest = carRequestService.read(dto.getCarRequestId());
        if (carRequest == null) {
            throw new EntityNotFoundException("No requests with id: " + dto.getCarRequestId());
        }

        Driver driver = driverService.readEntity(dto.getDriverId());
        if (driver == null) {
            throw new EntityNotFoundException("No drivers with id: " + dto.getDriverId());
        }

        CarSchedule entity = new CarSchedule();

        entity.setCar(car);
        entity.setCarRequest(carRequest);
        entity.setDriver(driver);

        return entity;
    }

}
