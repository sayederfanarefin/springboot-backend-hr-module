package com.sweetitech.hrm.controller.superadmin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.exception.NotFoundException;
import com.sweetitech.hrm.domain.PasswordResetRequest;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.PasswordResetRequestResponseDTO;
import com.sweetitech.hrm.domain.dto.UserDTO;
import com.sweetitech.hrm.domain.dto.UserEditDTO;
import com.sweetitech.hrm.service.implementation.PasswordResetRequestServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import com.sweetitech.hrm.service.mapping.UserEditMapper;
import com.sweetitech.hrm.service.mapping.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/super-admin/users")
public class SuperAdminUserController {

    private UserServiceImpl userService;

    @Autowired
    private UserEditMapper userEditMapper;

    @Autowired
    private PasswordResetRequestServiceImpl passwordResetRequestService;

    @Autowired
    public SuperAdminUserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // Assign Role to User
    @RequestMapping(value = "/role/{userId}", method = RequestMethod.PUT)
    public User assignRole(@PathVariable Long userId, @RequestParam Long roleId) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id provided!");
        }

        if (roleId == null) {
            throw new EntityNotFoundException("Null role id provided!");
        }

        return userService.updateRole(userId, roleId);
    }

    // Get All Non-Admins By Company Id
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public Iterable<User> getAllByCompanyId(@PathVariable Long companyId,
                                            @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new NotFoundException("Invalid company id!");
        }

        return userService.readNonAdminsByCompanyId(companyId, page);
    }

    // Get All Non Super Admins by Company and Department
    @RequestMapping(value = "/company/{companyId}/department/{departmentId}", method = RequestMethod.GET)
    public Iterable<User> getAllByCompanyAndDepartment(@PathVariable Long companyId,
                                                       @PathVariable Long departmentId,
                                                       @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new NotFoundException("Invalid company id!");
        }

        return userService.readAllActiveNonSuperAdminByCompanyIdAndDepartmentId(companyId, departmentId, page);
    }

    // Get All Inactive
    @RequestMapping(value = "/inactive", method = RequestMethod.GET)
    public Iterable<User> getAllInactive(@RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        return userService.readAllInactive(page);
    }

    // Get All Inactive By Company Id
    @RequestMapping(value = "/inactive/company/{companyId}", method = RequestMethod.GET)
    public Iterable<User> getAllInactiveByCompanyId(@PathVariable Long companyId,
                                                    @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return userService.readAllInactiveByCompanyId(companyId, page);
    }

    // Get Inactive User DTO by User Id
    @RequestMapping(value = "/inactive/{userId}", method = RequestMethod.GET)
    public UserEditDTO getInactiveUserDTO(@PathVariable Long userId) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return userEditMapper.map(userService.readInactiveById(userId));
    }

    // Activate Inactive User By Id
    @RequestMapping(value = "/inactive/{userId}", method = RequestMethod.PUT)
    public User activateUser(@PathVariable Long userId) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return userService.updateInactiveUser(userId);
    }

    // Search Active Non Super Admins by Company Id
    @RequestMapping(value = "/search/{companyId}", method = RequestMethod.GET)
    public Iterable<User> searchActiveNonSuperAdminUsers(@PathVariable Long companyId,
                                           @RequestParam String name,
                                           @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return userService.searchActiveNonSuperAdminByCompanyIdAndName(companyId, name, name, page);
    }

    // Search Inactive Users By Company Id
    @RequestMapping(value = "/inactive/search/{companyId}", method = RequestMethod.GET)
    public Iterable<User> searchInactiveUser(@PathVariable Long companyId,
                                             @RequestParam String name,
                                             @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return userService.searchInactiveByCompanyIdAndName(companyId, name, name, page);
    }

    // Update Grade of User
    @RequestMapping(value = "/grade/{userId}", method = RequestMethod.PUT)
    public User updateGradeByUserId(@PathVariable Long userId, @RequestParam Long gradeId) throws Exception {
        if (userId == null || gradeId == null) {
            throw new EntityNotFoundException("Null ids found!");
        }

        return userService.updateGrade(userId, gradeId);
    }

    // Reset Password
    @RequestMapping(value = "/reset-password/{userId}", method = RequestMethod.POST)
    public User resetPassword(@PathVariable Long userId, @RequestParam String newPassword) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return userService.resetPassword(userId, newPassword);
    }

    // Get all reset password requests
    @RequestMapping(value = "/reset-password/company/{companyId}", method = RequestMethod.GET)
    public Iterable<PasswordResetRequest> getAllRequests(@PathVariable Long companyId,
                                                         @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return passwordResetRequestService.readAllByCompanyId(companyId, page);
    }

    // Delete password reset request
    @RequestMapping(value = "/reset-password/delete/{requestId}", method = RequestMethod.DELETE)
    public void deleteRequest(@PathVariable Long requestId) throws Exception {
        if (requestId == null) {
            throw new EntityNotFoundException("Null request id found!");
        }

        passwordResetRequestService.remove(requestId);
    }

    // Approve password reset request
    @RequestMapping(value = "/reset-password/approve/{requestId}", method = RequestMethod.POST)
    public User approveRequest(@PathVariable Long requestId, @RequestParam String newPassword) throws Exception {
        if (requestId == null || newPassword == null || newPassword.length() < 6) {
            throw new EntityNotFoundException("Invalid values found!");
        }

        return passwordResetRequestService.approveRequest(requestId, newPassword);
    }

    // Get password reset request dto by id
    @RequestMapping(value = "/reset-password/dto/{requestId}", method = RequestMethod.GET)
    public PasswordResetRequestResponseDTO getRequestDTO(@PathVariable Long requestId) throws Exception {
        if (requestId == null) {
            throw new EntityNotFoundException("Null request id found!");
        }

        return passwordResetRequestService.readDTO(requestId);
    }
}
