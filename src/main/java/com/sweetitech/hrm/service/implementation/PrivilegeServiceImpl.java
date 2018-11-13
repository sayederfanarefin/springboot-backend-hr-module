package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Privilege;
import com.sweetitech.hrm.repository.PrivilegeRepository;
import com.sweetitech.hrm.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    private PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }


    @Override
    public Privilege create(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    @Override
    public Privilege update(Long id, Privilege privilege) throws Exception {
        Privilege privilegeOld = privilegeRepository.findByIdAndIsDeletedFalse(id);
        if (privilegeOld == null) {
            throw new EntityNotFoundException("No privilege with id: " + id);
        }

        privilege.setId(id);

        return privilegeRepository.save(privilege);
    }

    @Override
    public Privilege read(Long id) {
        return privilegeRepository.findByIdAndIsDeletedFalse(id);
    }

    @Override
    public List<Privilege> readAll() {
        return privilegeRepository.findAllByIsDeletedFalse();
    }

    @Override
    public Page<Privilege> readAll(int page) {
        return privilegeRepository.findAllByIsDeletedFalse(new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public void remove(Long id) throws Exception {
        Privilege privilege = privilegeRepository.findByIdAndIsDeletedFalse(id);
        if (privilege == null) {
            throw new EntityNotFoundException("No privilege with id: " + id);
        }

        privilege.setDeleted(true);
        privilegeRepository.save(privilege);
    }
}
