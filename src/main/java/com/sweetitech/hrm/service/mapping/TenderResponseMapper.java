package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.Tender;
import com.sweetitech.hrm.domain.dto.TenderResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenderResponseMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    /**
     *
     * @param entity
     * @return dto
     */
    public TenderResponseDTO map(Tender entity) throws Exception {
        TenderResponseDTO dto = new TenderResponseDTO();

        dto.setTenderId(entity.getId());
        dto.setInstitutionName(entity.getInstitutionName());
        dto.setMrCode(entity.getMrCode());
        dto.setTypeOfTender(entity.getTypeOfTender());
        dto.seteTenderId(entity.geteTenderId());
        dto.setTenderLotNumber(entity.getTenderLotNumber());
        dto.setCostOfTenderSchedule(entity.getCostOfTenderSchedule());
        dto.setTotalTenderValue(entity.getTotalTenderValue());
        dto.setTypeOfTenderSecurity(entity.getTypeOfTenderSecurity());
        dto.setAmountOfTenderSecurity(entity.getAmountOfTenderSecurity());
        dto.setTenderSecurityStatus(entity.getTenderSecurityStatus());
        dto.setCopyOfTenderSecurityInFile(entity.isCopyOfTenderSecurityInFile());
        dto.setIssueDateOfTenderSecurity(entity.getIssueDateOfTenderSecurity());
        dto.setTenderSecurityReleaseDate(entity.getTenderSecurityReleaseDate());
        dto.setTypeOfPerformanceSecurity(entity.getTypeOfPerformanceSecurity());
        dto.setAmountOfPerformanceSecurity(entity.getAmountOfPerformanceSecurity());
        dto.setCopyOfPerformanceSecurityInFile(entity.isCopyOfPerformanceSecurityInFile());
        dto.setIssueDateOfPerformanceSecurity(entity.getIssueDateOfPerformanceSecurity());
        dto.setPerformanceSecurityReleaseDate(entity.getPerformanceSecurityReleaseDate());
        dto.setTenderStatus(entity.getTenderStatus());
        dto.setStatus(entity.getStatus());
        dto.setCreatedByUser(userSmallResponseMapper.map(entity.getCreatedBy()));

        return dto;
    }
}
