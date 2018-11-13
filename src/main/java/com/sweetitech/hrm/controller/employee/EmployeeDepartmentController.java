package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.domain.Department;
import com.sweetitech.hrm.service.implementation.DepartmentServiceImpl;
import com.sweetitech.hrm.service.mapping.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee/departments")
public class EmployeeDepartmentController {

    private DepartmentServiceImpl departmentService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    public EmployeeDepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    // Get All Departments by Company Id
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public List<Department> getDepartmentsByCompanyId(@PathVariable long companyId) {
        return departmentService.readAllByCompanyId(companyId);
    }

}
