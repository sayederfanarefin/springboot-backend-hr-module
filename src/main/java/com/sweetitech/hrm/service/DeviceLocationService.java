package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.DeviceLocation;

import java.util.List;

public interface DeviceLocationService {

    DeviceLocation create(DeviceLocation deviceLocation) throws Exception;

    DeviceLocation update(Long id, DeviceLocation deviceLocation) throws Exception;

    List<DeviceLocation> readAllByCompanyId(Long companyId) throws Exception;

    void remove(Long id) throws Exception;

    void activate(Long id) throws Exception;

    List<DeviceLocation> readAllInactive();

    DeviceLocation read(Long id) throws Exception;

    boolean readByDeviceId(String deviceId) throws Exception;

    DeviceLocation readByIdOfDevice(Long id) throws Exception;

}
