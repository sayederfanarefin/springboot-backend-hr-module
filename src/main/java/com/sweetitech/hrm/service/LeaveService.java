package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Leave;
import com.sweetitech.hrm.domain.LeaveStatus;
import com.sweetitech.hrm.domain.dto.EarnedLeaveDTO;
import com.sweetitech.hrm.domain.dto.LeaveMonthlySummaryDTO;
import com.sweetitech.hrm.domain.dto.LeaveSummaryDTO;
import com.sweetitech.hrm.domain.dto.LeaveUserSummaryDTO;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface LeaveService {

    Leave read(Long id) throws Exception;

    Leave create(Leave leave) throws Exception;

    Leave update(Long id, Leave leave) throws Exception;

    Leave updateStatus(Long id, LeaveStatus status) throws Exception;

    String readStatus(Long id) throws Exception;

    Page<Leave> readAllByStatusAndUser(Long userId, String status, Integer page) throws Exception;

    Page<Leave> readAllByStatusAndCompany(Long companyId, String status, Integer page) throws Exception;

    List<Leave> readAllMonthlyApprovedByUser(Long userId, String status, Integer year, Integer month) throws Exception;

    List<LeaveSummaryDTO> getAllMonthlyLeaveSummary(Long userId, Integer year, Integer month) throws Exception;

    Integer getNumberForMonthAndYear(Long userId, String status, Integer year, Integer month) throws Exception;

    List<Leave> readAllByYearAndUser(Long userId, Integer year) throws Exception;

    Integer getLeaveCount(Date fromDate, Date toDate, Long companyId) throws Exception;

    List<LeaveMonthlySummaryDTO> getMonthlyLeaveSummaryForCompany(Long companyId, Integer year, Integer month) throws Exception;

    List<LeaveUserSummaryDTO> getYearlySummaryByCompany(Long companyId,
                                                        Long departmentId,
                                                        Long officeCodeId,
                                                        Integer year) throws Exception;

    List<LeaveMonthlySummaryDTO> getMonthlyLeaveSummaryForCompany(Long companyId,
                                                                  Long departmentId,
                                                                  Long officeCodeId,
                                                                  Integer month,
                                                                  Integer year) throws Exception;

    List<EarnedLeaveDTO> getYearlyEarnedLeaveSummaryByCompany(Long companyId,
                                                              Long departmentId,
                                                              Long officeCodeId,
                                                              Integer year) throws Exception;

}
