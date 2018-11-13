package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.domain.Role;
import com.sweetitech.hrm.repository.RoleRepository;
import com.sweetitech.hrm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleById(long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}