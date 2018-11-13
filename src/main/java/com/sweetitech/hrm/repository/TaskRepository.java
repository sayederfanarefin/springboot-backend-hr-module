package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    Task getFirstById(Long id);

    List<Task> findAllByDescriptionAndStatusOrderByScheduledDateAsc(String description, String status);

    @Query(value = "SELECT * FROM tasks t WHERE t.category = :category AND t.description LIKE CONCAT('%', :description, '%') AND YEAR(t.scheduled_date) = :year AND t.status = :status ORDER BY t.scheduled_date ASC", nativeQuery = true)
    List<Task> findAllApprovedHolidaysByDescriptionAndYear(@Param("description") String description,
                                                           @Param("year") Integer year,
                                                           @Param("status") String status,
                                                           @Param("category") String category);

    @Query(value = "SELECT * FROM tasks t WHERE t.category = :category AND t.description LIKE CONCAT('%', :description, '%') AND YEAR(t.scheduled_date) = :year AND t.status = :status AND t.company_id = :companyId ORDER BY t.scheduled_date ASC", nativeQuery = true)
    Page<Task> findAllApprovedHolidaysByDescriptionAndYear(@Param("description") String description,
                                                           @Param("year") Integer year,
                                                           @Param("status") String status,
                                                           @Param("category") String category,
                                                           @Param("companyId") Long companyId,
                                                           Pageable pageable);

    @Query(value = "SELECT * FROM tasks t WHERE t.category = :category AND YEAR(t.scheduled_date) = :year AND MONTH(t.scheduled_date) = :month AND t.status = :status AND t.company_id = :companyId ORDER BY t.scheduled_date ASC", nativeQuery = true)
    List<Task> findAllHolidaysByMonthAndYear(@Param("year") Integer year,
                                             @Param("month") Integer month,
                                             @Param("status") String status,
                                             @Param("category") String category,
                                             @Param("companyId") Long companyId);

    Task getFirstByScheduledDateAndCategoryAndStatusAndCompanyId(Date scheduledDate, String category, String status, Long companyId);

    @Query(value = "SELECT * FROM tasks t WHERE t.for_all = true AND YEAR(t.scheduled_date) = :year AND MONTH(t.scheduled_date) = :month AND t.status = :status AND t.company_id = :companyId ORDER BY t.scheduled_date ASC", nativeQuery = true)
    List<Task> findAllApprovedTasksByMonthAndYear(@Param("year") Integer year,
                                                  @Param("month") Integer month,
                                                  @Param("status") String status,
                                                  @Param("companyId") Long companyId);

    @Query(value = "SELECT * FROM tasks t WHERE YEAR(t.scheduled_date) = :year AND MONTH(t.scheduled_date) = :month AND t.status = :status AND t.assigned_by_id = :userId ORDER BY t.scheduled_date ASC", nativeQuery = true)
    List<Task> findAllApprovedTasksAssignedByMeByMonthAndYear(@Param("year") Integer year,
                                                              @Param("month") Integer month,
                                                              @Param("status") String status,
                                                              @Param("userId") Long userId);

    @Query(value = "SELECT * FROM tasks t WHERE YEAR(t.scheduled_date) = :year AND MONTH(t.scheduled_date) = :month AND t.status = :status AND t.assigned_by_id = :userId ORDER BY t.scheduled_date ASC", nativeQuery = true)
    Page<Task> findAllApprovedTasksAssignedByMeByMonthAndYear(@Param("year") Integer year,
                                                              @Param("month") Integer month,
                                                              @Param("status") String status,
                                                              @Param("userId") Long userId,
                                                              Pageable pageable);

}
