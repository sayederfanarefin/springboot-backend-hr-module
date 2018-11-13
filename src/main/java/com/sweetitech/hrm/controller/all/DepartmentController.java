package com.sweetitech.hrm.controller.all;

import com.sweetitech.hrm.domain.Department;
import com.sweetitech.hrm.domain.dto.DepartmentDTO;
import com.sweetitech.hrm.service.implementation.DepartmentServiceImpl;
import com.sweetitech.hrm.service.mapping.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/all/departments")
public class DepartmentController {

    private DepartmentServiceImpl departmentService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    // Create department
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Department createDepartment(@RequestBody DepartmentDTO dto) throws Exception {
        return departmentService.create(departmentMapper.map(dto));
    }

    // Get All Departments by Company Id
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public List<Department> getDepartmentsByCompanyId(@PathVariable long companyId) {
        return departmentService.readAllByCompanyId(companyId);
    }
}
