package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Driver;
import com.sweetitech.hrm.domain.dto.DriverResponseDTO;
import com.sweetitech.hrm.domain.dto.page.DriverPage;

import java.util.Date;
import java.util.List;

public interface DriverService {

    DriverResponseDTO create(Driver driver) throws Exception;

    DriverResponseDTO update(Long id, Driver driver) throws Exception;

    Driver readEntity(Long id) throws Exception;

    DriverResponseDTO read(Long id) throws Exception;

    void remove(Long id) throws Exception;

    DriverPage readAllActiveByCompanyId(Long companyId, Integer page) throws Exception;

    DriverPage readAllInactiveByCompanyId(Long companyId, Integer page) throws Exception;

    DriverResponseDTO reactivate(Long id) throws Exception;

    List<DriverResponseDTO> readAllAvailable(Long companyId, Date fromDate, Integer fromHour,
                                             Date toDate, Integer toHour, Long carId) throws Exception;

}
