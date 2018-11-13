package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.Car;
import com.sweetitech.hrm.domain.dto.CarResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarResponseMapper {

    @Autowired
    private CompanySmallResponseMapper companySmallResponseMapper;

    /**
     *
     * @param entity
     * @return dto
     */
    public CarResponseDTO map(Car entity) throws Exception {
        CarResponseDTO dto = new CarResponseDTO();

        dto.setCarId(entity.getId());
        dto.setRegNo(entity.getRegNo());
        dto.setTaxTokenNo(entity.getTaxTokenNo());
        dto.setInsuranceNo(entity.getInsuranceNo());
        dto.setFitnessTokenNo(entity.getFitnessTokenNo());
        dto.setLastRenewalDate(entity.getLastRenewalDate());
        dto.setModel(entity.getModel());
        dto.setCompany(companySmallResponseMapper.map(entity.getCompany()));
        dto.setCapacity(entity.getCapacity());
        dto.setMaintenance(entity.isMaintenance());
        dto.setDeleted(entity.isDeleted());

        return dto;
    }

}
