package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.UserSalaryDTO;
import com.sweetitech.hrm.service.implementation.UserSalaryServiceImpl;
import com.sweetitech.hrm.service.mapping.UserSalaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/employee/salaries")
public class EmployeeSalaryController {

    private UserSalaryServiceImpl userSalaryService;

    @Autowired
    private UserSalaryMapper userSalaryMapper;

    @Autowired
    public EmployeeSalaryController(UserSalaryServiceImpl userSalaryService) {
        this.userSalaryService = userSalaryService;
    }

    // Get current salary
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public UserSalaryDTO getCurrentSalary(@PathVariable Long userId) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return userSalaryMapper.map(userSalaryService.readLatestByUserId(userId));
    }

}
