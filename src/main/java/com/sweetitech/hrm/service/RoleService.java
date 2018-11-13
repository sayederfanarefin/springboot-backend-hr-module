package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Role;

public interface RoleService {

    Role findRoleById(long id);

    Role findRoleByName(String name);

}
