package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.dto.page.AttendanceMonthlySummaryPage;
import com.sweetitech.hrm.domain.Log;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.*;

import java.util.Date;
import java.util.List;

public interface LogService {

    Log read(Long id) throws Exception;

    List<Log> readAllByUserDeviceAndDay(String enrolNumber, String machineNumber, Date date) throws Exception;

    Log readByUserDeviceAndDay(Long userId, String enrolNumber, String machineNumber, Date date) throws Exception;

    List<Log> readAllByUserAndDay(Long userId, Date date) throws Exception;

    AttendanceDayTrackDTO countLateAndEarly(Long userId, Date date) throws Exception;

    boolean checkAbsent(Long userId, Date date) throws Exception;

    AttendanceMonthlySummaryDTO summaryByUserAndMonth(User user, Integer month, Integer year,
                                                      List<LeaveSummaryDTO> leaves, List<TaskResponseDTO> holidays,
                                                      List<OfficeHourDateDTO> weekends) throws Exception;

    List<AttendanceMonthlySummaryDTO> summaryByCompanyAndMonth(Long companyId, Integer month, Integer year) throws Exception;

    List<AttendanceMonthlySummaryDTO> summaryByCompanyDepartmentAndMonth(Long companyId,
                                                                         Long departmentId,
                                                                         Integer month,
                                                                         Integer year) throws Exception;

    List<AttendanceMonthlySummaryDTO> summaryByCompanyDepartmentOfficeCodeAndMonth(Long companyId,
                                                                                   Long departmentId,
                                                                                   Long officeCodeId,
                                                                                   Integer month,
                                                                                   Integer year) throws Exception;

    AttendanceMonthlySummaryDTO generateMonthlySummaryForUser(User user, Integer month, Integer year) throws Exception;

    AttendanceMonthlySummaryPage paginatedSummaryByCompanyAndMonth(Long companyId,
                                                                   Integer month,
                                                                   Integer year,
                                                                   Integer page) throws Exception;

    AttendanceMonthlySummaryPage paginatedSummaryByCompanyAndMonth(Long companyId,
                                                                   Integer month,
                                                                   Integer year,
                                                                   String name,
                                                                   Integer page) throws Exception;

    List<AttendanceDayTrackDTO> readAllByUserAndMonth(Long userId, Integer month, Integer year) throws Exception;

    List<AttendanceDayTrackDTO> getAllLateUsersByDate(Long companyId, Date fromDate) throws Exception;

    List<UserSmallResponseDTO> getAllAbsentUsersByDate(Long companyId, Date fromDate) throws Exception;

    AttendanceDailySummaryDTO getAttendanceSummaryByDate(Long companyId, Long departmentId, Long officeCodeId, Date fromDate) throws Exception;

}
