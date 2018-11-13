package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.UserInfo;

public interface UserInfoService {

    UserInfo read(Long id) throws Exception;

    UserInfo readByEnrollAndMachineNumbers(String enrollNumber, int machineNumber);

}
