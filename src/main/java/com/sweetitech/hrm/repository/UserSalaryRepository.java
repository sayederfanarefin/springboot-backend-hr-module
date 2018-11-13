package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.relation.UserSalaryRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface UserSalaryRepository extends CrudRepository<UserSalaryRelation, Long> {

    UserSalaryRelation findTopByUserIdOrderByLastUpdatedDesc(Long id);

    Page<UserSalaryRelation> findAllByUserIdOrderByLastUpdatedDesc(Long id, Pageable pageable);

    UserSalaryRelation getFirstById(Long id);

}
