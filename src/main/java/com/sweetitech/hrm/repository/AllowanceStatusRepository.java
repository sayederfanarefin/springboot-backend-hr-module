package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.AllowanceStatus;
import org.springframework.data.repository.CrudRepository;

public interface AllowanceStatusRepository extends CrudRepository<AllowanceStatus, Long> {

    AllowanceStatus getFirstById(Long id);

}
