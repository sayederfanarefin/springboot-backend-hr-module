package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Tender;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;
import com.sweetitech.hrm.domain.dto.TenderResponseDTO;

import java.util.Date;
import java.util.List;

public interface TenderService {

    TenderResponseDTO create(Tender tender) throws Exception;

    Tender read(Long id) throws Exception;

    TenderResponseDTO readDTO(Long id) throws Exception;

    TenderResponseDTO update(Long id, Tender tender) throws Exception;

    TenderResponseDTO updateStatus(Long id, String status) throws Exception;

    TenderResponseDTO updateTenderSecurityStatus(Long id, String status) throws Exception;

    TenderResponseDTO updateTenderStatus(Long id, String status) throws Exception;

    void remove(Long id) throws Exception;

    List<TenderResponseDTO> readAll() throws Exception;

    List<TenderResponseDTO> readAllByUser(Long userId) throws Exception;

    List<TenderResponseDTO> search(String institutionName, String eTenderId, String mrCode) throws Exception;

    List<TenderResponseDTO> readAllNearingExpiry(Date date) throws Exception;

    TaskResponseDTO createTaskForTender(Long tenderId,
                                        Long assignedToUserId,
                                        Long assignedByUserId) throws  Exception;

}
