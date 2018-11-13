package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RolePrivilegeRelationDTO {

    @NotNull
    private Long roleId;

    @NotNull
    private Long privilegeId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(Long privilegeId) {
        this.privilegeId = privilegeId;
    }

    @Override
    public String toString() {
        return "RolePrivilegeRelationDTO{" +
                "roleId=" + roleId +
                ", privilegeId=" + privilegeId +
                '}';
    }
}
