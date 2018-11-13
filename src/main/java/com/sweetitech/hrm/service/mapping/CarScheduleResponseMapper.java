package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.CarSchedule;
import com.sweetitech.hrm.domain.dto.CarScheduleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarScheduleResponseMapper {

    @Autowired
    private CarResponseMapper carResponseMapper;

    @Autowired
    private CarRequestResponseMapper carRequestResponseMapper;

    @Autowired
    private DriverResponseMapper driverResponseMapper;

    /**
     *
     * @param entity
     * @return dto
     */
    public CarScheduleResponseDTO map(CarSchedule entity) throws Exception {
        CarScheduleResponseDTO dto = new CarScheduleResponseDTO();

        dto.setCarScheduleId(entity.getId());
        dto.setCar(carResponseMapper.map(entity.getCar()));
        dto.setCarRequest(carRequestResponseMapper.map(entity.getCarRequest()));
        dto.setDriver(driverResponseMapper.map(entity.getDriver()));
        dto.setCompletedOn(entity.getCompletedOn());
        dto.setOccupiedFor(entity.getOccupiedFor());

        return dto;
    }

}
