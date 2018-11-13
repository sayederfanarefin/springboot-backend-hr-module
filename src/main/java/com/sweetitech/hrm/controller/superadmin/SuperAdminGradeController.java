package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Grade;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.GradeDTO;
import com.sweetitech.hrm.service.implementation.GradeServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import com.sweetitech.hrm.service.mapping.GradeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/super-admin/grades")
public class SuperAdminGradeController {

    private GradeServiceImpl gradeService;

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    public SuperAdminGradeController(GradeServiceImpl gradeService) {
        this.gradeService = gradeService;
    }

    // Create Grade
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Grade createGrade(@RequestBody GradeDTO dto) throws Exception {
        return gradeService.create(gradeMapper.map(dto));
    }

    // Edit Grade
    @RequestMapping(value = "/{gradeId}", method = RequestMethod.PUT)
    public Grade updateGrade(@PathVariable Long gradeId, @RequestBody GradeDTO dto) throws Exception {
        if (gradeId == null) {
            throw new EntityNotFoundException("Null grade id found!");
        }

        return gradeService.update(gradeId, gradeMapper.map(dto));
    }

    // Soft Delete Grade
    @RequestMapping(value = "/{gradeId}", method = RequestMethod.DELETE)
    public void deleteGrade(@PathVariable Long gradeId) throws Exception {
        if (gradeId == null) {
            throw new EntityNotFoundException("Null grade id found!");
        }

        gradeService.remove(gradeId);
    }

    // Get DTO by Id
    @RequestMapping(value = "/dto/{gradeId}", method = RequestMethod.GET)
    public GradeDTO getDTO(@PathVariable Long gradeId) throws Exception {
        if (gradeId == null) {
            throw new EntityNotFoundException("Null grade id found!");
        }

        return gradeMapper.map(gradeService.read(gradeId));
    }

    // Get Employees Against Grade
    @RequestMapping(value = "/users/{gradeId}", method = RequestMethod.GET)
    public Iterable<User> getEmployees(@PathVariable Long gradeId,
                                       @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (gradeId == null) {
            throw new EntityNotFoundException("Null grade id found!");
        }

        return userService.readAllByGrade(gradeId, page);
    }

}
