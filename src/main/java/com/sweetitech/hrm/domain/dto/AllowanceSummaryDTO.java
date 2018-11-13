package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.domain.Grade;
import com.sweetitech.hrm.domain.User;
import io.swagger.annotations.ApiModel;

import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllowanceSummaryDTO {

    private Long summaryId;

    @NotNull
    private Integer month;

    @NotNull
    private Integer year;

    private String territory;

    @NotNull
    private Double total = (double) 0;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Dhaka")
    private Date confirmDate = new Date();

    @NotNull
    private Long preparedForUserId;

    private Grade forGrade;

    @NotNull
    private Long confirmedByUserId;

    public Long getSummaryId() {
        return summaryId;
    }

    public void setSummaryId(Long summaryId) {
        this.summaryId = summaryId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Long getPreparedForUserId() {
        return preparedForUserId;
    }

    public void setPreparedForUserId(Long preparedForUserId) {
        this.preparedForUserId = preparedForUserId;
    }

    public Grade getForGrade() {
        return forGrade;
    }

    public void setForGrade(Grade forGrade) {
        this.forGrade = forGrade;
    }

    public Long getConfirmedByUserId() {
        return confirmedByUserId;
    }

    public void setConfirmedByUserId(Long confirmedByUserId) {
        this.confirmedByUserId = confirmedByUserId;
    }

    @Override
    public String toString() {
        return "AllowanceSummaryDTO{" +
                "summaryId=" + summaryId +
                ", month=" + month +
                ", year=" + year +
                ", territory='" + territory + '\'' +
                ", total=" + total +
                ", confirmDate=" + confirmDate +
                ", preparedForUserId=" + preparedForUserId +
                ", forGrade=" + forGrade +
                ", confirmedByUserId=" + confirmedByUserId +
                '}';
    }
}
