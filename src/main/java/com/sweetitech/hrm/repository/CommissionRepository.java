package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Commission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CommissionRepository extends CrudRepository<Commission, Long> {

    Commission getFirstById(Long id);

    Page<Commission> findAllByPaidToIdAndYearOrderByDateOfOrderAsc(Long paidToId, Integer year, Pageable pageable);

    Page<Commission> findAllByApprovedByIdAndMonthAndYearOrderByDateOfOrderAsc(Long approvedById, Integer month, Integer year, Pageable pageable);

    @Query(value = "SELECT * FROM commissions c WHERE c.month = :month AND c.year = :year AND (SELECT company_id FROM users WHERE id = c.paid_to_id) = :companyId ORDER BY c.date_of_order ASC", nativeQuery =  true)
    Page<Commission> findAllByCompanyMonthAndYear(@Param("month") Integer month,
                                             @Param("year") Integer year,
                                             @Param("companyId") Long companyId,
                                             Pageable pageable);

    @Query(value = "SELECT * FROM commissions c WHERE c.year = :year AND (SELECT company_id FROM users WHERE id = c.paid_to_id) = :companyId ORDER BY c.date_of_order ASC", nativeQuery =  true)
    Page<Commission> findAllByCompanyAndYear(@Param("year") Integer year,
                                        @Param("companyId") Long companyId,
                                        Pageable pageable);

    Commission getFirstByPaidToIdAndYearAndTypeOfCommissionId(Long paidToId, Integer year, Long typeId);

}
