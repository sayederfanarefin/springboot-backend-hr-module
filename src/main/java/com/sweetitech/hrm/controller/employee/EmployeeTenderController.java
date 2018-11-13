package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.SingleStringObjectDTO;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;
import com.sweetitech.hrm.domain.dto.TenderDTO;
import com.sweetitech.hrm.domain.dto.TenderResponseDTO;
import com.sweetitech.hrm.service.implementation.TenderServiceImpl;
import com.sweetitech.hrm.service.mapping.TenderMapper;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.sweetitech.hrm.report.service.PDFService.generateAllTenders;

@RestController
@RequestMapping("/employee/tenders")
public class EmployeeTenderController {

    private TenderServiceImpl tenderService;

    @Autowired
    private TenderMapper tenderMapper;

    @Autowired
    public EmployeeTenderController(TenderServiceImpl tenderService) {
        this.tenderService = tenderService;
    }

    // Create
    @RequestMapping(value = "", method = RequestMethod.POST)
    public TenderResponseDTO create(@RequestBody TenderDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tenderService.create(tenderMapper.map(dto));
    }

    // Update
    @RequestMapping(value = "/{tenderId}", method = RequestMethod.PUT)
    public TenderResponseDTO update(@PathVariable Long tenderId, @RequestBody TenderDTO dto) throws Exception {
        if (tenderId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tenderService.update(tenderId, tenderMapper.map(dto));
    }

    // Get DTO
    @RequestMapping(value = "/{tenderId}", method = RequestMethod.GET)
    public TenderResponseDTO getDTO(@PathVariable Long tenderId) throws Exception {
        if (tenderId == null) {
            throw new EntityNotFoundException("Null tender id found!");
        }

        return tenderService.readDTO(tenderId);
    }

    // Get all
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<TenderResponseDTO> getAll() throws Exception {
        return tenderService.readAll();
    }

    // Get all by user id
    @RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
    public List<TenderResponseDTO> getAllByUserId(@PathVariable Long userId) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return tenderService.readAllByUser(userId);
    }

    // Delete
    @RequestMapping(value = "/{tenderId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long tenderId) throws Exception {
        if (tenderId == null) {
            throw new EntityNotFoundException("Null tender id found!");
        }

        tenderService.remove(tenderId);
    }

    // Update status
    @RequestMapping(value = "/status/{tenderId}", method = RequestMethod.PUT)
    public TenderResponseDTO updateStatus(@PathVariable Long tenderId,
                                          @RequestBody SingleStringObjectDTO dto) throws Exception {
        if (tenderId == null || dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tenderService.updateStatus(tenderId, dto.getObject());
    }

    // Update tender security status
    @RequestMapping(value = "/tender-security/{tenderId}", method = RequestMethod.PUT)
    public TenderResponseDTO updateTenderSecurityStatus(@PathVariable Long tenderId,
                                                        @RequestBody SingleStringObjectDTO dto) throws Exception {
        if (tenderId == null || dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tenderService.updateTenderSecurityStatus(tenderId, dto.getObject());
    }

    // Update tender status
    @RequestMapping(value = "/tender-status/{tenderId}", method = RequestMethod.PUT)
    public TenderResponseDTO updateTenderStatus(@PathVariable Long tenderId,
                                                @RequestBody SingleStringObjectDTO dto) throws Exception {
        if (tenderId == null || dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tenderService.updateTenderStatus(tenderId, dto.getObject());
    }

    // List all nearing expiry
    @RequestMapping(value = "/expiry", method = RequestMethod.GET)
    public List<TenderResponseDTO> getAllNearingExpiry() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);
        Date date = sdf.parse(sdf1.format(new Date()));

        return tenderService.readAllNearingExpiry(date);
    }

    // Generate report for all tenders
    @RequestMapping(value = "/report/all", method = RequestMethod.GET)
    public ResponseEntity<Resource> getAllTenders() throws Exception {

        String downloadFilePath =
                generateAllTenders("/allTenders");
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
        headers.add("Content-Disposition", "attachment; filename=\"report-allTenders" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    // Generate report for tenders nearing expiry
    @RequestMapping(value = "/report/expiry", method = RequestMethod.GET)
    public ResponseEntity<Resource> getTendersNearingExpiry() throws Exception {

        String downloadFilePath =
                generateAllTenders("/expiredTenders");
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
        headers.add("Content-Disposition", "attachment; filename=\"report-expiredTenders" + ".pdf\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    // Generate task for tender
    @RequestMapping(value = "/task/{tenderId}", method = RequestMethod.POST)
    public TaskResponseDTO generateTaskForTender(@PathVariable Long tenderId,
                                                 @RequestParam Long assignedToUserId,
                                                 @RequestParam Long assignedByUserId) throws Exception {
        if (tenderId == null || assignedByUserId == null || assignedToUserId == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tenderService.createTaskForTender(tenderId, assignedToUserId, assignedByUserId);
    }

}
