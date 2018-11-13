package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.relation.UserTaskRelation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface UserTaskRepository extends CrudRepository<UserTaskRelation, Long> {

    List<UserTaskRelation> findAllByUserId(Long id);

    List<UserTaskRelation> findAllByTaskId(Long id);

    UserTaskRelation getFirstById(Long id);

    List<UserTaskRelation> findAllByUserIdAndTaskStatusAndTaskScheduledDateBetweenOrderByTaskScheduledDateAsc(Long userId,
                                                                                                              String status,
                                                                                                              Date fromDate,
                                                                                                              Date toDate);

    void deleteAllByTaskId(Long id);

    void deleteById(Long id);

    UserTaskRelation getFirstByUserIdAndTaskId(Long userId, Long taskId);

//    @Query("SELECT * FROM user_task_relations utr WHERE utr.user_id = :userId AND (SELECT )")
//    List<UserTaskRelation>

}
