package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrivilegeDTO {

    @NotNull
    private String module;

    @NotNull
    private String feature;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    @Override
    public String toString() {
        return "PrivilegeDTO{" +
                "module='" + module + '\'' +
                ", feature='" + feature + '\'' +
                '}';
    }
}
