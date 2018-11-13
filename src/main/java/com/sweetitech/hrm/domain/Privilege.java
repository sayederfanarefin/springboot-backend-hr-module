package com.sweetitech.hrm.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "privileges")
public class Privilege extends BaseEntity {

    @NotNull
    private String module;

    @NotNull
    private String feature;

    private boolean isDeleted = false;

    public Privilege() {
    }

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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "module='" + module + '\'' +
                ", feature='" + feature + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
