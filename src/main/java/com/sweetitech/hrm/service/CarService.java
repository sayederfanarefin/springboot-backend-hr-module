package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Car;
import com.sweetitech.hrm.domain.dto.CarResponseDTO;
import com.sweetitech.hrm.domain.dto.page.CarPage;

import java.util.Date;
import java.util.List;

public interface CarService {

    Car readEntity(Long id) throws Exception;

    CarResponseDTO read(Long id) throws Exception;

    CarResponseDTO create(Car car) throws Exception;

    CarResponseDTO update(Long id, Car car) throws Exception;

    void remove(Long id) throws Exception;

    CarPage readAllActiveByCompanyId(Long companyId, Integer page) throws Exception;

    CarPage readAllInactiveByCompanyId(Long companyId, Integer page) throws Exception;

    CarResponseDTO reactivate(Long id) throws Exception;

    CarResponseDTO updateMaintenanceStatus(Long id, boolean status) throws Exception;

    boolean exists(String regNo) throws Exception;

    List<CarResponseDTO> readAllAvailable(Long companyId, Date fromDate, Integer fromHour,
                                          Date toDate, Integer toHour) throws Exception;

}
