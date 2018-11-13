package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.CarRequest;
import com.sweetitech.hrm.domain.dto.CarRequestResponseDTO;
import com.sweetitech.hrm.domain.dto.CarScheduleResponseDTO;
import com.sweetitech.hrm.domain.dto.page.CarRequestPage;

import java.util.Date;

public interface CarRequestService {

    CarRequest read(Long id) throws Exception;

    CarRequestResponseDTO readDTO(Long id) throws Exception;

    CarRequestResponseDTO create(CarRequest carRequest) throws Exception;

    CarRequestResponseDTO update(Long id, CarRequest carRequest) throws Exception;

    void cancel(Long id) throws Exception;

    CarRequestPage readAllByRequestedByUserId(Long userId, String status, Integer page) throws Exception;

    CarRequestPage readAllByCompanyIdAndDate(Long companyId, Date date, String status, Integer page) throws Exception;

    CarRequestPage readAllByCompanyIdAndMonth(Long companyId, Integer year, Integer month, String status, Integer page) throws Exception;

    CarRequestResponseDTO updateStatus(Long id, String status) throws Exception;

    CarScheduleResponseDTO deleteScheduleAndCancel(Long requestId) throws Exception;

}
