package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Tour;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TourRepository extends CrudRepository<Tour, Long> {

    Tour getFirstById(Long id);

    @Query(value = "SELECT * FROM tours t WHERE t.requested_by_id = :userId AND YEAR(t.from_date) = :year AND t.status = :status ORDER BY t.from_date ASC", nativeQuery = true)
    List<Tour> findAllByUserAndYear(@Param("userId") Long userId,
                                    @Param("year") Integer year,
                                    @Param("status") String status);

    @Query(value = "SELECT * FROM tours t WHERE (SELECT company_id FROM users u WHERE u.id = t.requested_by_id) = :companyId AND MONTH(t.from_date) = :month AND YEAR(t.from_date) = :year AND t.status = :status ORDER BY t.from_date ASC", nativeQuery = true)
    List<Tour> findAllByCompanyStatusAndMonth(@Param("companyId") Long companyId,
                                              @Param("status") String status,
                                              @Param("month") Integer month,
                                              @Param("year") Integer year);

    @Query(value = "SELECT * FROM tours t WHERE t.requested_by_id = :userId AND MONTH(t.from_date) = :month AND YEAR(t.from_date) = :year AND t.status = :status ORDER BY t.from_date ASC", nativeQuery = true)
    List<Tour> findAllByUserStatusAndMonth(@Param("userId") Long userId,
                                           @Param("status") String status,
                                           @Param("month") Integer month,
                                           @Param("year") Integer year);

    @Query(value = "SELECT * FROM tours t WHERE t.requested_by_id = :userId AND t.status = :status ORDER BY t.from_date ASC", nativeQuery = true)
    List<Tour> findAllByUserAndStatus(@Param("userId") Long userId,
                                      @Param("status") String status);

}
