package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.Size;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDTO {

    @Size(min = 2, max = 100, message = "Company name must be between 2 to 100 characters!")
    private String name;

    @Size(min = 5)
    private String address;

    private String phone;

    private String email;

    private Long logoId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLogoId() {
        return logoId;
    }

    public void setLogoId(Long logoId) {
        this.logoId = logoId;
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", logoId=" + logoId +
                '}';
    }
}
