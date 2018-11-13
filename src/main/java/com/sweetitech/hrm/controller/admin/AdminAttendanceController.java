package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.page.AttendanceMonthlySummaryPage;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.domain.Log;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.service.implementation.LogServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import com.sweetitech.hrm.service.mapping.AttendanceResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.sweetitech.hrm.report.service.PDFService.*;

@RestController
@RequestMapping("/admin/attendances")
public class AdminAttendanceController {

    private LogServiceImpl logService;

    @Autowired
    private AttendanceResponseMapper attendanceResponseMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    public AdminAttendanceController(LogServiceImpl logService) {
        this.logService = logService;
    }

    // Get all by user id for a day
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public List<AttendanceResponseDTO> getByUserAndDate(@PathVariable Long userId,
                                                        @RequestBody LeaveCountDTO dto) throws Exception {
        if (userId == null || dto == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        List<AttendanceResponseDTO> dtos = new ArrayList<>();
        List<Log> logs = logService.readAllByUserAndDay(userId, dto.getFromDate());

        for (Log log: logs) {
            dtos.add(attendanceResponseMapper.map(log));
        }

        return dtos;
    }

    // Get summary of day by user id and date
    @RequestMapping(value = "/summary/{userId}", method = RequestMethod.POST)
    public AttendanceDayTrackDTO getSummaryByUserAndDate(@PathVariable Long userId,
                                                         @RequestBody LeaveCountDTO dto) throws Exception {
        if (userId == null || dto == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return logService.countLateAndEarly(userId, dto.getFromDate());
    }

    // Get summary of a month by user id
    @RequestMapping(value = "/summary/{userId}/month", method = RequestMethod.GET)
    public List<AttendanceDayTrackDTO> getSummaryByUserAndMonth(@PathVariable Long userId,
                                                                @RequestParam Integer month,
                                                                @RequestParam Integer year) throws Exception {
        if (userId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return logService.readAllByUserAndMonth(userId, month, year);
    }

    // Download monthly summary of user
    @RequestMapping(value = "/report/summary/{userId}/month", method = RequestMethod.GET)
    public ResponseEntity<Resource> getReportByUserAndMonth(@PathVariable Long userId,
                                                            @RequestParam Integer month,
                                                            @RequestParam Integer year) throws Exception {
        String downloadFilePath = generateMonthlyAttendanceByUserReport("/monthlyAttendanceByUser",
                userId, month, year);
        System.out.println("DL File Path: "+downloadFilePath);

        if (downloadFilePath == null)
            throw new NullPointerException("data missing");

        if (downloadFilePath == null)
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body(null);

        File file = new File(downloadFilePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"report-monthlyAttendanceByUser" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);

    }

    // Get all users with late attendance by company and date
    @RequestMapping(value = "/summary/company/{companyId}", method = RequestMethod.POST)
    public List<AttendanceDayTrackDTO> getAllLateUsersByDate(@PathVariable Long companyId,
                                                             @RequestBody LeaveCountDTO dto) throws Exception {
        if (companyId == null || dto == null) {
            throw new EntityNotFoundException("Null value found!");
        }
        if (!DateTimeUtils.isValidDate(dto.getFromDate().toString(), "E MMM dd HH:mm:ss z yyyy")) {
            throw new EntityNotFoundException("Invalid date found!");
        }

        return logService.getAllLateUsersByDate(companyId, dto.getFromDate());
    }

    // Get all absent users by company and date
    @RequestMapping(value = "/summary/company/absent/{companyId}", method = RequestMethod.POST)
    public List<UserSmallResponseDTO> getAllAbsentUsersByDate(@PathVariable Long companyId,
                                                              @RequestBody LeaveCountDTO dto) throws Exception {
        if (companyId == null || dto == null) {
            throw new EntityNotFoundException("Null value found!");
        }
        if (!DateTimeUtils.isValidDate(dto.getFromDate().toString(), "E MMM dd HH:mm:ss z yyyy")) {
            throw new EntityNotFoundException("Invalid date found!");
        }

        return logService.getAllAbsentUsersByDate(companyId, dto.getFromDate());
    }

    // Get attendance summary by month for all employees in a company
    @RequestMapping(value = "/summary/company/{companyId}/month", method = RequestMethod.GET)
    public List<AttendanceMonthlySummaryDTO> getMonthlySummaryByCompany(@PathVariable Long companyId,
                                                                        @RequestParam Integer month,
                                                                        @RequestParam Integer year) throws Exception {
        if (companyId == null  || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return logService.summaryByCompanyAndMonth(companyId, month, year);
    }

    // Get attendance summary by month for paginated employees in a company
    @RequestMapping(value = "/summary/company/{companyId}/month/page", method = RequestMethod.GET)
    public AttendanceMonthlySummaryPage getPagedMonthlySummaryByCompany(@PathVariable Long companyId,
                                                                        @RequestParam Integer month,
                                                                        @RequestParam Integer year,
                                                                        @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null  || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return logService.paginatedSummaryByCompanyAndMonth(companyId, month, year, page);
    }

    // Get attendance summary by month for paginated employees in a company
    @RequestMapping(value = "/summary/company/{companyId}/month/page", method = RequestMethod.POST)
    public AttendanceMonthlySummaryPage getPagedMonthlySummaryByCompany(@PathVariable Long companyId,
                                                                        @RequestParam Integer month,
                                                                        @RequestParam Integer year,
                                                                        @RequestBody SingleStringObjectDTO dto,
                                                                        @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null  || month == null || year == null || (dto != null && dto.getObject() == null)) {
            throw new EntityNotFoundException("Null values found!");
        }

        return logService.paginatedSummaryByCompanyAndMonth(companyId, month, year, dto.getObject(), page);
    }

    @RequestMapping(value = "/test/{companyId}", method = RequestMethod.GET)
    public List<AttendanceMonthlySummaryDTO> test(@PathVariable Long companyId,
                                                  @RequestParam Integer month,
                                                  @RequestParam Integer year) throws Exception {
        if (companyId == null  || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return logService.test(companyId, month, year);
    }

    // Get attendance summary by month for a user
    @RequestMapping(value = "/summary/user/{userId}/month", method = RequestMethod.GET)
    public AttendanceMonthlySummaryDTO getMonthlySummaryForUser(@PathVariable Long userId,
                                                                @RequestParam Integer month,
                                                                @RequestParam Integer year) throws Exception {
        if (userId == null  || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        return logService.generateMonthlySummaryForUser(user, month, year);
    }

    // Daily attendance by user report
    @RequestMapping(value = "/report/{userId}", method = RequestMethod.POST)
    public ResponseEntity<Resource> getReportByUserAndDate(@PathVariable Long userId,
                                                           @RequestBody LeaveCountDTO dto) throws Exception {
        String downloadFilePath = generateDailyAttendanceByUserReport("/dailyAttendanceByUser",
                userId, dto);
        System.out.println("DL File Path: "+downloadFilePath);

        if (downloadFilePath == null)
            throw new NullPointerException("data missing");

        if (downloadFilePath == null)
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body(null);

        File file = new File(downloadFilePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"report-dailyAttendanceByUser" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);

    }

    // Get attendance report by month for all employees in a company
    @RequestMapping(value = "/report/company/{companyId}/month", method = RequestMethod.GET)
    public ResponseEntity<Resource> getMonthlyReportByCompanyAndDepartment(@PathVariable Long companyId,
                                                                           @RequestParam Long departmentId,
                                                                           @RequestParam Integer month,
                                                                           @RequestParam Integer year) throws Exception {
        if (companyId == null || departmentId == null  || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath =
                generateMonthlyAttendanceByCompanyAndDepartmentReport("/monthlyAttendanceByCompanyAndDepartment",
                companyId, departmentId, month, year);
        System.out.println("DL File Path: "+downloadFilePath);

        if (downloadFilePath == null)
            throw new NullPointerException("data missing");

        if (downloadFilePath == null)
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body(null);

        File file = new File(downloadFilePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"report-dailyAttendanceByUser" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    // Get daily summary by company id
    @RequestMapping(value = "/summary/{companyId}/day", method = RequestMethod.POST)
    public AttendanceDailySummaryDTO getDailySummary(@PathVariable Long companyId,
                                                     @RequestParam Long departmentId,
                                                     @RequestParam Long officeCodeId,
                                                     @RequestBody LeaveCountDTO dto) throws Exception {
        if (companyId == null  || dto == null || dto.getFromDate() == null || departmentId == null || officeCodeId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return logService.getAttendanceSummaryByDate(companyId, departmentId, officeCodeId, dto.getFromDate());
    }

    // Download daily company summary
    @RequestMapping(value = "report/summary/day", method = RequestMethod.POST)
    public ResponseEntity<Resource> getDailyAttendanceReport(@RequestParam Long companyId,
                                                             @RequestParam Long departmentId,
                                                             @RequestParam Long officeCodeId,
                                                             @RequestBody LeaveCountDTO dto) throws Exception {
        if (companyId == null || departmentId == null  || officeCodeId == null || dto == null || dto.getFromDate() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath =
                generateDailyAttendanceByCompanyReport("/dailyAttendanceSummary",
                        companyId, departmentId, officeCodeId, dto);
        System.out.println("DL File Path: "+downloadFilePath);

        if (downloadFilePath == null)
            throw new NullPointerException("data missing");

        if (downloadFilePath == null)
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body(null);

        File file = new File(downloadFilePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"report-dailyAttendanceSummary" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    // Get attendance report by month for all employees filtered by company, department and office code
    @RequestMapping(value = "/report/summary/month", method = RequestMethod.GET)
    public ResponseEntity<Resource> getMonthlyAttendanceReport(@RequestParam Long companyId,
                                                               @RequestParam Long departmentId,
                                                               @RequestParam Long officeCodeId,
                                                               @RequestParam Integer month,
                                                               @RequestParam Integer year) throws Exception {
        if (companyId == null || departmentId == null  || officeCodeId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath =
                generateMonthlyAttendance("/monthlyAttendanceSummary",
                        companyId, departmentId, officeCodeId, month, year);
        System.out.println("DL File Path: "+downloadFilePath);

        if (downloadFilePath == null)
            throw new NullPointerException("data missing");

        if (downloadFilePath == null)
            return ResponseEntity.badRequest()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .body(null);

        File file = new File(downloadFilePath);

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"report-monthlyAttendanceSummary" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    // Check if user is absent for date
    @RequestMapping(value = "/absent/{userId}", method = RequestMethod.POST)
    public boolean checkAbsent(@PathVariable Long userId,
                               @RequestBody LeaveCountDTO dto) throws Exception {
        return logService.checkAbsent(userId, dto.getFromDate());
    }
}
