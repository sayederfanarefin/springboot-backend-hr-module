package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.Image;
import com.sweetitech.hrm.domain.dto.CompanyDTO;
import com.sweetitech.hrm.service.implementation.CompanyServiceImpl;
import com.sweetitech.hrm.service.implementation.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyMapper {

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private ImageServiceImpl imageService;

    /**
     * Maps DTO to Entity
     *
     * @param dto
     * @return entity
     */
    public Company map(CompanyDTO dto) throws Exception {
        Image logo = null;
        if (dto.getLogoId() != null)
            logo = imageService.findById(dto.getLogoId());

        Company company = new Company();

        company.setName(dto.getName());
        company.setPhone(dto.getPhone());
        company.setEmail(dto.getEmail());
        company.setAddress(dto.getAddress());
        company.setLogo(logo);

        return company;
    }

}
