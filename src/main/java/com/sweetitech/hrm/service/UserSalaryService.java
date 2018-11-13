package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Salary;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.relation.UserSalaryRelation;
import org.springframework.data.domain.Page;

public interface UserSalaryService {

    UserSalaryRelation create(Long userId, Salary salary, Integer month, Integer year);

    UserSalaryRelation readLatestByUserId(Long id) throws Exception;

    Page<UserSalaryRelation> readAllByUserId(Long id, Integer page) throws Exception;

    UserSalaryRelation read(Long id) throws Exception;

}
