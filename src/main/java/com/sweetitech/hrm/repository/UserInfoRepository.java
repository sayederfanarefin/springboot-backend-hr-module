package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

    UserInfo getFirstById(Long id);

    UserInfo getFirstByEnrollNumberAndMachineNumber(String enrollNumber, int machineNumber);

}
