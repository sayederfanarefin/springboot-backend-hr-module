package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.relation.UserDeviceRelation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDeviceRepository extends CrudRepository<UserDeviceRelation, Long> {

    UserDeviceRelation getFirstById(Long id);

    List<UserDeviceRelation> findAllByUserIdOrderByLastUpdated(Long userId);

    UserDeviceRelation getFirstByUserIdAndLocationId(Long userId, Long locationId);

    void deleteById(long id);

    UserDeviceRelation getFirstByLocationDeviceDeviceIdAndEnrollmentNumber(String deviceId, String enrolNumber);

}
