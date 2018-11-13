package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.relation.SalaryBreakdownRelation;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SalaryBreakdownDTO {

    @NotNull
    private Long salaryId;

    private List<SalaryBreakdownRelation> relations;

    public Long getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Long salaryId) {
        this.salaryId = salaryId;
    }

    public List<SalaryBreakdownRelation> getRelations() {
        return relations;
    }

    public void setRelations(List<SalaryBreakdownRelation> relations) {
        this.relations = relations;
    }

    @Override
    public String toString() {
        return "SalaryBreakdownDTO{" +
                "salaryId=" + salaryId +
                ", relations=" + relations +
                '}';
    }
}
