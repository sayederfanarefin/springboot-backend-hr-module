package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Leave;
import com.sweetitech.hrm.domain.LeaveStatus;
import com.sweetitech.hrm.domain.dto.LeaveDTO;
import com.sweetitech.hrm.domain.dto.LeaveMonthlySummaryDTO;
import com.sweetitech.hrm.domain.dto.LeaveResponseDTO;
import com.sweetitech.hrm.service.implementation.LeaveServiceImpl;
import com.sweetitech.hrm.service.mapping.LeaveCreateMapper;
import com.sweetitech.hrm.service.mapping.LeaveResponseMapper;
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
import java.util.List;

import static com.sweetitech.hrm.report.service.PDFService.generateMonthlyLeaveByCompanyReport;
import static com.sweetitech.hrm.report.service.PDFService.generateYearlyEarnedLeaveByCompanyReport;
import static com.sweetitech.hrm.report.service.PDFService.generateYearlyLeaveByCompanyReport;

@RestController
@RequestMapping("/admin/leaves")
public class AdminLeaveController {

    private LeaveServiceImpl leaveService;

    @Autowired
    private LeaveCreateMapper leaveCreateMapper;

    @Autowired
    private LeaveResponseMapper leaveResponseMapper;

    @Autowired
    public AdminLeaveController(LeaveServiceImpl leaveService) {
        this.leaveService = leaveService;
    }

    // Update Leave
    @RequestMapping(value = "/{leaveId}", method = RequestMethod.PUT)
    public LeaveResponseDTO updateLeave(@PathVariable Long leaveId,
                                        @RequestBody LeaveDTO dto) throws Exception {
        if(leaveId == null) {
            throw new EntityNotFoundException("Null leave id found!");
        }
        if (dto == null) {
            throw new EntityNotFoundException("No leave details found!");
        }

        if (leaveService.readStatus(leaveId).equals(Constants.RequestStatus.FORWARDED)) {
            throw new EntityNotFoundException("Admin cannot edit leave now! Status has been changed!");
        }

        return leaveResponseMapper.map(leaveService.update(leaveId, leaveCreateMapper.map(dto)));
    }

    // Update Status Excluding Forwarded
    @RequestMapping(value = "/status/{leaveId}", method = RequestMethod.PUT)
    public LeaveResponseDTO updateStatus(@PathVariable Long leaveId,
                                         @RequestParam Long userId,
                                         @RequestParam String status,
                                         @RequestParam String reason) throws Exception {
        if(leaveId == null) {
            throw new EntityNotFoundException("Null leave id found!");
        }
        if(userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        if (leaveService.readStatus(leaveId).equals(Constants.RequestStatus.FORWARDED)) {
            throw new EntityNotFoundException("Admin cannot update forwarded leave request!");
        }

        LeaveStatus leaveStatus = leaveService.prepareStatus(status, userId, reason);

        return leaveResponseMapper.map(leaveService.updateStatus(leaveId, leaveStatus));
    }

    // Get All By Status And Company
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public Iterable<Leave> getAllByStatusAndCompany(@PathVariable Long companyId,
                                                 @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "status", defaultValue = Constants.RequestStatus.REQUESTED) String status) throws Exception {
        if(companyId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }
        if (status == null) {
            throw new EntityNotFoundException("Null status found!");
        }

        return leaveService.readAllByStatusAndCompany(companyId, status, page);
    }

    // Get all users who took leave for month and year
    @RequestMapping(value = "/company/{companyId}/month", method = RequestMethod.GET)
    public List<LeaveMonthlySummaryDTO> getAllByMonthAndCompany(@PathVariable Long companyId,
                                                                @RequestParam Integer month,
                                                                @RequestParam Integer year) throws Exception {
        if(companyId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return leaveService.getMonthlyLeaveSummaryForCompany(companyId, year, month);
    }

    // Get all users who took leave in month and year for company, department and office code
    @RequestMapping(value = "/company/month", method = RequestMethod.GET)
    public List<LeaveMonthlySummaryDTO> getAllByMonthAndCompany(@RequestParam Long companyId,
                                                                @RequestParam Long departmentId,
                                                                @RequestParam Long officeCodeId,
                                                                @RequestParam Integer month,
                                                                @RequestParam Integer year) throws Exception {
        if(companyId == null || departmentId == null || officeCodeId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return leaveService.getMonthlyLeaveSummaryForCompany(companyId, departmentId, officeCodeId, month, year);
    }

    // Download monthly summary of company
    @RequestMapping(value = "/report/summary/{companyId}/month", method = RequestMethod.GET)
    public ResponseEntity<Resource> getReportByCompanyAndMonth(@PathVariable Long companyId,
                                                               @RequestParam Long departmentId,
                                                               @RequestParam Long officeCodeId,
                                                               @RequestParam Integer month,
                                                               @RequestParam Integer year) throws Exception {
        if (companyId == null || departmentId == null || officeCodeId == null || month == null || year ==  null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath = generateMonthlyLeaveByCompanyReport("/monthlyLeaveByCompany",
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
        headers.add("Content-Disposition", "attachment; filename=\"report-monthlyLeaveByCompany" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);

    }

    // Download yearly summary of company
    @RequestMapping(value = "/report/summary/{companyId}/year", method = RequestMethod.GET)
    public ResponseEntity<Resource> getReportByCompanyAndYear(@PathVariable Long companyId,
                                                              @RequestParam Long departmentId,
                                                              @RequestParam Long officeCodeId,
                                                              @RequestParam Integer year) throws Exception {
        if (companyId == null || departmentId == null || officeCodeId == null || year ==  null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath = generateYearlyLeaveByCompanyReport("/yearlyLeaveByCompany",
                companyId, departmentId, officeCodeId, year);
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
        headers.add("Content-Disposition", "attachment; filename=\"report-yearlyLeaveByCompany" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);

    }

    // Download yearly earned leave summary of company
    @RequestMapping(value = "/report/earned/{companyId}/year", method = RequestMethod.GET)
    public ResponseEntity<Resource> getEarnedLeaveReportByCompanyAndYear(@PathVariable Long companyId,
                                                                         @RequestParam Long departmentId,
                                                                         @RequestParam Long officeCodeId,
                                                                         @RequestParam Integer year) throws Exception {
        if (companyId == null || departmentId == null || officeCodeId == null || year ==  null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath = generateYearlyEarnedLeaveByCompanyReport("/yearlyEarnedLeaveByCompany",
                companyId, departmentId, officeCodeId, year);
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
        headers.add("Content-Disposition", "attachment; filename=\"report-yearlyEarnedLeaveByCompany" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);

    }
}
