package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Log;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

public interface LogRepository extends CrudRepository<Log, Long> {

    Log getFirstById(Long id);

    List<Log> findAllByIndRegIdAndMachineNumberAndDateTimeRecordGreaterThanAndDateTimeRecordLessThan(Long indRegId, Long machineNumber, String fromDate, String toDate);

    @Query(value = "SELECT * FROM logs l WHERE l.ind_reg_id = :indRegId AND l.machine_number = :machineNumber AND l.date_time_record LIKE CONCAT('%', :fromDate, '%') ORDER BY l.date_time_record ASC", nativeQuery = true)
    List<Log> findAllByEnrolAndMachineNumberAndDate(@Param("indRegId") Long indRegId,
                                                    @Param("machineNumber") Long machineNumber,
                                                    @Param("fromDate") String fromDate) throws Exception;

    @Query(value = "SELECT * FROM logs l WHERE l.ind_reg_id = :indRegId AND l.machine_number = :machineNumber AND l.date_time_record LIKE CONCAT('%', :fromDate, '%') LIMIT 1", nativeQuery = true)
    Log getFirstByEnrolAndMachineNumberAndDate(@Param("indRegId") Long indRegId,
                                               @Param("machineNumber") Long machineNumber,
                                               @Param("fromDate") String fromDate) throws Exception;

}
