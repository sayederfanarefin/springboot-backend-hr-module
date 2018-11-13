package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.AllocatedLeaves;
import com.sweetitech.hrm.repository.AllocatedLeavesRepository;
import com.sweetitech.hrm.service.AllocatedLeavesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AllocatedLeavesServiceImpl implements AllocatedLeavesService {

    private AllocatedLeavesRepository allocatedLeavesRepository;

    @Autowired
    public AllocatedLeavesServiceImpl(AllocatedLeavesRepository allocatedLeavesRepository) {
        this.allocatedLeavesRepository = allocatedLeavesRepository;
    }

    @Override
    public AllocatedLeaves read(Long id) {
        return allocatedLeavesRepository.getFirstById(id);
    }

    @Override
    public AllocatedLeaves create(AllocatedLeaves allocatedLeaves) {
        return allocatedLeavesRepository.save(allocatedLeaves);
    }

    @Override
    public AllocatedLeaves update(Long id, AllocatedLeaves allocatedLeaves) throws Exception {
        AllocatedLeaves allocatedLeavesOld = this.read(id);
        if (allocatedLeavesOld == null) {
            throw new EntityNotFoundException("No allocated leave with id: " + id);
        }

        allocatedLeaves.setId(id);
        return allocatedLeavesRepository.save(allocatedLeaves);
    }

    @Override
    public List<AllocatedLeaves> readAll() {
        return allocatedLeavesRepository.findAllByOrderByTypeOfEmployeeAsc();
    }
}
