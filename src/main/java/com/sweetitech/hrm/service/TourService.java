package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Tour;
import com.sweetitech.hrm.domain.dto.TourDTO;
import com.sweetitech.hrm.domain.dto.TourResponseDTO;

import java.util.List;

public interface TourService {

    Tour create(Tour tour) throws Exception;

    TourResponseDTO create(TourDTO dto) throws Exception;

    Tour update(Long id, Tour tour) throws Exception;

    TourResponseDTO update(Long id, TourDTO dto, boolean fromRequest) throws Exception;

    Tour read(Long id) throws Exception;

    TourResponseDTO readDTO(Long id) throws Exception;

    TourResponseDTO updateStatus(Long id, Long userId, String status) throws Exception;

    List<TourResponseDTO> readByCompanyAndStatusAndMonth(Long companyId, String status, Integer month, Integer year) throws Exception;

    List<TourResponseDTO> readForUser(Long userId, Integer year) throws Exception;

    void cancel(Long id) throws Exception;

    String checkStatus(Long id) throws Exception;

    List<TourResponseDTO> readByUserAndStatusAndMonth(Long userId, String status, Integer month, Integer year) throws Exception;

    List<TourResponseDTO> readByUserAndStatus(Long userId, String status) throws Exception;

}
