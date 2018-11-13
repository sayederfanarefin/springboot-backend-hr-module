package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Allowance;
import com.sweetitech.hrm.domain.dto.AllowanceDTO;
import com.sweetitech.hrm.domain.dto.AllowanceListDTO;
import com.sweetitech.hrm.domain.dto.AllowanceListObjectDTO;
import com.sweetitech.hrm.domain.dto.AllowanceMonthlyDTO;
import com.sweetitech.hrm.service.implementation.AllowanceServiceImpl;
import com.sweetitech.hrm.service.mapping.AllowanceCreateMapper;
import com.sweetitech.hrm.service.mapping.AllowanceEditMapper;
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

import static com.sweetitech.hrm.report.service.PDFService.generateBlankAllowanceForm;
import static com.sweetitech.hrm.report.service.PDFService.generateMonthlyAllowanceByUserReport;

@RestController
@RequestMapping(value = "/employee/allowances")
public class EmployeeAllowanceController {

    private AllowanceServiceImpl allowanceService;

    @Autowired
    private AllowanceCreateMapper allowanceCreateMapper;

    @Autowired
    private AllowanceEditMapper allowanceEditMapper;

    @Autowired
    public EmployeeAllowanceController(AllowanceServiceImpl allowanceService) {
        this.allowanceService = allowanceService;
    }

    // Request Allowance
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public Allowance requestAllowanceByUser(@PathVariable Long userId,
                                            @RequestBody AllowanceDTO dto) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        if (dto == null) {
            throw new EntityNotFoundException("Null data found!");
        }

        dto.setRequestedBy(userId);

        return allowanceService.create(allowanceCreateMapper.map(dto));
    }

    // Edit Requested Allowance
    @RequestMapping(value = "/{allowanceId}/allowance", method = RequestMethod.PUT)
    public Allowance editRequestedAllowance(@PathVariable Long allowanceId,
                                            @RequestBody AllowanceDTO dto) throws Exception {
        if (allowanceId == null) {
            throw new EntityNotFoundException("Null allowance id found!");
        }

        if (dto == null) {
            throw new EntityNotFoundException("Null data found!");
        }

        if (!allowanceService.readStatus(allowanceId).equals(Constants.RequestStatus.REQUESTED)) {
            throw new EntityNotFoundException("Allowance cannot be edited now!");
        }

        dto.setStatus(Constants.RequestStatus.REQUESTED);

        return allowanceService.update(allowanceId, allowanceEditMapper.map(dto));
    }

    // Cancel Request
    @RequestMapping(value = "/{allowanceId}", method = RequestMethod.DELETE)
    public void cancelRequest(@PathVariable Long allowanceId) throws Exception {
        if (allowanceId == null) {
            throw new EntityNotFoundException("Null allowance id found!");
        }

        allowanceService.cancel(allowanceId);
    }

    // Get Entity
    @RequestMapping(value = "/{allowanceId}", method = RequestMethod.GET)
    public Allowance getEntity(@PathVariable Long allowanceId) throws Exception {
        if (allowanceId == null) {
            throw new EntityNotFoundException("Null allowance id found!");
        }

        return allowanceService.read(allowanceId);
    }

    // Get DTO
    @RequestMapping(value = "/{allowanceId}/dto", method = RequestMethod.GET)
    public AllowanceDTO getDTO(@PathVariable Long allowanceId) throws Exception {
        if (allowanceId == null) {
            throw new EntityNotFoundException("Null allowance id found!");
        }

        return allowanceEditMapper.map(allowanceService.read(allowanceId));
    }

    // Get All Requested by User
    @RequestMapping(value = "/requested/user/{userId}", method = RequestMethod.GET)
    public Iterable<Allowance> getAllByUser(@PathVariable Long userId,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if(userId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return allowanceService.readAllByUserId(userId, page);
    }

    // Get All By Status and User id
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public Iterable<Allowance> getAllByStatusAndUser(@PathVariable Long userId,
                                                     @RequestParam(value = "status", defaultValue = "requested") String status,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if(userId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return allowanceService.readAllByStatusAndUser(userId, status, page);
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

    // Download blank TA/DA form
    @RequestMapping(value = "/report/month/{userId}/year/{month}/user/{year}/blank", method = RequestMethod.GET)
    public ResponseEntity<Resource> getMonthlyAttendanceReport(@PathVariable Long userId,
                                                               @PathVariable Integer month,
                                                               @PathVariable Integer year) throws Exception {
        if (userId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath =
                generateBlankAllowanceForm("/blankAllowanceForm", userId, month, year);
        System.out.println("DL File Path: "+downloadFilePath);

        System.out.println("Line 164: " + userId + " " + month + " " + year);

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
        headers.add("Content-Disposition", "attachment; filename=\"report-blankAllowanceForm" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    // Bulk add allowances for a month
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public List<AllowanceListObjectDTO> bulkRequest(@RequestBody AllowanceListDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return allowanceService.bulkRequestAllowances(dto);
    }

    // Download monthly allowance report by user
    @RequestMapping(value = "/report/month/{userId}/year/{month}/user/{year}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getMonthlyAllowanceReportByUser(@PathVariable Integer year,
                                                                    @PathVariable Long userId,
                                                                    @PathVariable Integer month) throws Exception {
        if (userId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath =
                generateMonthlyAllowanceByUserReport("/monthlyAllowanceByUser", userId, month, year);
        System.out.println("DL File Path: "+downloadFilePath);

        System.out.println("Line 217: " + userId + " " + month + " " + year);

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
        headers.add("Content-Disposition", "attachment; filename=\"report-monthlyEmployeeAllowance" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
