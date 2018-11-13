package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.TourDTO;
import com.sweetitech.hrm.domain.dto.TourResponseDTO;
import com.sweetitech.hrm.service.implementation.TourServiceImpl;
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
import static com.sweetitech.hrm.report.service.PDFService.generateYearlyTourReportByUser;

@RestController
@RequestMapping("/admin/tours")
public class AdminTourController {

    private TourServiceImpl tourService;

    @Autowired
    public AdminTourController(TourServiceImpl tourService) {
        this.tourService = tourService;
    }

    // Edit tour
    @RequestMapping(value = "/{tourId}", method = RequestMethod.PUT)
    public TourResponseDTO editTour(@PathVariable Long tourId,
                                    @RequestBody TourDTO dto) throws Exception {
        if (tourId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tourService.update(tourId, dto, false);
    }

    // Update status
    @RequestMapping(value = "/status/{tourId}", method = RequestMethod.PUT)
    public TourResponseDTO updateStatus(@PathVariable Long tourId,
                                        @RequestParam Long userId,
                                        @RequestParam String status) throws Exception {
        if (tourId == null || userId == null || status == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tourService.updateStatus(tourId, userId, status);
    }

    // Get all by company, status and month
    @RequestMapping(value = "/company/{companyId}", method = RequestMethod.GET)
    public List<TourResponseDTO> getAllByCompanyStatusAndMonth(@PathVariable Long companyId,
                                                               @RequestParam String status,
                                                               @RequestParam Integer month,
                                                               @RequestParam Integer year) throws Exception {
        if (companyId == null || status == null || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tourService.readByCompanyAndStatusAndMonth(companyId, status, month, year);
    }

    // Get all by user and year
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public List<TourResponseDTO> getAllByUserAndYear(@PathVariable Long userId,
                                                     @RequestParam Integer year) throws Exception {
        if (userId == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tourService.readForUser(userId, year);
    }

    // Download yearly tour report for user
    @RequestMapping(value = "/report/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Resource> getYearlyTourReportForUser(@PathVariable Long userId,
                                                               @RequestParam Integer year) throws Exception {
        if (userId == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        String downloadFilePath =
                generateYearlyTourReportByUser("/yearlyToursByUser",
                        userId, year);
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
        headers.add("Content-Disposition", "attachment; filename=\"report-yearlyToursByUser" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    // Download monthly approved tours by company
    @RequestMapping(value = "/report/company", method = RequestMethod.GET)
    public ResponseEntity<Resource> getMonthlyApprovedToursByCompany(@RequestParam Long companyId,
                                                                     @RequestParam Integer month,
                                                                     @RequestParam Integer year) throws Exception {
        if (companyId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }


        String downloadFilePath =
                generatePayrollByCompany("/monthlyApprovedToursByCompany",
                        companyId, month, year);
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
        headers.add("Content-Disposition", "attachment; filename=\"report-monthlyApprovedToursByCompany" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
