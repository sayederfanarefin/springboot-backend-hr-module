package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.domain.Device;
import com.sweetitech.hrm.repository.DeviceRepository;
import com.sweetitech.hrm.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private DeviceRepository deviceRepository;

    @Autowired
    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Device read(long id) {
        return deviceRepository.findById(id);
    }

    @Override
    public Device readByMac(String mac) {
        return deviceRepository.findByMac(mac);
    }

    @Override
    public List<Device> readAll() {
        return deviceRepository.findAllByOrderByDeviceNameAsc();
    }
}
