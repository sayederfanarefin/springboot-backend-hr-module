package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.relation.UserDeviceRelation;
import com.sweetitech.hrm.repository.UserDeviceRepository;
import com.sweetitech.hrm.service.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDeviceServiceImpl implements UserDeviceService {

    private UserDeviceRepository userDeviceRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DeviceLocationServiceImpl deviceLocationService;

    @Autowired
    public UserDeviceServiceImpl(UserDeviceRepository userDeviceRepository) {
        this.userDeviceRepository = userDeviceRepository;
    }

    public UserDeviceRelation getByMachineAndEnrolNumber(String deviceId, String enrolNumber) throws Exception {
        return userDeviceRepository.getFirstByLocationDeviceDeviceIdAndEnrollmentNumber(deviceId, enrolNumber);
    }

    @Override
    public UserDeviceRelation read(Long id) throws Exception {
        return userDeviceRepository.getFirstById(id);
    }

    @Override
    public UserDeviceRelation create(UserDeviceRelation relation) throws Exception {
        if (relation.getUser() == null || relation.getLocation() == null) {
            throw new EntityNotFoundException("Information missing!");
        }

        if (this.exists(relation.getUser().getId(), relation.getLocation().getId()) != null) {
            throw new EntityNotFoundException("A map already exists with this combination. Try updating the existing one!");
        }

        return userDeviceRepository.save(relation);
    }

    @Override
    public UserDeviceRelation update(Long userId, Long locationId, UserDeviceRelation relation) throws Exception {
        if (relation.getUser() == null || relation.getLocation() == null) {
            throw new EntityNotFoundException("Information missing!");
        }

        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (deviceLocationService.read(locationId) == null) {
            throw new EntityNotFoundException("No locations with id: " + locationId);
        }

        if (userDeviceRepository.getFirstByUserIdAndLocationId(userId, locationId) == null) {
            throw new EntityNotFoundException("No such mappings exist!");
        }

        UserDeviceRelation relationOld = userDeviceRepository.getFirstByUserIdAndLocationId(userId, locationId);
        relationOld.setEnrollmentNumber(relation.getEnrollmentNumber());
        return userDeviceRepository.save(relationOld);
    }

    @Override
    public void remove(Long id) throws Exception {
        if (this.read(id) == null) {
            throw new EntityNotFoundException("No mapping with id: " + id);
        }

        userDeviceRepository.deleteById(id);
    }

    @Override
    public UserDeviceRelation exists(Long userId, Long locationId) throws Exception {
        return userDeviceRepository.getFirstByUserIdAndLocationId(userId, locationId);
    }

    @Override
    public List<UserDeviceRelation> readAllByUserId(Long userId) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        return userDeviceRepository.findAllByUserIdOrderByLastUpdated(userId);
    }
}
