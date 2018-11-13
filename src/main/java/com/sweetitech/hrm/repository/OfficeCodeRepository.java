package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.OfficeCode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfficeCodeRepository extends CrudRepository<OfficeCode, Long> {

    OfficeCode getFirstById(Long id);

    OfficeCode readByCodeLike(String code);

    OfficeCode getFirstByCode(String code);

    OfficeCode getFirstByCodeAndCompanyId(String code, Long companyId);

    List<OfficeCode> getAllByIsDeletedFalseOrderByCodeAsc();

    List<OfficeCode> findAllByCompanyIdAndIsDeletedFalseOrderByCodeAsc(Long companyId);

}
