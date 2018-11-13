package com.sweetitech.hrm.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "type_of_commission")
public class TypeOfCommission extends BaseEntity {

    @NotNull
    private String name;

    private boolean isDeleted = false;

    public TypeOfCommission() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
