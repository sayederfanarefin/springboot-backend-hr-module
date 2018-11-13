package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.Device;
import com.sweetitech.hrm.domain.DeviceLocation;
import com.sweetitech.hrm.domain.dto.DeviceLocationDTO;
import com.sweetitech.hrm.service.implementation.CompanyServiceImpl;
import com.sweetitech.hrm.service.implementation.DeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceLocationMapper {

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private DeviceServiceImpl deviceService;

    /**
     * Maps DTO to Entity
     *
     * @param dto
     * @return entity
     */
    public DeviceLocation map(DeviceLocationDTO dto) throws Exception {
        Company company = companyService.read(dto.getCompanyId());
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + dto.getCompanyId());
        }

        Device device = deviceService.read(dto.getDeviceId());
        if (device == null) {
            throw new EntityNotFoundException("No devices with id: " + dto.getDeviceId());
        }

        DeviceLocation entity = new DeviceLocation();
        entity.setCompany(company);
        entity.setLocation(dto.getLocation());
        entity.setDevice(device);

        return entity;
    }

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public DeviceLocationDTO map(DeviceLocation entity) throws Exception {
        DeviceLocationDTO dto = new DeviceLocationDTO();

        dto.setLocationId(entity.getId());
        dto.setCompanyId(entity.getCompany().getId());
        dto.setLocation(entity.getLocation());
        dto.setDeviceId(entity.getDevice().getId());

        return dto;
    }

}
