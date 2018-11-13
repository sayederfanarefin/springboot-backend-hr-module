package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {

    Car getFirstById(Long id);

    Car getFirstByModel(String model);

    Car getFirstByRegNo(String regNo);

    Page<Car> findAllByCompanyIdAndIsDeletedFalseOrderByModelAsc(Long companyId, Pageable pageable);

    List<Car> findAllByCompanyIdAndIsDeletedFalseOrderByModelAsc(Long companyId);

    Page<Car> findAllByCompanyIdAndIsDeletedTrueOrderByModelAsc(Long companyId, Pageable pageable);

}
