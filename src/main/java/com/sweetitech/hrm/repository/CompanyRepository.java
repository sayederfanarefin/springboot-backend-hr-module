package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company, Long> {

    Company findById(long id);

    Page<Company> findAllByOrderByNameAsc(Pageable pageable);

    List<Company> findAllByOrderByNameAsc();

    Page<Company> findAllByIsDeletedFalseOrderByNameAsc(Pageable pageable);

    List<Company> findAllByIsDeletedFalseOrderByNameAsc();

    Page<Company> findAllByIdAndIsDeletedFalseOrderByNameAsc(Long id, Pageable pageable);

    List<Company> findAllByIdAndIsDeletedFalseOrderByNameAsc(Long id);

}
