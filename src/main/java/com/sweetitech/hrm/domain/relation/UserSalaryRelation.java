package com.sweetitech.hrm.domain.relation;

import com.sweetitech.hrm.domain.BaseEntity;
import com.sweetitech.hrm.domain.Salary;
import com.sweetitech.hrm.domain.User;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_salary_relations")
public class UserSalaryRelation extends BaseEntity {

    @NotNull
    private Integer month;

    @NotNull
    private Integer year;

    @NotNull
    @OneToOne
    private User user;

    @NotNull
    @OneToOne
    private Salary salary;

    public UserSalaryRelation() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "UserSalaryRelation{" +
                "month=" + month +
                ", year=" + year +
                ", user=" + user +
                ", salary=" + salary +
                '}';
    }
}
