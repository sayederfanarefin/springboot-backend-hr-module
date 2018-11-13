package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {

    Privilege findByIdAndIsDeletedFalse(Long id);

    List<Privilege> findAllByIsDeletedFalse();

    Page<Privilege> findAllByIsDeletedFalse(Pageable pageable);

    Privilege getFirstByIdAndIsDeletedFalse(Long id);

}
