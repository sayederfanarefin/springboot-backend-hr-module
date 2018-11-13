package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.OfficeHour;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfficeHourRepository extends CrudRepository<OfficeHour, Long> {

    List<OfficeHour> findAllByOrderByDayNumberAsc();

    OfficeHour getFirstById(Long id);

    OfficeHour getFirstByDayNumber(Integer number);

    List<OfficeHour> findAllByTypeOfDay(String typeOfDay);

}
