package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.CarRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CarRequestRepository extends CrudRepository<CarRequest, Long> {

    CarRequest getFirstById(Long id);

    // TODO get all by company id and status sorted by request on desc
    Page<CarRequest> findAllByRequestedByCompanyIdAndStatusOrderByRequestedOnDesc(Long companyId, String status, Pageable pageable);

    // TODO get all by company id, requested from, sorted by requested from asc
    @Query(value = "SELECT * FROM car_requests cr WHERE ((SELECT company_id FROM users WHERE id = cr.requested_by_id) = :companyId) AND YEAR(cr.requested_from) = :year AND MONTH(cr.requested_from) = :month AND DAY(cr.requested_from) = :day AND cr.status = :status ORDER BY cr.from_hour ASC", nativeQuery = true)
    Page<CarRequest> getAllByCompanyIdAndRequestedFrom(@Param("companyId") Long companyId,
                                                       @Param("year") Integer year,
                                                       @Param("month") Integer month,
                                                       @Param("day") Integer day,
                                                       @Param("status") String status,
                                                       Pageable pageable);

    // TODO get all by user id, sorted by requested on asc
    Page<CarRequest> findAllByRequestedByIdAndStatusOrderByRequestedFromDesc(Long userId, String status, Pageable pageable);

    // TODO get all approved by month and year and company id, sorted by requested on desc
    @Query(value = "SELECT * FROM car_requests cr WHERE ((SELECT company_id FROM users WHERE id = cr.requested_by_id) = :companyId) AND YEAR(cr.requested_from) = :year AND MONTH(cr.requested_from) = :month AND cr.status = :status ORDER BY cr.requested_from ASC", nativeQuery = true)
    Page<CarRequest> getAllMonthlyByCompanyIdandStatus(@Param("companyId") Long companyId,
                                                       @Param("year") Integer year,
                                                       @Param("month") Integer month,
                                                       @Param("status") String status,
                                                       Pageable pageable);

    // TODO search by company id and destination, sorted by requested on desc

    // TODO search between from hour and to hour and requested For and company id, sorted by from hour asc


}
