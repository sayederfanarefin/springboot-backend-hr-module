package com.sweetitech.hrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "allowance_summary")
public class AllowanceSummary extends BaseEntity {

    @NotNull
    private Integer month;

    @NotNull
    private Integer year;

    private String territory;

    @NotNull
    private Double total = (double) 0;

    @NotNull
    private Date confirmDate = new Date();

    @OneToOne
    private Grade forGrade;

    @NotNull
    @OneToOne
    private User preparedFor;

    @NotNull
    @OneToOne
    private User confirmedBy;

    @JsonIgnore
    @OneToMany(mappedBy = "allowanceSummary")
    private List<Allowance> allowances;

    public AllowanceSummary() {
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

    public Grade getForGrade() {
        return forGrade;
    }

    public void setForGrade(Grade forGrade) {
        this.forGrade = forGrade;
    }

    public User getPreparedFor() {
        return preparedFor;
    }

    public void setPreparedFor(User preparedFor) {
        this.preparedFor = preparedFor;
    }

    public User getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(User confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public List<Allowance> getAllowances() {
        return allowances;
    }

    public void setAllowances(List<Allowance> allowances) {
        this.allowances = allowances;
    }

    @Override
    public String toString() {
        return "AllowanceSummary{" +
                "month=" + month +
                ", year=" + year +
                ", territory='" + territory + '\'' +
                ", total=" + total +
                ", confirmDate=" + confirmDate +
                ", forGrade=" + forGrade +
                ", preparedFor=" + preparedFor +
                ", confirmedBy=" + confirmedBy +
                ", allowances=" + allowances +
                '}';
    }
}
