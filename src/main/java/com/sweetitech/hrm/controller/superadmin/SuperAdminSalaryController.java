package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Salary;
import com.sweetitech.hrm.domain.dto.UserSalaryDTO;
import com.sweetitech.hrm.domain.relation.UserSalaryRelation;
import com.sweetitech.hrm.service.implementation.UserSalaryServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import com.sweetitech.hrm.service.mapping.UserSalaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/super-admin/salaries")
public class SuperAdminSalaryController {

    private UserSalaryServiceImpl userSalaryService;

    @Autowired
    private UserSalaryMapper userSalaryMapper;

    @Autowired
    public SuperAdminSalaryController(UserSalaryServiceImpl userSalaryService) {
        this.userSalaryService = userSalaryService;
    }

    // Add salary to user
    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserSalaryDTO addSalaryToUser(@RequestBody UserSalaryDTO dto) throws Exception {
        return userSalaryMapper.map(userSalaryService.create(dto.getUserId(), dto.getSalary(), dto.getMonth(), dto.getYear()));
    }

    // Get current salary
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public UserSalaryDTO getCurrentSalary(@PathVariable Long userId) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return userSalaryMapper.map(userSalaryService.readLatestByUserId(userId));
    }

    // Get all salaries for user
    @RequestMapping(value = "/all/{userId}", method = RequestMethod.GET)
    public Iterable<UserSalaryRelation> getAllSalariesOfUser(@PathVariable Long userId,
                                                             @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return userSalaryService.readAllByUserId(userId, page);
    }

}
