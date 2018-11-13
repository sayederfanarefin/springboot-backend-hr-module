package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.OfficeCode;
import com.sweetitech.hrm.domain.dto.OfficeCodeDTO;
import com.sweetitech.hrm.service.implementation.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfficeCodeMapper {

    @Autowired
    private CompanyServiceImpl companyService;

    /**
     * Maps DTO to Entity
     *
     * @param dto
     * @return entity
     */
    public OfficeCode map(OfficeCodeDTO dto) throws Exception {
        if (dto.getCompanyId() == null || dto.getCode() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        Company company = companyService.read(dto.getCompanyId());
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + dto.getCompanyId());
        }

        OfficeCode entity = new OfficeCode();

        entity.setCode(dto.getCode());
        entity.setCompany(company);

        return entity;
    }

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public OfficeCodeDTO map(OfficeCode entity) throws Exception {
        OfficeCodeDTO dto = new OfficeCodeDTO();

        dto.setOfficeCodeId(entity.getId());
        dto.setCode(entity.getCode());
        dto.setCompanyId(entity.getCompany().getId());
        dto.setDeleted(entity.isDeleted());

        return dto;
    }

}
