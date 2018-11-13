package com.sweetitech.hrm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "grades")
public class Grade extends BaseEntity {

    @NotNull
    @Size(min = 2, max = 100, message = "Grade title must be between 2 to 100 characters!")
    @Column(nullable = false, unique = true)
    private String title;

    @NotNull
    private Integer gradeNumber;

    @NotNull
    private Double hqAllowance;

    @NotNull
    private Double exHqAllowance;

    @NotNull
    private Double osWHotelAllowance;

    @NotNull
    private Double osWoHotelAllowance;

    @OneToOne
    private AllocatedLeaves allocatedLeaves;

    // TODO remove after error testing
    private Double salary;

    private Double bonus;

    private boolean isDeleted = false;

    public Grade() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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

    public AllocatedLeaves getAllocatedLeaves() {
        return allocatedLeaves;
    }

    public void setAllocatedLeaves(AllocatedLeaves allocatedLeaves) {
        this.allocatedLeaves = allocatedLeaves;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "title='" + title + '\'' +
                ", gradeNumber=" + gradeNumber +
                ", hqAllowance=" + hqAllowance +
                ", exHqAllowance=" + exHqAllowance +
                ", osWHotelAllowance=" + osWHotelAllowance +
                ", osWoHotelAllowance=" + osWoHotelAllowance +
                ", allocatedLeaves=" + allocatedLeaves +
                ", salary=" + salary +
                ", bonus=" + bonus +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
