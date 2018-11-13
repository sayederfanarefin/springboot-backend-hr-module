package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.TypeOfCommission;

import java.util.List;

public interface TypeOfCommissionService {

    TypeOfCommission create(TypeOfCommission typeOfCommission) throws Exception;

    TypeOfCommission update(Long id, TypeOfCommission typeOfCommission) throws Exception;

    void remove(Long id) throws Exception;

    TypeOfCommission read(Long id);

    List<TypeOfCommission> readAll();

    List<TypeOfCommission> searchByName(String name);

}
