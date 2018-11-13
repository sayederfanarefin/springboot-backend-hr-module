package com.sweetitech.hrm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "office_codes")
public class OfficeCode extends BaseEntity {

    @NotNull
    private String code;

    @NotNull
    @OneToOne
    private Company company;

    private boolean isDeleted = false;

    public OfficeCode() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "OfficeCode{" +
                "code='" + code + '\'' +
                ", company=" + company +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
