package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.exception.NotFoundException;
import com.sweetitech.hrm.domain.Grade;
import com.sweetitech.hrm.domain.Image;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.SingleStringObjectDTO;
import com.sweetitech.hrm.domain.dto.UserDTO;
import com.sweetitech.hrm.domain.dto.UserEditDTO;
import com.sweetitech.hrm.domain.dto.UserSmallResponseDTO;
import com.sweetitech.hrm.service.implementation.ImageServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import com.sweetitech.hrm.service.mapping.UserEditMapper;
import com.sweetitech.hrm.service.mapping.UserMapper;
import com.sweetitech.hrm.service.mapping.UserSmallResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee/users")
public class EmployeeUserController {

    private UserServiceImpl userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserEditMapper userEditMapper;

    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    public EmployeeUserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // Update User
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public User updateUser(@RequestBody UserEditDTO dto, @PathVariable long userId) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("User does not exist");
        }

        User updatedUser = userEditMapper.map(dto);
        updatedUser.setId(userId);
        updatedUser.setUsername(user.getUsername());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setDeleted(user.isDeleted());

        return userService.update(updatedUser);
    }

    //Update Profile Picture
    @RequestMapping(value = "/profilePicture", method = RequestMethod.POST)
    public ResponseEntity updateProfilePicture(@RequestParam("id") Long id,
                                               @RequestParam("profileImage") MultipartFile multipartFile) throws NotFoundException, IOException {
        userService.updateProfilePicture(id, multipartFile);
        return ResponseEntity.ok().build();
    }

    // Send User DTO
    @RequestMapping(value = "/dto/{userId}", method = RequestMethod.GET)
    public UserDTO getUserDTO(@PathVariable Long userId) throws Exception {
        if (userId == null) {
            throw new Exception("Null user id found!");
        }

        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user found with id: " + userId);
        }

        return userMapper.map(user);
    }

    // Get By User Id
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable long userId) throws Exception {
        User user = userService.readById(userId);
        if (user == null) {
            throw new EntityNotFoundException("User does not exist!");
        }

        return user;
    }

    @RequestMapping(value = "/profilePicture/{profilePictureId}", method = RequestMethod.GET)
    public Image getProfilePictureById(@PathVariable Long profilePictureId) throws Exception {
        if (profilePictureId == null) {
            throw new EntityNotFoundException("Null profile picture id found!");
        }

        return imageService.findById(profilePictureId);
    }

    // Get Grade for User
    @RequestMapping(value = "/grade/{userId}", method = RequestMethod.GET)
    public Grade getGradeForUser(@PathVariable Long userId) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        return userService.read(userId).getGrade();
    }

    // Change Password
    @RequestMapping(value = "/password/{userId}", method = RequestMethod.POST)
    public User updatePassword(@PathVariable Long userId, @RequestParam String currentPassword, @RequestParam String newPassword) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with this User Id!");
        }

        return userService.updatePassword(user, currentPassword, newPassword);
    }

    // Search employees by company, department and name
    @RequestMapping(value = "/search/{companyId}/department/{departmentId}", method = RequestMethod.GET)
    public List<UserSmallResponseDTO> searchEmployeesByCompanyAndDepartment(@PathVariable Long companyId,
                                                                            @PathVariable Long departmentId,
                                                                            @RequestParam String name) throws Exception {
        if (companyId == null || departmentId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return userService.searchActiveByCompanyIdDepartmentIdAndName(companyId, departmentId, name, name);
    }

    // Get small response dto
    @RequestMapping(value = "/smallDTO/{userId}", method = RequestMethod.GET)
    public UserSmallResponseDTO getSmallDTO(@PathVariable Long userId) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return userService.readSmallResponse(userId);
    }

    // Search all active by company, department and office code
    @RequestMapping(value = "/search/active/dto", method = RequestMethod.POST)
    public List<UserSmallResponseDTO> searchAllActive(@RequestParam Long companyId,
                                      @RequestParam Long departmentId,
                                      @RequestParam Long officeCodeId,
                                      @RequestBody SingleStringObjectDTO dto) throws Exception {
        if (companyId == null || departmentId == null || officeCodeId == null || dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        List<User> users = userService.searchActiveUsers(companyId, departmentId, officeCodeId, dto.getObject());

        List<UserSmallResponseDTO> userSmallResponseDTOs = new ArrayList<>();
        if (users != null && users.size() > 0) {
            for (User user: users) {
                userSmallResponseDTOs.add(userSmallResponseMapper.map(user));
            }
        }

        return userSmallResponseDTOs;
    }
}
