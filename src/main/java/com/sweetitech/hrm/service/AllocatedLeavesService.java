package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.AllocatedLeaves;

import java.util.List;

public interface AllocatedLeavesService {

    AllocatedLeaves read(Long id);

    AllocatedLeaves create(AllocatedLeaves allocatedLeaves);

    AllocatedLeaves update(Long id, AllocatedLeaves allocatedLeaves) throws Exception;

    List<AllocatedLeaves> readAll();

}
