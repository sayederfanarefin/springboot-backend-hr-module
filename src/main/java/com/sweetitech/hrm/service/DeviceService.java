package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Device;

import java.util.List;

public interface DeviceService {

    Device read(long id);

    Device readByMac(String mac);

    List<Device> readAll();

}
