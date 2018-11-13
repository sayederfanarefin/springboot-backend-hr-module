package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.OfficeCode;

import java.util.List;

public interface OfficeCodeService {

    OfficeCode create(OfficeCode officeCode) throws Exception;

    OfficeCode update(Long id, OfficeCode officeCode) throws Exception;

    void remove(Long id) throws Exception;

    OfficeCode read(Long id) throws Exception;

    OfficeCode readById(Long id) throws Exception;

    OfficeCode readByCode(String code) throws Exception;

    boolean exists(Long companyId, String code) throws Exception;

    List<OfficeCode> readAll();

    List<OfficeCode> readByCompanyId(Long companyId) throws Exception;

}
