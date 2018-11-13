package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GradeDTO {

    @NotNull
    @Size(min = 2, max = 100, message = "Grade title must be between 2 to 100 characters!")
    @Column(nullable = false, unique = true)
    private String title;

    @NotNull
    private Integer gradeNumber;

    private Double hqAllowance;

    private Double exHqAllowance;

    private Double osWHotelAllowance;

    private Double osWoHotelAllowance;

    @NotNull
    private Long allocatedLeaveId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getGradeNumber() {
        return gradeNumber;
    }

    public void setGradeNumber(Integer gradeNumber) {
        this.gradeNumber = gradeNumber;
    }

    public Double getHqAllowance() {
        return hqAllowance;
    }

    public void setHqAllowance(Double hqAllowance) {
        this.hqAllowance = hqAllowance;
    }

    public Double getExHqAllowance() {
        return exHqAllowance;
    }

    public void setExHqAllowance(Double exHqAllowance) {
        this.exHqAllowance = exHqAllowance;
    }

    public Double getOsWHotelAllowance() {
        return osWHotelAllowance;
    }

    public void setOsWHotelAllowance(Double osWHotelAllowance) {
        this.osWHotelAllowance = osWHotelAllowance;
    }

    public Double getOsWoHotelAllowance() {
        return osWoHotelAllowance;
    }

    public void setOsWoHotelAllowance(Double osWoHotelAllowance) {
        this.osWoHotelAllowance = osWoHotelAllowance;
    }

    public Long getAllocatedLeaveId() {
        return allocatedLeaveId;
    }

    public void setAllocatedLeaveId(Long allocatedLeaveId) {
        this.allocatedLeaveId = allocatedLeaveId;
    }

    @Override
    public String toString() {
        return "GradeDTO{" +
                "title='" + title + '\'' +
                ", gradeNumber=" + gradeNumber +
                ", hqAllowance=" + hqAllowance +
                ", exHqAllowance=" + exHqAllowance +
                ", osWHotelAllowance=" + osWHotelAllowance +
                ", osWoHotelAllowance=" + osWoHotelAllowance +
                ", allocatedLeaveId=" + allocatedLeaveId +
                '}';
    }
}
