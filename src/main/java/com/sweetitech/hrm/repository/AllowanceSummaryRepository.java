package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.AllowanceSummary;
import org.springframework.data.repository.CrudRepository;

public interface AllowanceSummaryRepository extends CrudRepository<AllowanceSummary, Long> {

    AllowanceSummary getFirstById(Long id);

    AllowanceSummary getFirstByMonthAndYearAndPreparedForId(Integer month, Integer year, Long id);

}
