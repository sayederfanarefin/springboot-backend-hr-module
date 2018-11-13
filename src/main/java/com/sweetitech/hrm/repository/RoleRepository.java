package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findById(long id);

    Role findByName(String name);

}
