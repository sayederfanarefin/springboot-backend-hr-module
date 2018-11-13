package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Commission;
import com.sweetitech.hrm.domain.dto.CommissionDTO;
import com.sweetitech.hrm.domain.dto.CommissionListDTO;
import com.sweetitech.hrm.domain.dto.CommissionResponseDTO;
import com.sweetitech.hrm.domain.dto.LeaveCountDTO;
import com.sweetitech.hrm.service.implementation.CommissionServiceImpl;
import com.sweetitech.hrm.service.mapping.CommissionMapper;
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

import static com.sweetitech.hrm.report.service.PDFService.generateYearlyByCategoryTypeAndDepartment;

@RestController
@RequestMapping("/admin/commissions")
public class AdminCommissionController {

    private CommissionServiceImpl commissionService;

    @Autowired
    private CommissionMapper commissionMapper;
    
    @Autowired
    public AdminCommissionController(CommissionServiceImpl commissionService) {
        this.commissionService = commissionService;
    }

    // Create
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CommissionResponseDTO create(@RequestBody CommissionDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null object found!");
        }

        return commissionService.create(commissionMapper.map(dto));
    }

    // Update
    @RequestMapping(value = "/{commissionId}", method = RequestMethod.PUT)
    public CommissionResponseDTO update(@PathVariable Long commissionId, @RequestBody CommissionDTO dto) throws Exception {
        if (commissionId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return commissionService.update(commissionId, commissionMapper.map(dto));
    }

    // Update payment date
    @RequestMapping(value = "/paid/{commissionId}", method = RequestMethod.PUT)
    public CommissionResponseDTO updatePaymentDate(@PathVariable Long commissionId, @RequestBody LeaveCountDTO dto) throws Exception {
        if (commissionId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return commissionService.updatePaymentDate(commissionId, dto.getFromDate());
    }

    // Delete
    @RequestMapping(value = "/{commissionId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long commissionId) throws Exception {
        if (commissionId == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        commissionService.remove(commissionId);
    }

    // Get all approved by user for month and year
    @RequestMapping(value = "/from/{userId}", method = RequestMethod.GET)
    public Iterable<Commission> getCreatedByUser(@PathVariable Long userId,
                                                 @RequestParam Integer month,
                                                 @RequestParam Integer year,
                                                 @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (userId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return commissionService.readAllByApprovedByMonthAndYear(userId, month, year, page);
    }

    // Get all by company, month and year
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public Iterable<Commission> getMonthlyByCompany(@PathVariable Long companyId,
                                               @RequestParam Integer month,
                                               @RequestParam Integer year,
                                               @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return commissionService.readAllByCompanyMonthAndYear(companyId, month, year, page);
    }

    // Get all by company and year
    @RequestMapping(value = "/company/{companyId}/year", method = RequestMethod.GET)
    public Iterable<Commission> getYearlyByCompany(@PathVariable Long companyId,
                                              @RequestParam Integer year,
                                              @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null || year == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return commissionService.readAllByCompanyAndYear(companyId, year, page);
    }

    // Get all commissions for employees in same department
    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public List<CommissionResponseDTO> getYearlyByTypeAndDepartment(@RequestParam Long companyId,
                                                                    @RequestParam String department,
                                                                    @RequestParam Long officeCodeId,
                                                                    @RequestParam Long typeId,
                                                                    @RequestParam Integer year) throws Exception {
        if (companyId == null
                || department == null
                || officeCodeId == null
                || typeId == null
                || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return commissionService.readAllByCompanyDepartmentOfficeCodeTypeAndYear(companyId, department, officeCodeId, typeId, year);
    }

    // Bulk update commissions for employees filtered by department
    @RequestMapping(value = "/department", method = RequestMethod.POST)
    public List<CommissionResponseDTO> updateYearlyByDepartment(@RequestBody CommissionListDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null value received!");
        }

        return commissionService.createOrUpdateCommissionsByYear(dto);
    }

    // Generate report based on type, category and department
    @RequestMapping(value = "/report/department", method = RequestMethod.GET)
    public ResponseEntity<Resource> getYearlyByCategoryTypeAndDepartment(@RequestParam Long companyId,
                                                                         @RequestParam String department,
                                                                         @RequestParam Long officeCodeId,
                                                                         @RequestParam Long typeId,
                                                                         @RequestParam Integer year,
                                                                         @RequestParam String category) throws Exception {
        if (companyId == null
                || department == null
                || officeCodeId == null
                || typeId == null
                || year == null
                || category == null) {
            throw new EntityNotFoundException("Null values found!");
        }

//        return commissionService.generateReportForYearByCompanyDepartmentOfficeCodeTypeAndCategory(companyId, department, officeCodeId, typeId, year, category);

        String downloadFilePath =
                generateYearlyByCategoryTypeAndDepartment("/yearlyCommissionSummary",
                        companyId, department, officeCodeId, typeId, year, category);
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
        headers.add("Content-Disposition", "attachment; filename=\"report-yearlyCommissionSummary" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
