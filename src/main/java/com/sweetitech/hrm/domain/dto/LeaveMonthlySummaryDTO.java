package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeaveMonthlySummaryDTO {

    private UserSmallResponseDTO user;

    private List<LeaveSummaryDTO> leaves;

    public UserSmallResponseDTO getUser() {
        return user;
    }

    public void setUser(UserSmallResponseDTO user) {
        this.user = user;
    }

    public List<LeaveSummaryDTO> getLeaves() {
        return leaves;
    }

    public void setLeaves(List<LeaveSummaryDTO> leaves) {
        this.leaves = leaves;
    }

    @Override
    public String toString() {
        return "LeaveMonthlySummaryDTO{" +
                "user=" + user +
                ", leaves=" + leaves +
                '}';
    }
}
