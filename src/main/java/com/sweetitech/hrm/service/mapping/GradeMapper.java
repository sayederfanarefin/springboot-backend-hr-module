package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.AllocatedLeaves;
import com.sweetitech.hrm.domain.Grade;
import com.sweetitech.hrm.domain.dto.GradeDTO;
import com.sweetitech.hrm.service.implementation.AllocatedLeavesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeMapper {

    @Autowired
    private AllocatedLeavesServiceImpl allocatedLeavesService;

    /**
     * Maps DTO to Entity
     *
     * @param dto
     * @return entity
     */
    public Grade map(GradeDTO dto) throws Exception {
        if (dto.getAllocatedLeaveId() == null) {
            throw new EntityNotFoundException("Null allocated leave id found!");
        }

        AllocatedLeaves allocatedLeaves = allocatedLeavesService.read(dto.getAllocatedLeaveId());
        if (allocatedLeaves == null) {
            throw new EntityNotFoundException("No allocated leave with id: " + dto.getAllocatedLeaveId());
        }

        Grade grade = new Grade();
        grade.setTitle(dto.getTitle());
        grade.setGradeNumber(dto.getGradeNumber());
        grade.setHqAllowance(dto.getHqAllowance());
        grade.setExHqAllowance(dto.getExHqAllowance());
        grade.setOsWHotelAllowance(dto.getOsWHotelAllowance());
        grade.setOsWoHotelAllowance(dto.getOsWoHotelAllowance());
        grade.setAllocatedLeaves(allocatedLeaves);

        return grade;
    }

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public GradeDTO map(Grade entity) throws Exception {
        GradeDTO dto = new GradeDTO();
        dto.setTitle(entity.getTitle());
        dto.setGradeNumber(entity.getGradeNumber());
        dto.setHqAllowance(entity.getHqAllowance());
        dto.setExHqAllowance(entity.getExHqAllowance());
        dto.setOsWHotelAllowance(entity.getOsWHotelAllowance());
        dto.setOsWoHotelAllowance(entity.getOsWoHotelAllowance());

        if (entity.getAllocatedLeaves() == null) {
            dto.setAllocatedLeaveId((long) -1);
        } else {
            dto.setAllocatedLeaveId(entity.getAllocatedLeaves().getId());
        }

        return dto;
    }

}
