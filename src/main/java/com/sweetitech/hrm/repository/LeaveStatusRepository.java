package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.LeaveStatus;
import org.springframework.data.repository.CrudRepository;

public interface LeaveStatusRepository extends CrudRepository<LeaveStatus, Long> {

    LeaveStatus getFirstById(Long id);

}
