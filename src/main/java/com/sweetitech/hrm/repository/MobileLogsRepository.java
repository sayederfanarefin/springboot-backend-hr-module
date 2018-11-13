package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.MobileLogs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MobileLogsRepository extends CrudRepository<MobileLogs, Long> {

    MobileLogs getFirstByLogId(Long id);

    Page<MobileLogs> findAllByUserIdOrderByTimestampDesc(Long userId, Pageable pageable);

    List<MobileLogs> findAllByUserIdAndTimestampBetweenOrderByTimestampAsc(Long userId, Long fromTime, Long toTime);

    MobileLogs getFirstByUserIdAndTimestampBetweenOrderByTimestampAsc(Long userId, Long fromTime, Long toTime);

}
