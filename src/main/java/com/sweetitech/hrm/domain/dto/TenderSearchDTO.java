package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TenderSearchDTO {

    private String institutionName;

    private String eTenderId;

    private String mrCode;

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String geteTenderId() {
        return eTenderId;
    }

    public void seteTenderId(String eTenderId) {
        this.eTenderId = eTenderId;
    }

    public String getMrCode() {
        return mrCode;
    }

    public void setMrCode(String mrCode) {
        this.mrCode = mrCode;
    }

    @Override
    public String toString() {
        return "TenderSearchDTO{" +
                "institutionName='" + institutionName + '\'' +
                ", eTenderId='" + eTenderId + '\'' +
                ", mrCode='" + mrCode + '\'' +
                '}';
    }
}
