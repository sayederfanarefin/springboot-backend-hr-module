package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Grade;
import com.sweetitech.hrm.service.implementation.GradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/grades")
public class AdminGradeController {

    private GradeServiceImpl gradeService;

    @Autowired
    public AdminGradeController(GradeServiceImpl gradeService) {
        this.gradeService = gradeService;
    }

    // Get All Grades
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Grade> getAllGrades() throws Exception {
        return gradeService.readAll();
    }

    // Get Grade By Id
    @RequestMapping(value = "/{gradeId}", method = RequestMethod.GET)
    public Grade getById(@PathVariable Long gradeId) throws Exception {
        if (gradeId == null) {
            throw new EntityNotFoundException("Null grade id found!");
        }

        return gradeService.read(gradeId);
    }
}
