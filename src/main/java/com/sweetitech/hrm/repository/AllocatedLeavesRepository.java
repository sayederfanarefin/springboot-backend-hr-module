package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.AllocatedLeaves;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AllocatedLeavesRepository extends CrudRepository<AllocatedLeaves, Long> {

    AllocatedLeaves getFirstById(Long id);

    List<AllocatedLeaves> findAllByOrderByTypeOfEmployeeAsc();

}
