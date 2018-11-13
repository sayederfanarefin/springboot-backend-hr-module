package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.relation.SalaryBreakdownRelation;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalaryBreakdownCheckListDTO {

    private List<SalaryBreakdownCheckDTO> checkDTOS;

    private List<SalaryBreakdownRelation> relations;

    public List<SalaryBreakdownCheckDTO> getCheckDTOS() {
        return checkDTOS;
    }

    public void setCheckDTOS(List<SalaryBreakdownCheckDTO> checkDTOS) {
        this.checkDTOS = checkDTOS;
    }

    public List<SalaryBreakdownRelation> getRelations() {
        return relations;
    }

    public void setRelations(List<SalaryBreakdownRelation> relations) {
        this.relations = relations;
    }

    @Override
    public String toString() {
        return "SalaryBreakdownCheckListDTO{" +
                "checkDTOS=" + checkDTOS +
                ", relations=" + relations +
                '}';
    }
}
