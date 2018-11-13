package com.sweetitech.hrm.service;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.domain.Allowance;
import com.sweetitech.hrm.domain.AllowanceSummary;
import com.sweetitech.hrm.domain.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AllowanceService {

    Allowance create(Allowance allowance) throws Exception;

    Allowance update(Long id, Allowance allowance) throws Exception;

    Allowance read(Long id);

    String readStatus(Long id) throws Exception;

    void cancel(Long id) throws Exception;

    Allowance updateStatus(Long id, Long userId, String status) throws Exception;

    Page<Allowance> readAllRequested(Long companyId, String status, Integer page) throws Exception;

    Page<Allowance> readAllRequestedByUserId(Long userId, String status, Integer page) throws Exception;

    Page<Allowance> readAllByUserId(Long userId, Integer page) throws Exception;

    Page<Allowance> readAllDecided(Long companyId, String status1, String status2, Integer page) throws Exception;

    List<AllowanceListObjectDTO> readForMonthByUserId(Long userId, Integer month, Integer year) throws Exception;

    Page<Allowance> readAllByStatusAndCompany(Long companyId, String status, Integer page) throws Exception;

    Page<Allowance> readAllByStatusAndUser(Long userId, String status, Integer page) throws Exception;

    AllowanceSummary approveSummary(AllowanceSummary allowanceSummary) throws Exception;

    AllowanceSummary updateSummary(Long id, AllowanceSummary allowanceSummary) throws Exception;

    AllowanceSummary getSummary(Integer month, Integer year, Long userId) throws Exception;

    Allowance confirmBySummary(Long allowanceId, Long summaryId) throws Exception;

    AllowanceMonthlyDTO getMonthlyAllowanceSummaryByUser(Long userId, Integer month, Integer year) throws Exception;

    List<AllowanceListObjectDTO> bulkRequestAllowances(AllowanceListDTO allowances) throws Exception;

    List<UserSmallResponseDTO> listUsersByAllowanceRequestsForMonth(Long companyId,
                                                                    Long departmentId,
                                                                    Long officeCodeId,
                                                                    Integer month,
                                                                    Integer year) throws Exception;

    Allowance checkIfExistsForUserAndDate(Long userId,
                                          Integer day,
                                          Integer month,
                                          Integer year) throws Exception;

    Allowance updateDestination(Long id, String destination) throws Exception;

}
