package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.MobileLogs;
import com.sweetitech.hrm.domain.dto.MobileLogsDTO;
import com.sweetitech.hrm.domain.dto.MobileLogsResponseDTO;
import com.sweetitech.hrm.domain.dto.page.MobileLogsPage;

import java.util.Date;
import java.util.List;

public interface MobileLogsService {

    MobileLogsResponseDTO create(MobileLogs mobileLogs) throws Exception;

    MobileLogsResponseDTO readDTO(Long id) throws Exception;

    MobileLogs read(Long id) throws Exception;

    MobileLogsPage readAllByUser(Long userId, Integer page) throws Exception;

    List<MobileLogsResponseDTO> bulkAdd(List<MobileLogsDTO> mobileLogs, Long userId) throws Exception;

    List<MobileLogsResponseDTO> readAllByUserAndDate(Long userId, Date date) throws Exception;

}
