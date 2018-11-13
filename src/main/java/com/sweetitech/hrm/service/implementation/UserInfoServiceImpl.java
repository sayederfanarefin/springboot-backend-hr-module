package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.domain.UserInfo;
import com.sweetitech.hrm.repository.UserInfoRepository;
import com.sweetitech.hrm.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserInfo read(Long id) throws Exception {
        return userInfoRepository.getFirstById(id);
    }

    @Override
    public UserInfo readByEnrollAndMachineNumbers(String enrollNumber, int machineNumber) {
        return userInfoRepository.getFirstByEnrollNumberAndMachineNumber(enrollNumber, machineNumber);
    }
}
