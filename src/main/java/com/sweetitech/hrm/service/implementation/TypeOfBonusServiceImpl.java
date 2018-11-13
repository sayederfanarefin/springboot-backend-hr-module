package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.TypeOfBonus;
import com.sweetitech.hrm.repository.TypeOfBonusRepository;
import com.sweetitech.hrm.service.TypeOfBonusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeOfBonusServiceImpl implements TypeOfBonusService {

    private TypeOfBonusRepository typeOfBonusRepository;

    @Autowired
    public TypeOfBonusServiceImpl(TypeOfBonusRepository typeOfBonusRepository) {
        this.typeOfBonusRepository = typeOfBonusRepository;
    }

    @Override
    public TypeOfBonus create(TypeOfBonus typeOfBonus) throws Exception {
        return typeOfBonusRepository.save(typeOfBonus);
    }

    @Override
    public TypeOfBonus update(Long id, TypeOfBonus typeOfBonus) throws Exception {
        if (typeOfBonusRepository.getFirstById(id) == null) {
            throw new EntityNotFoundException("No types with id: " + id);
        }

        typeOfBonus.setId(id);
        return typeOfBonusRepository.save(typeOfBonus);
    }

    @Override
    public void remove(Long id) throws Exception {
        TypeOfBonus type = typeOfBonusRepository.getFirstById(id);
        if (type == null) {
            throw new EntityNotFoundException("No types with id: " + id);
        }

        type.setDeleted(true);
        typeOfBonusRepository.save(type);
    }

    @Override
    public TypeOfBonus read(Long id) {
        return typeOfBonusRepository.getFirstById(id);
    }

    @Override
    public List<TypeOfBonus> readAll() {
        return typeOfBonusRepository.findAllByIsDeletedFalseOrderByNameAsc();
    }

    @Override
    public List<TypeOfBonus> searchByName(String name) {
        return typeOfBonusRepository.searchByName(name);
    }

}
