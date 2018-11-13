package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.TypeOfCommission;
import com.sweetitech.hrm.repository.TypeOfCommissionRepository;
import com.sweetitech.hrm.service.TypeOfCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeOfCommissionServiceImpl implements TypeOfCommissionService {

    private TypeOfCommissionRepository typeOfCommissionRepository;

    @Autowired
    public TypeOfCommissionServiceImpl(TypeOfCommissionRepository typeOfCommissionRepository) {
        this.typeOfCommissionRepository = typeOfCommissionRepository;
    }

    @Override
    public TypeOfCommission create(TypeOfCommission typeOfCommission) throws Exception {
        return typeOfCommissionRepository.save(typeOfCommission);
    }

    @Override
    public TypeOfCommission update(Long id, TypeOfCommission typeOfCommission) throws Exception {
        if (typeOfCommissionRepository.getFirstById(id) == null) {
            throw new EntityNotFoundException("No types with id: " + id);
        }

        typeOfCommission.setId(id);
        return typeOfCommissionRepository.save(typeOfCommission);
    }

    @Override
    public void remove(Long id) throws Exception {
        TypeOfCommission type = typeOfCommissionRepository.getFirstById(id);
        if (type == null) {
            throw new EntityNotFoundException("No types with id: " + id);
        }

        type.setDeleted(true);
        typeOfCommissionRepository.save(type);
    }

    @Override
    public TypeOfCommission read(Long id) {
        return typeOfCommissionRepository.getFirstById(id);
    }

    @Override
    public List<TypeOfCommission> readAll() {
        return typeOfCommissionRepository.findAllByIsDeletedFalseOrderByNameAsc();
    }

    @Override
    public List<TypeOfCommission> searchByName(String name) {
        return typeOfCommissionRepository.searchByName(name);
    }
}
