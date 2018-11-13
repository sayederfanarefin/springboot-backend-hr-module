package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Allowance;
import com.sweetitech.hrm.domain.AllowanceSummary;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.service.implementation.AllowanceServiceImpl;
import com.sweetitech.hrm.service.implementation.HolidayServiceImpl;
import com.sweetitech.hrm.service.implementation.LeaveServiceImpl;
import com.sweetitech.hrm.service.implementation.OfficeHourServiceImpl;
import com.sweetitech.hrm.service.mapping.AllowanceEditMapper;
import com.sweetitech.hrm.service.mapping.AllowanceListObjectMapper;
import com.sweetitech.hrm.service.mapping.AllowanceSummaryMapper;
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

import static com.sweetitech.hrm.report.service.PDFService.generateMonthlyAllowanceByUserReport;

@RestController
@RequestMapping("/admin/allowances")
public class AdminAllowanceController {

    private AllowanceServiceImpl allowanceService;
    private OfficeHourServiceImpl officeHourService;
    private LeaveServiceImpl leaveService;
    private HolidayServiceImpl holidayService;

    @Autowired
    private AllowanceEditMapper allowanceEditMapper;

    @Autowired
    private AllowanceSummaryMapper allowanceSummaryMapper;

    @Autowired
    public AdminAllowanceController(AllowanceServiceImpl allowanceService,
                                    OfficeHourServiceImpl officeHourService,
                                    LeaveServiceImpl leaveService,
                                    HolidayServiceImpl holidayService) {
        this.allowanceService = allowanceService;
        this.officeHourService = officeHourService;
        this.leaveService = leaveService;
        this.holidayService = holidayService;
    }

    // Edit Requested Allowance
    @RequestMapping(value = "/{allowanceId}/allowance", method = RequestMethod.PUT)
    public Allowance editNotForwardedAllowance(@PathVariable Long allowanceId,
                                            @RequestBody AllowanceDTO dto) throws Exception {
        if (allowanceId == null) {
            throw new EntityNotFoundException("Null allowance id found!");
        }

        if (dto == null) {
            throw new EntityNotFoundException("Null data found!");
        }

        if (allowanceService.readStatus(allowanceId).equals(Constants.RequestStatus.FORWARDED)) {
            throw new EntityNotFoundException("Allowance cannot be edited now!");
        }

        // dto.setStatus(Constants.RequestStatus.REQUESTED);

        return allowanceService.update(allowanceId, allowanceEditMapper.map(dto));
    }

    // Update Status
    @RequestMapping(value = "/{allowanceId}/status", method = RequestMethod.PUT)
    public Allowance updateStatus(@PathVariable Long allowanceId, @RequestParam Long userId, @RequestParam String status) throws Exception {
        if (allowanceId == null || userId == null || status == null) {
            throw new EntityNotFoundException("Null value received!");
        }

        return allowanceService.updateStatus(allowanceId, userId, status);
    }

    // Get All Requested By Company
    @RequestMapping(value = "/requested/{companyId}", method = RequestMethod.GET)
    public Iterable<Allowance> getAllRequestedByCompany(@PathVariable Long companyId,
                                                        @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if(companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return allowanceService.readAllRequested(companyId, Constants.RequestStatus.REQUESTED, page);
    }

    // Get All by Status and Company Id
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public Iterable<Allowance> getAllByStatusAndCompany(@PathVariable Long companyId,
                                                        @RequestParam(value = "status", defaultValue = "requested") String status,
                                                        @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if(companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        if (status.equals(Constants.RequestStatus.FORWARDED) || status.equals(Constants.RequestStatus.CANCELLED)) {
            throw new EntityNotFoundException("Access Denied!");
        }

        return allowanceService.readAllByStatusAndCompany(companyId, status, page);
    }

    // Get All Requested by User
    @RequestMapping(value = "/requested/user/{userId}", method = RequestMethod.GET)
    public Iterable<Allowance> getAllRequestedByUser(@PathVariable Long userId,
                                                        @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if(userId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return allowanceService.readAllRequestedByUserId(userId, Constants.RequestStatus.REQUESTED, page);
    }

    // Get All By Status and User id
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public Iterable<Allowance> getAllByStatusAndUser(@PathVariable Long userId,
                                                     @RequestParam(value = "status", defaultValue = "requested") String status,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if(userId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        if (status.equals(Constants.RequestStatus.FORWARDED) || status.equals(Constants.RequestStatus.CANCELLED)) {
            throw new EntityNotFoundException("Access Denied!");
        }

        return allowanceService.readAllByStatusAndUser(userId, status, page);
    }

    // Get All Approved or Declined by Company
    @RequestMapping(value = "/decided/company/{companyId}", method = RequestMethod.GET)
    public Iterable<Allowance> getAllDecided(@PathVariable Long companyId,
                                             @RequestParam(value = "post", defaultValue = "0") Integer page) throws Exception {
        if(companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return allowanceService.readAllDecided(companyId, Constants.RequestStatus.APPROVED, Constants.RequestStatus.DECLINED, page);
    }

    // Get summary of monthly allowance along with DTOs for leaves, holidays, weekends and allowances
    @RequestMapping(value = "/month/{month}/year/{year}/user/{userId}", method = RequestMethod.GET)
    public AllowanceMonthlyDTO getMonthlyApprovedAllowances(@PathVariable Integer month,
                                                            @PathVariable Integer year,
                                                            @PathVariable Long userId) throws Exception {
        if (month == null || year == null || userId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return allowanceService.getMonthlyAllowanceSummaryByUser(userId, month, year);
    }

    // Approve Allowance Summary
    @RequestMapping(value = "/summary", method = RequestMethod.POST)
    public AllowanceSummary approveSummary(@RequestBody AllowanceSummaryDTO dto) throws Exception {
        return allowanceService.approveSummary(allowanceSummaryMapper.map(dto));
    }

    // Update Allowance Summary
    @RequestMapping(value = "/summary/{summaryId}", method = RequestMethod.PUT)
    public AllowanceSummary updateSummary(@PathVariable Long summaryId,
                                          @RequestBody AllowanceSummaryDTO dto) throws Exception {
        if (summaryId == null) {
            throw new EntityNotFoundException("Null summary id found!");
        }

        return allowanceService.updateSummary(summaryId, allowanceSummaryMapper.map(dto));
    }

    // Download monthly allowance report by user
    @RequestMapping(value = "/report/month/{month}/year/{year}/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getMonthlyAttendanceReport(@PathVariable Long userId,
                                                               @PathVariable Integer month,
                                                               @PathVariable Integer year) throws Exception {
        if (userId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath =
                generateMonthlyAllowanceByUserReport("/monthlyAllowanceByUser", userId, month, year);
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
        headers.add("Content-Disposition", "attachment; filename=\"report-monthlyAllowanceByUser" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    // List users for a company who requested allowance for a month
    @RequestMapping(value = "/users/requested/month", method = RequestMethod.GET)
    public List<UserSmallResponseDTO> getUsersByRequestAndMonth(@RequestParam Long companyId,
                                                                @RequestParam Long departmentId,
                                                                @RequestParam Long officeCodeId,
                                                                @RequestParam Integer month,
                                                                @RequestParam Integer year) throws Exception {
        if (companyId == null || departmentId == null || officeCodeId == null || month == null || year == null) {
            throw new EntityNotFoundException("Invalid values found!");
        }

        return allowanceService.listUsersByAllowanceRequestsForMonth(companyId, departmentId, officeCodeId, month, year);
    }
}
