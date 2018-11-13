package com.sweetitech.hrm.service;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Company;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompanyService {

    Company read(long id);

    Company create(Company company);

    Page<Company> readAll(Integer page);

    List<Company> readAll();

    Page<Company> readAllActive(Long userId, Integer page) throws Exception;

    List<Company> readAllActive(Long userId) throws Exception;

    Company update(long id, Company company) throws EntityNotFoundException;

    void removeActiveState(long id) throws EntityNotFoundException;

    List<Company> readAllActiveByRole(Long userId) throws Exception;

}
