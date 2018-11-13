package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.PayrollPostDTO;
import com.sweetitech.hrm.domain.dto.LeaveCountDTO;
import com.sweetitech.hrm.domain.dto.UserPayrollDTO;
import com.sweetitech.hrm.domain.dto.UserPayrollListDTO;
import com.sweetitech.hrm.service.implementation.PayrollServiceImpl;
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

import static com.sweetitech.hrm.report.service.PDFService.generatePayrollByCompany;
import static com.sweetitech.hrm.report.service.PDFService.generatePayrollForUser;

@RestController
@RequestMapping("/admin/payroll")
public class AdminPayrollController {

    private PayrollServiceImpl payrollService;

    @Autowired
    public AdminPayrollController(PayrollServiceImpl payrollService) {
        this.payrollService = payrollService;
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public UserPayrollDTO getPayrollOfUserByMonth(@PathVariable Long userId,
                                                  @RequestParam Integer month,
                                                  @RequestParam Integer year) throws Exception {
        if (userId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return payrollService.readPayrollForUser(userId, month, year);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<UserPayrollListDTO> getAllForMonth(@RequestParam Long companyId,
                                                   @RequestParam Long departmentId,
                                                   @RequestParam Long officeCodeId,
                                                   @RequestParam Integer month,
                                                   @RequestParam Integer year) throws Exception {
        if (companyId == null || departmentId == null || officeCodeId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return payrollService.readAllUserPayrollByMonth(companyId, departmentId, officeCodeId, month, year);
    }

    // Approve payroll for user
    @RequestMapping(value = "", method = RequestMethod.POST)
    public UserPayrollDTO approvePayroll(@RequestBody PayrollPostDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return payrollService.approve(dto.getPayrollDTO(), dto.getBreakdowns());
    }

    // Add payment date to approved payroll
    @RequestMapping(value = "/paid/{payrollId}", method = RequestMethod.POST)
    public UserPayrollDTO changeStatusToPaid(@PathVariable Long payrollId,
                                             @RequestBody LeaveCountDTO dto) throws Exception {
        if (payrollId == null || dto == null || dto.getFromDate() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return payrollService.changeStatusToPaid(payrollId, dto.getFromDate());
    }

    // Download monthly payroll report for user
    @RequestMapping(value = "/report/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getMonthlyPayrollReportForUser(@PathVariable Long userId,
                                                                   @RequestParam Integer month,
                                                                   @RequestParam Integer year) throws Exception {
        if (userId == null || month == null  || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath =
                generatePayrollForUser("/monthlyPayrollByUser",
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
        headers.add("Content-Disposition", "attachment; filename=\"report-monthlyPayrollByUser" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    // Download monthly payroll report by company
    @RequestMapping(value = "/report/company", method = RequestMethod.GET)
    public ResponseEntity<Resource> getMonthlyPayrollReportByCompany(@RequestParam Long companyId,
                                                                     @RequestParam Long departmentId,
                                                                     @RequestParam Long officeCodeId,
                                                                     @RequestParam Integer month,
                                                                     @RequestParam Integer year) throws Exception {
        if (companyId == null || departmentId == null || officeCodeId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath =
                generatePayrollByCompany("/monthlyPayrollByCompany",
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
        headers.add("Content-Disposition", "attachment; filename=\"report-monthlyPayrollByCompany" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
