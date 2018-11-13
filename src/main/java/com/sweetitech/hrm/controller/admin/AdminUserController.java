package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.exception.NotFoundException;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.SingleStringObjectDTO;
import com.sweetitech.hrm.domain.dto.UserDTO;
import com.sweetitech.hrm.domain.dto.UserSmallResponseDTO;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import com.sweetitech.hrm.service.mapping.UserEditMapper;
import com.sweetitech.hrm.service.mapping.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.sweetitech.hrm.report.service.PDFService.generateEmployeeList;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

    private UserServiceImpl userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserEditMapper userEditMapper;

    @Autowired
    public AdminUserController(UserServiceImpl userService) {
        this.userService = userService;
    }

//    @Resource(name="tokenStore")
//    TokenStore tokenStore;
//    @Resource(name="tokenServices")
//    ConsumerTokenServices tokenServices;

    // Create user
    @RequestMapping(value = "", method = RequestMethod.POST)
    public User createUser(@RequestBody UserDTO dto) throws Exception {
        return userService.create(userMapper.map(dto));
    }

    // Get All Employees By Company Id
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public Iterable<User> getAllByCompanyId(@PathVariable Long companyId,
                                            @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new NotFoundException("Invalid company id!");
        }

        return userService.readEmployeesByCompanyId(companyId, page);
    }

    // Get All Employees by Company and Department
    @RequestMapping(value = "/company/{companyId}/department/{departmentId}", method = RequestMethod.GET)
    public Iterable<User> getAllByCompanyAndDepartment(@PathVariable Long companyId,
                                                       @PathVariable Long departmentId,
                                                       @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new NotFoundException("Invalid company id!");
        }

        return userService.readAllActiveEmployeeByCompanyIdAndDepartmentId(companyId, departmentId, page);
    }

    // Soft Delete User
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Long userId) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        userService.remove(userId);
//        String tokenId = tokenStore.findTokensByClientId()
//        tokenServices.revokeToken(tokenId);
    }

    // Search Active Users By Company Id
    @RequestMapping(value = "/search/{companyId}", method = RequestMethod.GET)
    public Iterable<User> searchActiveUser(@PathVariable Long companyId,
                                             @RequestParam String name,
                                             @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return userService.searchActiveByCompanyIdAndName(companyId, name, name, name, page);
    }

    // Search employees by company, department and name
    @RequestMapping(value = "/search/{companyId}/department/{departmentId}", method = RequestMethod.GET)
    public List<UserSmallResponseDTO> searchNonSuperAdminsByCompanyAndDepartment(@PathVariable Long companyId,
                                                                            @PathVariable Long departmentId,
                                                                            @RequestParam String name) throws Exception {
        if (companyId == null || departmentId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return userService.searchActiveNonSuperAdminByCompanyIdDepartmentIdAndName(companyId, departmentId, name, name);
    }

    // Get All by Office Code Id
    @RequestMapping(value = "/office-code/{officeCodeId}", method = RequestMethod.GET)
    public Iterable<User> getAllByOfficeCodeId(@PathVariable Long officeCodeId,
                                               @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (officeCodeId == null) {
            throw new EntityNotFoundException("Null office code id found!");
        }

        return userService.readAllActiveByOfficeCodeId(officeCodeId, page);
    }

    // Get All by Office Code
    @RequestMapping(value = "/office-code", method = RequestMethod.GET)
    public Iterable<User> getAllByOfficeCode(@RequestParam String code,
                                             @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (code == null) {
            throw new EntityNotFoundException("Null code value found!");
        }

        return userService.readAllActiveByOfficeCodeCode(code, page);
    }

    // List all by company and department
    @RequestMapping(value = "/list/company/{companyId}/department/{departmentId}", method = RequestMethod.GET)
    public List<UserSmallResponseDTO> listAllByCompanyAndDepartment(@PathVariable Long companyId,
                                                                    @PathVariable Long departmentId) throws Exception {
        if (companyId == null || departmentId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return userService.readAllActiveUsersByCompanyAndDepartment(companyId, departmentId);
    }

    // List all active by company, department and office code
    @RequestMapping(value = "/list/active", method = RequestMethod.GET)
    public List<User> listAllActive(@RequestParam Long companyId,
                                    @RequestParam Long departmentId,
                                    @RequestParam Long officeCodeId) throws Exception {
        if (companyId == null || departmentId == null || officeCodeId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return userService.listActiveUsers(companyId, departmentId, officeCodeId);
    }

    // Search all active by company, department and office code
    @RequestMapping(value = "/search/active", method = RequestMethod.POST)
    public List<User> searchAllActive(@RequestParam Long companyId,
                                      @RequestParam Long departmentId,
                                      @RequestParam Long officeCodeId,
                                      @RequestBody SingleStringObjectDTO dto) throws Exception {
        if (companyId == null || departmentId == null || officeCodeId == null || dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return userService.searchActiveUsers(companyId, departmentId, officeCodeId, dto.getObject());
    }

    // List all inactive by company, department and office code
    @RequestMapping(value = "/list/inactive", method = RequestMethod.GET)
    public List<User> listAllInactive(@RequestParam Long companyId,
                                      @RequestParam Long departmentId,
                                      @RequestParam Long officeCodeId) throws Exception {
        if (companyId == null || departmentId == null || officeCodeId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return userService.listInactiveUsers(companyId, departmentId, officeCodeId);
    }

    // Get list of employees with corresponding details
    @RequestMapping(value = "/report/list", method = RequestMethod.GET)
    public ResponseEntity<Resource> getEmployeeList(@RequestParam Long companyId,
                                                    @RequestParam Long departmentId,
                                                    @RequestParam Long officeCodeId) throws Exception {
        if (companyId == null || departmentId == null  || officeCodeId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath =
                generateEmployeeList("/employeeList",
                        companyId, departmentId, officeCodeId);
        System.out.println("DL File Path: "+downloadFilePath);

        if (downloadFilePath == null)
            throw new NullPointerException("data missing");

        if (downloadFilePath == null)
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body(null);

        File file = new File(downloadFilePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"report-monthlyAttendanceSummary" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
