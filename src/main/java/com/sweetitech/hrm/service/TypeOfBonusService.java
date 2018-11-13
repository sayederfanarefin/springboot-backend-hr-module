package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.TypeOfBonus;

import java.util.List;

public interface TypeOfBonusService {

    TypeOfBonus create(TypeOfBonus typeOfBonus) throws Exception;

    TypeOfBonus update(Long id, TypeOfBonus typeOfBonus) throws Exception;

    void remove(Long id) throws Exception;

    TypeOfBonus read(Long id);

    List<TypeOfBonus> readAll();

    List<TypeOfBonus> searchByName(String name);

}
