package com.sweetitech.hrm.controller.all;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.exception.NotFoundException;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.service.implementation.PasswordResetRequestServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import com.sweetitech.hrm.service.mapping.PasswordResetRequestMapper;
import com.sweetitech.hrm.service.mapping.PasswordResetRequestResponseMapper;
import com.sweetitech.hrm.service.mapping.UserEditMapper;
import com.sweetitech.hrm.service.mapping.UserMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/all/users")
public class UserController {

    private UserServiceImpl userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserEditMapper userEditMapper;

    @Autowired
    private PasswordResetRequestMapper passwordResetRequestMapper;

    @Autowired
    private PasswordResetRequestResponseMapper passwordResetRequestResponseMapper;

    @Autowired
    private PasswordResetRequestServiceImpl passwordResetRequestService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // Create user
    @RequestMapping(value = "", method = RequestMethod.POST)
    public User createUser(@RequestBody UserDTO dto) throws Exception {
        return userService.create(userMapper.map(dto));
    }

    // Check username
    @RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
    public boolean checkUsername(@PathVariable String username) throws Exception {
        if (username == null) {
            throw new NotFoundException("Null username provided!");
        }

        return userService.readByUsername(username) == null;
    }

    // Get By Company Id
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public Iterable<User> getAllByCompanyId(@PathVariable Long companyId,
                                            @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new NotFoundException("Invalid company id!");
        }

        return userService.readByCompanyId(companyId, page);
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

        return userService.update(updatedUser);
    }

    //Update Profile Picture
    @RequestMapping(value = "/profilePicture", method = RequestMethod.POST)
    public ResponseEntity updateProfilePicture(@RequestParam("id") Long id,
                                               @RequestParam("profileImage") MultipartFile multipartFile) throws NotFoundException, IOException {
        userService.updateProfilePicture(id, multipartFile);
        return ResponseEntity.ok().build();
    }

    // returns image (File) with that entity id
    @RequestMapping(value = "/images/file/{userId}", method = RequestMethod.GET)
    private void getImageFile(@PathVariable Long userId, HttpServletResponse response) throws IOException {
        User user = userService.read(userId);
        if (user == null || user.getBasicInfo().getProfilePicture() == null) return;
        // InputStream inputStream = new ByteArrayInputStream(user.getBasicInfo().getProfilePicture());
        InputStream inputStream = new ByteArrayInputStream(null);
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
        response.setContentType("image/png");
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

    // Update Username
    @RequestMapping(value = "/username/{userId}", method = RequestMethod.POST)
    public User updateUsername(@PathVariable Long userId,
                               @RequestBody SingleStringObjectDTO dto) throws Exception {
        if (userId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return userService.updateUsername(userId, dto.getObject());
    }

    // Request reset password
    @RequestMapping(value = "/reset-password/request", method = RequestMethod.POST)
    public PasswordResetRequestResponseDTO createRequest(@RequestBody SingleStringObjectDTO dto) throws Exception {
        if (dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return passwordResetRequestResponseMapper.map(passwordResetRequestService.initiateRequest(dto.getObject()));
    }
}
