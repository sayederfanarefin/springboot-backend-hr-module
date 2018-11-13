package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Tender;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TenderRepository extends CrudRepository<Tender, Long> {

    Tender getFirstById(Long id);

    List<Tender> findAllByOrderByLastUpdatedAsc();

    List<Tender> findAllByCreatedByIdOrderByLastUpdatedDesc(Long userId);

    // 1 1 1
    List<Tender> findAllByInstitutionNameContainingAndETenderIdContainingAndMrCodeContainingOrderByLastUpdatedAsc(String institutionName, String eTenderId, String mrCode);

    // 1 1 0
    List<Tender> findAllByInstitutionNameContainingAndETenderIdContainingOrderByLastUpdatedAsc(String institutionName, String eTenderId);

    // 1 0 1
    List<Tender> findAllByInstitutionNameContainingAndMrCodeContainingOrderByLastUpdatedAsc(String institutionName, String mrCode);

    // 0 1 1
    List<Tender> findAllByETenderIdContainingAndMrCodeContainingOrderByLastUpdatedAsc(String eTenderId, String mrCode);

    // 1 0 0
    List<Tender> findAllByInstitutionNameContainingOrderByLastUpdatedAsc(String institutionName);

    // 0 1 0
    List<Tender> findAllByETenderIdContainingOrderByLastUpdatedAsc(String eTenderId);

    // 0 0 1
    List<Tender> findAllByMrCodeContainingOrderByLastUpdatedAsc(String mrCode);

}
