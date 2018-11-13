package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Department;
import com.sweetitech.hrm.domain.dto.DepartmentDTO;
import com.sweetitech.hrm.service.implementation.DepartmentServiceImpl;
import com.sweetitech.hrm.service.mapping.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/super-admin/departments")
public class SuperAdminDepartmentController {

    private DepartmentServiceImpl departmentService;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    public SuperAdminDepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    // Create department
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Department createDepartment(@RequestBody DepartmentDTO dto) throws Exception {
        return departmentService.create(departmentMapper.map(dto));
    }

    // Check Department Name
    @RequestMapping(value = "/name/{companyId}", method = RequestMethod.GET)
    public boolean checkDeptName(@PathVariable Long companyId, @RequestParam String name) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return departmentService.checkName(companyId, name);
    }

    // Edit Department
    @RequestMapping(value = "/{departmentId}", method = RequestMethod.PUT)
    public Department updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentDTO dto) throws Exception {
        if (departmentId == null) {
            throw new EntityNotFoundException("Null department id found!");
        }

        return departmentService.update(departmentId, departmentMapper.map(dto));
    }

    // Soft Delete Department
    @RequestMapping(value = "/{departmentId}", method = RequestMethod.DELETE)
    public void deleteCompany(@PathVariable Long departmentId) throws Exception {
        if (departmentId == null) {
            throw new EntityNotFoundException("Null department id provided!");
        }

        departmentService.removeActiveState(departmentId);
    }
}
