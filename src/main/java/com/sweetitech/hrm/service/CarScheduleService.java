package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Car;
import com.sweetitech.hrm.domain.CarRequest;
import com.sweetitech.hrm.domain.CarSchedule;
import com.sweetitech.hrm.domain.Driver;
import com.sweetitech.hrm.domain.dto.CarScheduleDTO;
import com.sweetitech.hrm.domain.dto.CarScheduleResponseDTO;
import com.sweetitech.hrm.domain.dto.page.CarSchedulePage;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface CarScheduleService {

    CarSchedule readEntity(Long id) throws Exception;

    CarScheduleResponseDTO read(Long id) throws Exception;

    CarScheduleResponseDTO create(CarSchedule carSchedule) throws Exception;

    CarScheduleResponseDTO update(Long id, CarSchedule carSchedule) throws Exception;

    CarScheduleResponseDTO updateCompletedDate(Long id, Date date) throws Exception;

    CarSchedulePage readAllByDateAndCompanyId(Date date, Long companyId, Integer page) throws Exception;

    CarSchedulePage readAllByCar(Long carId, Integer page) throws Exception;

    CarSchedulePage readAllByDriver(Long driverId, Integer page) throws Exception;

    CarScheduleResponseDTO readByRequest(Long carRequestId) throws Exception;

    void remove(Long id) throws Exception;

    CarSchedulePage readAllCompletedByCompanyId(Long companyId, Integer page) throws Exception;

    CarSchedulePage readAllNotCompletedByCompanyId(Long companyId, Integer page) throws Exception;

    CarSchedulePage readAllByUserId(Long userId, Integer page) throws Exception;

    boolean checkCarStatus(Long carId, Date fromDate, Integer fromHour, Date toDate, Integer toHour) throws Exception;

    boolean checkDriverStatus(Long carId, Date fromDate, Integer fH, Date toDate, Integer tH) throws Exception;

    Driver readDriverBySchedule(Long carId, Date fromDate, Integer fH, Date toDate, Integer tH) throws Exception;

}
