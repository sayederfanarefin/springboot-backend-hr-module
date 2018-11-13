package com.sweetitech.hrm.domain.relation;

import com.sweetitech.hrm.domain.BaseEntity;
import com.sweetitech.hrm.domain.Privilege;
import com.sweetitech.hrm.domain.Role;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role_privilege_relations")
public class RolePrivilegeRelation extends BaseEntity {

    @NotNull
    @OneToOne
    private Role role;

    @NotNull
    @OneToOne
    private Privilege privilege;

    private boolean isDeleted = false;

    public RolePrivilegeRelation() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "RolePrivilegeRelation{" +
                "role=" + role +
                ", privilege=" + privilege +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
