package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.ConstantsUtil;
import com.sweetitech.hrm.domain.Tender;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.TenderDTO;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenderMapper {

    @Autowired
    private UserServiceImpl userService;

    /**
     *
     * @param dto
     * @return entity
     */
    public Tender map(TenderDTO dto) throws Exception {
        User user = userService.read(dto.getCreatedByUserId());
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + dto.getCreatedByUserId());
        }

        if (dto.getInstitutionName() == null) {
            dto.setInstitutionName("");
        }

        if (dto.getTypeOfTender() == null) {
            throw new EntityNotFoundException("No type of tender found!");
        }

        Tender entity = new Tender();

        entity.setInstitutionName(dto.getInstitutionName());
        entity.setMrCode(dto.getMrCode());

        if (!ConstantsUtil.isValidTypeOfTender(dto.getTypeOfTender()))
            throw new EntityNotFoundException("Invalid type of tender provided!");

        entity.setTypeOfTender(dto.getTypeOfTender());

        if (dto.geteTenderId() != null) entity.seteTenderId(dto.geteTenderId());
        if (dto.getTenderLotNumber() != null) entity.setTenderLotNumber(dto.getTenderLotNumber());

        entity.setCostOfTenderSchedule(dto.getCostOfTenderSchedule());

        if (dto.getTotalTenderValue() != null) entity.setTotalTenderValue(dto.getTotalTenderValue());
        if (dto.getTypeOfTenderSecurity() != null) entity.setTypeOfTenderSecurity(dto.getTypeOfTenderSecurity());
        if (dto.getAmountOfTenderSecurity() != null) entity.setAmountOfTenderSecurity(dto.getAmountOfTenderSecurity());

        if (dto.getTenderSecurityStatus() != null) {
            if (dto.getTenderSecurityStatus().isEmpty()) {
                entity.setTenderSecurityStatus(null);
            } else {
                if (!ConstantsUtil.isValidAmountOfTenderSecurityStatus(dto.getTenderSecurityStatus())) {
                    throw new EntityNotFoundException("Invalid tender security status");
                }

                entity.setTenderSecurityStatus(dto.getTenderSecurityStatus());
            }
        }

        entity.setCopyOfTenderSecurityInFile(dto.isCopyOfTenderSecurityInFile());

        if (dto.getIssueDateOfTenderSecurity() != null) entity.setIssueDateOfTenderSecurity(dto.getIssueDateOfTenderSecurity());
        if (dto.getTenderSecurityReleaseDate() != null) entity.setTenderSecurityReleaseDate(dto.getTenderSecurityReleaseDate());
        if (dto.getTypeOfPerformanceSecurity() != null) entity.setTypeOfPerformanceSecurity(dto.getTypeOfPerformanceSecurity());
        if (dto.getAmountOfPerformanceSecurity() != null) entity.setAmountOfPerformanceSecurity(dto.getAmountOfPerformanceSecurity());

        entity.setCopyOfPerformanceSecurityInFile(dto.isCopyOfPerformanceSecurityInFile());

        if (dto.getIssueDateOfPerformanceSecurity() != null) entity.setIssueDateOfPerformanceSecurity(dto.getIssueDateOfPerformanceSecurity());
        if (dto.getPerformanceSecurityReleaseDate() != null) entity.setPerformanceSecurityReleaseDate(dto.getPerformanceSecurityReleaseDate());
        if (dto.getTenderStatus() != null) entity.setTenderStatus(dto.getTenderStatus());

        entity.setStatus(dto.getStatus());

        entity.setCreatedBy(user);

        return entity;
    }

}
