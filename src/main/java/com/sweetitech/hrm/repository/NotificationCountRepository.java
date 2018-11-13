package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.NotificationCount;
import org.springframework.data.repository.CrudRepository;

public interface NotificationCountRepository extends CrudRepository<NotificationCount, Long> {

    NotificationCount getFirstByUserId(Long userId);

}
