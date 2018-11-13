package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Bonus;
import com.sweetitech.hrm.domain.dto.BonusDTO;
import com.sweetitech.hrm.domain.dto.BonusListDTO;
import com.sweetitech.hrm.domain.dto.BonusResponseDTO;
import com.sweetitech.hrm.domain.dto.LeaveCountDTO;
import com.sweetitech.hrm.service.implementation.BonusServiceImpl;
import com.sweetitech.hrm.service.mapping.BonusMapper;
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

import static com.sweetitech.hrm.report.service.PDFService.generateYearlyBonusByTypeAndDepartment;

@RestController
@RequestMapping("/admin/bonuses")
public class AdminBonusController {

    private BonusServiceImpl bonusService;

    @Autowired
    private BonusMapper bonusMapper;

    @Autowired
    public AdminBonusController(BonusServiceImpl bonusService) {
        this.bonusService = bonusService;
    }

    // Create
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BonusResponseDTO create(@RequestBody BonusDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null object found!");
        }

        return bonusService.create(bonusMapper.map(dto));
    }

    // Update
    @RequestMapping(value = "/{bonusId}", method = RequestMethod.PUT)
    public BonusResponseDTO update(@PathVariable Long bonusId, @RequestBody BonusDTO dto) throws Exception {
        if (bonusId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return bonusService.update(bonusId, bonusMapper.map(dto));
    }

    // Update payment date
    @RequestMapping(value = "/paid/{bonusId}", method = RequestMethod.PUT)
    public BonusResponseDTO updatePaymentDate(@PathVariable Long bonusId, @RequestBody LeaveCountDTO dto) throws Exception {
        if (bonusId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return bonusService.updatePaymentDate(bonusId, dto.getFromDate());
    }

    // Delete
    @RequestMapping(value = "/{bonusId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long bonusId) throws Exception {
        if (bonusId == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        bonusService.remove(bonusId);
    }

    // Get all approved by user for month and year
    @RequestMapping(value = "/from/{userId}", method = RequestMethod.GET)
    public Iterable<Bonus> getCreatedByUser(@PathVariable Long userId,
                                            @RequestParam Integer month,
                                            @RequestParam Integer year,
                                            @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (userId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return bonusService.readAllByApprovedByMonthAndYear(userId, month, year, page);
    }

    // Get all by company, month and year
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public Iterable<Bonus> getMonthlyByCompany(@PathVariable Long companyId,
                                               @RequestParam Integer month,
                                               @RequestParam Integer year,
                                               @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return bonusService.readAllByCompanyMonthAndYear(companyId, month, year, page);
    }

    // Get all by company and year
    @RequestMapping(value = "/company/{companyId}/year", method = RequestMethod.GET)
    public Iterable<Bonus> getYearlyByCompany(@PathVariable Long companyId,
                                              @RequestParam Integer year,
                                              @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null || year == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return bonusService.readAllByCompanyAndYear(companyId, year, page);
    }

    // Get all bonuses for employees in same department
    @RequestMapping(value = "/department", method = RequestMethod.GET)
    public List<BonusResponseDTO> getYearlyByTypeAndDepartment(@RequestParam Long companyId,
                                                               @RequestParam Long departmentId,
                                                               @RequestParam Long officeCodeId,
                                                               @RequestParam Long typeId,
                                                               @RequestParam Integer year) throws Exception {
        if (companyId == null
                || departmentId == null
                || officeCodeId == null
                || typeId == null
                || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return bonusService.readAllByCompanyDepartmentOfficeCodeTypeAndYear(companyId, departmentId, officeCodeId, typeId, year);
    }

    // Bulk update bonuses for employees filtered by department
    @RequestMapping(value = "/department", method = RequestMethod.POST)
    public List<BonusResponseDTO> updateYearlyByDepartment(@RequestBody BonusListDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null value received!");
        }

        return bonusService.createOrUpdateBonusesByYear(dto);
    }

    // Generate report based on type and department
    @RequestMapping(value = "/report/department", method = RequestMethod.GET)
    public ResponseEntity<Resource> getYearlyByCategoryTypeAndDepartment(@RequestParam Long companyId,
                                                                         @RequestParam Long departmentId,
                                                                         @RequestParam Long officeCodeId,
                                                                         @RequestParam Long typeId,
                                                                         @RequestParam Integer year) throws Exception {
        if (companyId == null
                || departmentId == null
                || officeCodeId == null
                || typeId == null
                || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

//        return commissionService.generateReportForYearByCompanyDepartmentOfficeCodeTypeAndCategory(companyId, department, officeCodeId, typeId, year, category);

        String downloadFilePath =
                generateYearlyBonusByTypeAndDepartment("/yearlyBonusSummary",
                        companyId, departmentId, officeCodeId, typeId, year);
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
        headers.add("Content-Disposition", "attachment; filename=\"report-yearlyBonusSummary" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
