package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.PasswordResetRequest;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.PasswordResetRequestResponseDTO;
import com.sweetitech.hrm.repository.PasswordResetRequestRepository;
import com.sweetitech.hrm.service.PasswordResetRequestService;
import com.sweetitech.hrm.service.UserService;
import com.sweetitech.hrm.service.mapping.PasswordResetRequestResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PasswordResetRequestServiceImpl implements PasswordResetRequestService {

    private PasswordResetRequestRepository passwordResetRequestRepository;

    @Autowired
    private PasswordResetRequestResponseMapper passwordResetRequestResponseMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    public PasswordResetRequestServiceImpl(PasswordResetRequestRepository passwordResetRequestRepository) {
        this.passwordResetRequestRepository = passwordResetRequestRepository;
    }

    @Override
    public PasswordResetRequest create(PasswordResetRequest passwordResetRequest) {
        return passwordResetRequestRepository.save(passwordResetRequest);
    }

    @Override
    public PasswordResetRequest initiateRequest(String username) throws Exception {
        User user = userService.readByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException("No user with username: " + username);
        }

        PasswordResetRequest passwordResetRequest = new PasswordResetRequest();
        passwordResetRequest.setUser(user);
        passwordResetRequest.setRequestedOn(new Date());

        return this.create(passwordResetRequest);
    }

    @Override
    public Page<PasswordResetRequest> readAllByCompanyId(Long companyId, Integer page) {
        return passwordResetRequestRepository.findAllByUserCompanyId(companyId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public PasswordResetRequest read(Long id) throws Exception {
        return passwordResetRequestRepository.getFirstById(id);
    }

    @Override
    public PasswordResetRequestResponseDTO readDTO(Long id) throws Exception {
        return passwordResetRequestResponseMapper.map(passwordResetRequestRepository.getFirstById(id));
    }

    @Override
    public void remove(Long id) throws Exception {
        PasswordResetRequest resetRequest = passwordResetRequestRepository.getFirstById(id);
        if (resetRequest == null) {
            throw new EntityNotFoundException("No request with id: " + id);
        }

        passwordResetRequestRepository.delete(resetRequest);
    }

    @Override
    public User approveRequest(Long id, String newPassword) throws Exception {
        PasswordResetRequest resetRequest = this.read(id);

        User user = userService.resetPassword(resetRequest.getUser().getId(), newPassword);

        this.remove(resetRequest.getId());

        return user;
    }
}
