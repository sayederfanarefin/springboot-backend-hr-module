package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.Device;
import com.sweetitech.hrm.domain.DeviceLocation;
import com.sweetitech.hrm.repository.DeviceLocationRepository;
import com.sweetitech.hrm.repository.DeviceRepository;
import com.sweetitech.hrm.service.DeviceLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DeviceLocationServiceImpl implements DeviceLocationService {

    private DeviceLocationRepository deviceLocationRepository;
    private DeviceRepository deviceRepository;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    public DeviceLocationServiceImpl(DeviceLocationRepository deviceLocationRepository, DeviceRepository deviceRepository) {
        this.deviceLocationRepository = deviceLocationRepository;
        this.deviceRepository = deviceRepository;
    }

    @Override
    public DeviceLocation create(DeviceLocation deviceLocation) throws Exception {
        // TODO remove the following condition to allow same device for multiple companies
//        DeviceLocation deviceLocationOld = deviceLocationRepository.getFirstByDeviceMac(deviceLocation.getDevice().getMac());
//        if (deviceLocationOld != null) {
//            throw new Exception("The device is already mapped to a location. Consider updating or activating the device!");
//        }

        return deviceLocationRepository.save(deviceLocation);
    }

    @Override
    public DeviceLocation update(Long id, DeviceLocation deviceLocation) throws Exception {
        DeviceLocation deviceLocationOld = deviceLocationRepository.getFirstById(id);
        if (deviceLocationOld == null) {
            throw new Exception("No device is mapped to any location with id: " + id);
        }

        DeviceLocation check = deviceLocationRepository.getFirstByDeviceMac(deviceLocation.getDevice().getMac());
        if (!Objects.equals(check.getId(), id)) {
            throw new EntityNotFoundException("Another location is already mapped with this device");
        }

        deviceLocation.setId(id);
        return deviceLocationRepository.save(deviceLocation);
    }

    @Override
    public List<DeviceLocation> readAllByCompanyId(Long companyId) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        return deviceLocationRepository.findAllByCompanyIdAndIsDeletedFalseOrderByCreatedAsc(companyId);
    }

    @Override
    public void remove(Long id) throws Exception {
        DeviceLocation location = deviceLocationRepository.getFirstById(id);
        if (location == null) {
            throw new EntityNotFoundException("No locations with id: " + id);
        }

        location.setDeleted(true);
        deviceLocationRepository.save(location);
    }

    @Override
    public void activate(Long id) throws Exception {
        DeviceLocation location = deviceLocationRepository.getFirstById(id);
        if (location == null) {
            throw new EntityNotFoundException("No locations with id: " + id);
        }

        location.setDeleted(false);
        deviceLocationRepository.save(location);
    }

    @Override
    public List<DeviceLocation> readAllInactive() {
        return deviceLocationRepository.findAllByIsDeletedTrue();
    }

    @Override
    public DeviceLocation read(Long id) throws Exception {
        return deviceLocationRepository.getFirstById(id);
    }

    @Override
    public boolean readByDeviceId(String deviceId) throws Exception {
        if (deviceLocationRepository.getFirstByDeviceDeviceId(deviceId) == null) {
            return false;
        }
        return true;
    }

    @Override
    public DeviceLocation readByIdOfDevice(Long id) throws Exception {
        return null;
    }
}
