package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.PasswordResetRequest;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.PasswordResetRequestResponseDTO;
import org.springframework.data.domain.Page;

public interface PasswordResetRequestService {

    PasswordResetRequest create(PasswordResetRequest passwordResetRequest);

    PasswordResetRequest initiateRequest(String username) throws Exception;

    Page<PasswordResetRequest> readAllByCompanyId(Long companyId, Integer page);

    PasswordResetRequest read(Long id) throws Exception;

    PasswordResetRequestResponseDTO readDTO(Long id) throws Exception;

    void remove(Long id) throws Exception;

    User approveRequest(Long id, String newPassword) throws Exception;

}
