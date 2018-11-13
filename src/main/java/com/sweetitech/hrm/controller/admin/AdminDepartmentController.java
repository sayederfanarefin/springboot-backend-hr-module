package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Department;
import com.sweetitech.hrm.domain.dto.DepartmentDTO;
import com.sweetitech.hrm.service.implementation.DepartmentServiceImpl;
import com.sweetitech.hrm.service.mapping.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/departments")
public class AdminDepartmentController {

    private DepartmentServiceImpl departmentService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    public AdminDepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    // Get All Departments by Company Id
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public List<Department> getDepartmentsByCompanyId(@PathVariable long companyId) {
        return departmentService.readAllByCompanyId(companyId);
    }

    // Get Department by Id
    @RequestMapping(value = "/{departmentId}", method = RequestMethod.GET)
    public Department getDepartmentById(@PathVariable Long departmentId) throws Exception {
        if (departmentId == null) {
            throw new EntityNotFoundException("Null department id found!");
        }

        return departmentService.read(departmentId);
    }

    // Get Department DTO by Id
    @RequestMapping(value = "/dto/{departmentId}", method = RequestMethod.GET)
    public DepartmentDTO getDepartmentDTOById(@PathVariable Long departmentId) throws Exception {
        if (departmentId == null) {
            throw new EntityNotFoundException("Null department id found!");
        }

        return departmentMapper.map(departmentService.read(departmentId));
    }
}
