package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Car;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.dto.CarDTO;
import com.sweetitech.hrm.service.implementation.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarMapper {

    @Autowired
    private CompanyServiceImpl companyService;

    /**
     *
     * @param dto
     * @return entity
     */
    public Car map(CarDTO dto) throws Exception {
        if (dto.getCompanyId() == null || dto.getModel() == null || dto.getRegNo() == null) {
            throw new EntityNotFoundException("Mandatory values missing!");
        }

        Company company = companyService.read(dto.getCompanyId());
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + dto.getCompanyId());
        }

        Car entity = new Car();

        entity.setRegNo(dto.getRegNo());

        if (dto.getTaxTokenNo() != null)
            entity.setTaxTokenNo(dto.getTaxTokenNo());
        if (dto.getInsuranceNo() != null)
            entity.setInsuranceNo(dto.getInsuranceNo());
        if (dto.getFitnessTokenNo() != null)
            entity.setFitnessTokenNo(dto.getFitnessTokenNo());
        if (dto.getLastRenewalDate() != null)
            entity.setLastRenewalDate(dto.getLastRenewalDate());
        if (dto.getCapacity() != null)
            entity.setCapacity(dto.getCapacity());

        entity.setCompany(company);
        entity.setModel(dto.getModel());

        return entity;
    }

}
