package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveMonthlySummaryListDTO {

    private UserSmallResponseDTO user;

    private Integer leaves = 0;

    public LeaveMonthlySummaryListDTO(UserSmallResponseDTO user, Integer leaves) {
        this.user = user;
        this.leaves = leaves;
    }

    public UserSmallResponseDTO getUser() {
        return user;
    }

    public void setUser(UserSmallResponseDTO user) {
        this.user = user;
    }

    public Integer getLeaves() {
        return leaves;
    }

    public void setLeaves(Integer leaves) {
        this.leaves = leaves;
    }

    @Override
    public String toString() {
        return "LeaveMonthlySummaryListDTO{" +
                "user=" + user +
                ", leaves=" + leaves +
                '}';
    }
}
