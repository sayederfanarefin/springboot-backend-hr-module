package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.relation.SalaryBreakdownRelation;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalaryBreakdownListDTO {

    private List<SalaryBreakdownRelationDTO> relations;

    public List<SalaryBreakdownRelationDTO> getRelations() {
        return relations;
    }

    public void setRelations(List<SalaryBreakdownRelationDTO> relations) {
        this.relations = relations;
    }

    @Override
    public String toString() {
        return "SalaryBreakdownListDTO{" +
                "relations=" + relations +
                '}';
    }
}
