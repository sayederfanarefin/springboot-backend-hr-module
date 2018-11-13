package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.dto.CompanySmallResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class CompanySmallResponseMapper {

    /**
     *
     * @param entity
     * @return dto
     */
    public CompanySmallResponseDTO map(Company entity) throws Exception {
        CompanySmallResponseDTO dto = new CompanySmallResponseDTO();

        dto.setCompanyId(entity.getId());
        dto.setCompanyName(entity.getName());
        dto.setCompanyLogo(entity.getLogo());

        return dto;
    }

}
