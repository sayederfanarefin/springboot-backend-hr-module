package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {

    Page<Notification> findAllByUserIdOrForAllTrueAndCompanyIdOrderByCreatedOnDesc(Long userId, Long companyId, Pageable pageable);

    Notification getFirstById(Long id);

    @Query(value = "SELECT * FROM notifications n WHERE n.user_id = :userId AND n.title LIKE CONCAT('%', :month, '%') ORDER BY n.created_on DESC", nativeQuery = true)
    List<Notification> findAllByUserIdAndMonth(@Param("userId") Long userId,
                                               @Param("month") String month);

}
