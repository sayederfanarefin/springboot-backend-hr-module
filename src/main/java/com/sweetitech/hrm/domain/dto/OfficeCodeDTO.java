package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfficeCodeDTO {

    private Long officeCodeId;

    private String code;

    private Long companyId;

    private boolean isDeleted;

    public Long getOfficeCodeId() {
        return officeCodeId;
    }

    public void setOfficeCodeId(Long officeCodeId) {
        this.officeCodeId = officeCodeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "OfficeCodeDTO{" +
                "officeCodeId=" + officeCodeId +
                ", code='" + code + '\'' +
                ", companyId=" + companyId +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
