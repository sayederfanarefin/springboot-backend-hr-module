package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Leave;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRepository extends CrudRepository<Leave, Long> {

    Leave getFirstById(Long id);

    @Query("SELECT l FROM Leave l WHERE l.leaveStatus.status = :status AND l.requestedBy.company.id = :companyId ORDER BY l.requestedOn DESC")
    Page<Leave> findAllByStatusAndCompany(@Param("status") String status,
                                          @Param("companyId") Long companyId,
                                          Pageable pageable);

    @Query("SELECT l FROM Leave l WHERE l.leaveStatus.status = :status AND l.requestedBy.id = :userId ORDER BY l.requestedOn DESC")
    Page<Leave> findAllByStatusAndUser(@Param("status") String status,
                                           @Param("userId") Long userId,
                                           Pageable pageable);

    @Query(value = "SELECT * FROM leaves l WHERE l.requested_by_id = :userId AND ((YEAR(l.from_date) = :year AND MONTH(l.from_date) = :month) OR (YEAR(l.to_date) = :year AND MONTH(l.to_date) = :month)) AND (SELECT status FROM leave_status WHERE id = l.leave_status_id) = :status ORDER BY l.requested_on DESC", nativeQuery = true)
    List<Leave> findMonthlyApprovedByUser(@Param("userId") Long userId,
                                          @Param("status") String status,
                                          @Param("year") Integer year,
                                          @Param("month") Integer month);

    @Query(value = "SELECT * FROM leaves l WHERE l.requested_by_id = :userId AND ((YEAR(l.from_date) = :year) OR (YEAR(l.to_date) = :year)) AND (SELECT status FROM leave_status WHERE id = l.leave_status_id) = :status ORDER BY l.requested_on DESC", nativeQuery = true)
    List<Leave> findYearlyApprovedByUser(@Param("userId") Long userId,
                                          @Param("status") String status,
                                          @Param("year") Integer year);
}
