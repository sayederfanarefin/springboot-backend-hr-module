package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, Long> {

    Device findById(long id);

    Device findByMac(String mac);

    List<Device> findAllByOrderByDeviceNameAsc();

}
