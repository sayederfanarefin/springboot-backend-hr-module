package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.PasswordResetRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetRequestRepository extends CrudRepository<PasswordResetRequest, Long> {

    Page<PasswordResetRequest> findAllByUserCompanyId(Long companyId, Pageable pageable);

    PasswordResetRequest getFirstById(Long id);

}
