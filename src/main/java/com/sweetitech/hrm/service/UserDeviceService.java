package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.relation.UserDeviceRelation;

import java.util.List;

public interface UserDeviceService {

    UserDeviceRelation read(Long id) throws Exception;

    UserDeviceRelation create(UserDeviceRelation relation) throws Exception;

    UserDeviceRelation update(Long userId, Long locationId, UserDeviceRelation relation) throws Exception;

    void remove(Long id) throws Exception;

    UserDeviceRelation exists(Long userId, Long locationId) throws Exception;

    List<UserDeviceRelation> readAllByUserId(Long userId) throws Exception;

}
