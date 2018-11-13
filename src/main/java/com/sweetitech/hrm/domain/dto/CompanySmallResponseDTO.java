package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.Image;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanySmallResponseDTO {

    private Long companyId;

    private String companyName;

    private Image companyLogo;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Image getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(Image companyLogo) {
        this.companyLogo = companyLogo;
    }

    @Override
    public String toString() {
        return "CompanySmallResponseDTO{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", companyLogo=" + companyLogo +
                '}';
    }
}
