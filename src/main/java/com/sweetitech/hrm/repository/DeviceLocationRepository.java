package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.DeviceLocation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeviceLocationRepository extends CrudRepository<DeviceLocation, Long> {

    List<DeviceLocation> findAllByCompanyIdAndIsDeletedFalseOrderByCreatedAsc(Long companyId);

    List<DeviceLocation> findAllByIsDeletedTrue();

    DeviceLocation getFirstByDeviceMac(String Mac);

    DeviceLocation getFirstById(Long id);

    @Query(value = "SELECT * FROM device_locations dl WHERE (SELECT device_id from devices WHERE id = dl.device_id) = :deviceId", nativeQuery = true)
    DeviceLocation getFirstByDeviceDeviceId(@Param("deviceId") String deviceId);

}
