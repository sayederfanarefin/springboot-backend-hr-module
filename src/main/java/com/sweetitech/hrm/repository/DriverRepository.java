package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DriverRepository extends CrudRepository<Driver, Long> {

    Driver getFirstById(Long id);

    Driver getFirstByUserId(Long userId);

    Page<Driver> findAllByUserCompanyIdAndIsDeletedFalseOrderByUserBasicInfoFirstNameAsc(Long companyId, Pageable pageable);

    List<Driver> findAllByUserCompanyIdAndIsDeletedFalseOrderByUserBasicInfoFirstNameAsc(Long companyId);

    Page<Driver> findAllByUserCompanyIdAndIsDeletedTrueOrderByUserBasicInfoFirstNameAsc(Long companyId, Pageable pageable);

}
