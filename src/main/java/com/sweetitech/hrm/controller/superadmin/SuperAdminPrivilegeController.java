package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Privilege;
import com.sweetitech.hrm.domain.dto.PrivilegeDTO;
import com.sweetitech.hrm.service.implementation.PrivilegeServiceImpl;
import com.sweetitech.hrm.service.mapping.PrivilegeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/super-admin/privileges")
public class SuperAdminPrivilegeController {

    private PrivilegeServiceImpl privilegeService;

    @Autowired
    private PrivilegeMapper privilegeMapper;

    @Autowired
    public SuperAdminPrivilegeController(PrivilegeServiceImpl privilegeService) {
        this.privilegeService = privilegeService;
    }

    // Create Privilege
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Privilege createPrivilege(@RequestBody PrivilegeDTO dto) throws Exception {
        return privilegeService.create(privilegeMapper.map(dto));
    }

    // Edit Privilege
    @RequestMapping(value = "/{privilegeId}", method = RequestMethod.PUT)
    public Privilege updatePrivilege(@PathVariable Long privilegeId, @RequestBody PrivilegeDTO dto) throws Exception {
        if (privilegeId == null) {
            throw new EntityNotFoundException("Null privilege id found!");
        }

        return privilegeService.update(privilegeId, privilegeMapper.map(dto));
    }

    // Get Privileges
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<Privilege> getPrivileges(@RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        return privilegeService.readAll(page);
    }

    // Get Privileges as List
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Privilege> getPrivilegesAsList() throws Exception {
        return privilegeService.readAll();
    }

    // Get Privilege by Id
    @RequestMapping(value = "/{privilegeId}", method = RequestMethod.GET)
    public Privilege getPrivilegeById(@PathVariable Long privilegeId) throws Exception {
        if (privilegeId == null) {
            throw new EntityNotFoundException("Null privilege id found!");
        }

        return privilegeService.read(privilegeId);
    }

    // Get Privilege DTO by Id
    @RequestMapping(value = "/dto/{privilegeId}", method = RequestMethod.GET)
    public PrivilegeDTO getPrivilegeDTOById(@PathVariable Long privilegeId) throws Exception {
        if (privilegeId == null) {
            throw new EntityNotFoundException("Null privilege id found!");
        }

        return privilegeMapper.map(privilegeService.read(privilegeId));
    }

    // Soft Delete Privilege
    @RequestMapping(value = "/{privilegeId}", method = RequestMethod.DELETE)
    public void deletePrivilege(@PathVariable Long privilegeId) throws Exception {
        if (privilegeId == null) {
            throw new EntityNotFoundException("Null privilege id found!");
        }

        privilegeService.remove(privilegeId);
    }
}
