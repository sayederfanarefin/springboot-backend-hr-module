package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Allowance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AllowanceRepository extends CrudRepository<Allowance, Long> {

    Allowance getFirstById(Long id);

    @Query("SELECT a FROM Allowance a WHERE a.allowanceStatus.status = :status AND a.requestedBy.company.id = :companyId ORDER BY a.createdOn DESC")
    Page<Allowance> findAllRequested(@Param("status") String status,
                                     @Param("companyId") Long companyId,
                                     Pageable pageable);

    @Query("SELECT a FROM Allowance a WHERE a.allowanceStatus.status = :status AND a.requestedBy.id = :userId ORDER BY a.createdOn DESC")
    Page<Allowance> findAllRequestedByUser(@Param("status") String status,
                                     @Param("userId") Long userId,
                                     Pageable pageable);

    @Query("SELECT a FROM Allowance a WHERE a.requestedBy.id = :userId ORDER BY a.createdOn DESC")
    Page<Allowance> findAllByUser(@Param("userId") Long userId,
                                           Pageable pageable);

    @Query("SELECT a FROM Allowance a WHERE a.allowanceStatus.status = :status1 OR a.allowanceStatus.status = :status2 AND a.requestedBy.company.id = :companyId ORDER BY a.createdOn DESC")
    Page<Allowance> findAllDecided(@Param("status1") String status1,
                                     @Param("status2") String status2,
                                     @Param("companyId") Long companyId,
                                     Pageable pageable);

    @Query(value = "SELECT * FROM allowances a WHERE a.requested_by_id = :userId AND YEAR(a.created_on) = :year AND MONTH(a.created_on) = :month AND ((SELECT status FROM allowance_status WHERE id = a.allowance_status_id) = :status2 OR (SELECT status FROM allowance_status WHERE id = a.allowance_status_id) = :status1) ORDER BY a.updated_at ASC", nativeQuery = true)
    List<Allowance> findMonthlyApprovedByUser(@Param("status1") String status1,
                                              @Param("status2") String status2,
                                              @Param("userId") Long userId,
                                              @Param("year") int year,
                                              @Param("month") int month);

    @Query("SELECT a FROM Allowance a WHERE a.allowanceStatus.status = :status AND a.requestedBy.company.id = :companyId ORDER BY a.createdOn DESC")
    Page<Allowance> findAllByStatusAndCompany(@Param("status") String status,
                                     @Param("companyId") Long companyId,
                                     Pageable pageable);

    @Query("SELECT a FROM Allowance a WHERE a.allowanceStatus.status = :status AND a.requestedBy.id = :userId ORDER BY a.createdOn DESC")
    Page<Allowance> findAllByStatusAndUser(@Param("status") String status,
                                           @Param("userId") Long userId,
                                           Pageable pageable);

    @Query(value = "SELECT * FROM allowances a WHERE a.requested_by_id = :userId AND YEAR(a.created_on) = :year AND MONTH(a.created_on) = :month ORDER BY a.updated_at ASC LIMIT 1", nativeQuery = true)
    Allowance getFirstByUserIdAndMonthAndYear(@Param("userId") Long userId,
                                              @Param("year") int year,
                                              @Param("month") int month);

    @Query(value = "SELECT * FROM allowances a WHERE a.requested_by_id = :userId AND YEAR(a.created_on) = :year AND MONTH(a.created_on) = :month AND DAY(a.created_on) = :day AND ((SELECT status FROM allowance_status WHERE id = a.allowance_status_id) = :status2 OR (SELECT status FROM allowance_status WHERE id = a.allowance_status_id) = :status1) ORDER BY a.updated_at ASC LIMIT 1", nativeQuery = true)
    Allowance findDailyApprovedByUser(@Param("status1") String status1,
                                      @Param("status2") String status2,
                                      @Param("userId") Long userId,
                                      @Param("year") int year,
                                      @Param("month") int month,
                                      @Param("day") int day);
}
