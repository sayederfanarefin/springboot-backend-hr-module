package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Salary;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.relation.UserSalaryRelation;
import com.sweetitech.hrm.repository.UserSalaryRepository;
import com.sweetitech.hrm.service.UserSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserSalaryServiceImpl implements UserSalaryService {

    private UserSalaryRepository userSalaryRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SalaryServiceImpl salaryService;

    @Autowired
    public UserSalaryServiceImpl(UserSalaryRepository userSalaryRepository) {
        this.userSalaryRepository = userSalaryRepository;
    }

    @Override
    public UserSalaryRelation create(Long userId, Salary salary, Integer month, Integer year) {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (salary == null || month == null || year == null) {
            throw new EntityNotFoundException("Null object found!");
        }

        Salary salaryCreated = salaryService.create(salary);

        UserSalaryRelation relation = new UserSalaryRelation();
        relation.setMonth(month);
        relation.setYear(year);
        relation.setUser(user);
        relation.setSalary(salaryCreated);

        return userSalaryRepository.save(relation);
    }

    @Override
    public UserSalaryRelation readLatestByUserId(Long id) throws Exception {
        User user = userService.read(id);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + id);
        }

        return userSalaryRepository.findTopByUserIdOrderByLastUpdatedDesc(id);
    }

    @Override
    public Page<UserSalaryRelation> readAllByUserId(Long id, Integer page) throws Exception {
        User user = userService.read(id);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + id);
        }

        return userSalaryRepository.findAllByUserIdOrderByLastUpdatedDesc(id, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public UserSalaryRelation read(Long id) throws Exception {
        return userSalaryRepository.getFirstById(id);
    }
}
