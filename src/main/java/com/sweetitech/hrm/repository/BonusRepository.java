package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Bonus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BonusRepository extends CrudRepository<Bonus, Long> {

    Bonus getFirstById(Long id);

    Page<Bonus> findAllByPaidToIdAndYearOrderByDateOfOrderAsc(Long paidToId, Integer year, Pageable pageable);

    Page<Bonus> findAllByApprovedByIdAndMonthAndYearOrderByDateOfOrderAsc(Long approvedById, Integer month, Integer year, Pageable pageable);

    @Query(value = "SELECT * FROM bonuses b WHERE b.month = :month AND b.year = :year AND (SELECT company_id FROM users WHERE id = b.paid_to_id) = :companyId ORDER BY b.date_of_order ASC", nativeQuery =  true)
    Page<Bonus> findAllByCompanyMonthAndYear(@Param("month") Integer month,
                                             @Param("year") Integer year,
                                             @Param("companyId") Long companyId,
                                             Pageable pageable);

    @Query(value = "SELECT * FROM bonuses b WHERE b.year = :year AND (SELECT company_id FROM users WHERE id = b.paid_to_id) = :companyId ORDER BY b.date_of_order ASC", nativeQuery =  true)
    Page<Bonus> findAllByCompanyAndYear(@Param("year") Integer year,
                                        @Param("companyId") Long companyId,
                                        Pageable pageable);

    Bonus getFirstByPaidToIdAndYearAndTypeOfBonusId(Long paidToId, Integer year, Long typeId);

}
