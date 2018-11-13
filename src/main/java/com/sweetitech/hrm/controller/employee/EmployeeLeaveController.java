package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.domain.Leave;
import com.sweetitech.hrm.domain.LeaveStatus;
import com.sweetitech.hrm.domain.OfficeHour;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.service.implementation.HolidayServiceImpl;
import com.sweetitech.hrm.service.implementation.LeaveServiceImpl;
import com.sweetitech.hrm.service.implementation.OfficeHourServiceImpl;
import com.sweetitech.hrm.service.mapping.LeaveCreateMapper;
import com.sweetitech.hrm.service.mapping.LeaveResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.sweetitech.hrm.report.service.PDFService.generateMonthlyLeaveByUserReport;

@RestController
@RequestMapping("/employee/leaves")
public class EmployeeLeaveController {

    private LeaveServiceImpl leaveService;

    @Autowired
    private LeaveCreateMapper leaveCreateMapper;

    @Autowired
    private LeaveResponseMapper leaveResponseMapper;

    @Autowired
    public EmployeeLeaveController(LeaveServiceImpl leaveService) {
        this.leaveService = leaveService;
    }

    // Request Leave
    @RequestMapping(value = "", method = RequestMethod.POST)
    public LeaveResponseDTO requestLeave(@RequestBody LeaveDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("No leave details found!");
        }

        return leaveResponseMapper.map(leaveService.create(leaveCreateMapper.map(dto)));
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

        if (!leaveService.readStatus(leaveId).equals(Constants.RequestStatus.REQUESTED)) {
            throw new EntityNotFoundException("Employee cannot edit leave now! Status has been changed!");
        }

        return leaveResponseMapper.map(leaveService.update(leaveId, leaveCreateMapper.map(dto)));
    }

    // Cancel Leave
    @RequestMapping(value = "/cancel/{leaveId}", method = RequestMethod.PUT)
    public LeaveResponseDTO cancelLeave(@PathVariable Long leaveId,
                                        @RequestParam Long userId) throws Exception {
        if(leaveId == null) {
            throw new EntityNotFoundException("Null leave id found!");
        }
        if(userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        if (!leaveService.readStatus(leaveId).equals(Constants.RequestStatus.REQUESTED)) {
            throw new EntityNotFoundException("Employee cannot edit leave now! Status has been changed!");
        }

        LeaveStatus leaveStatus = leaveService.prepareStatus(Constants.RequestStatus.CANCELLED, userId, null);

        return leaveResponseMapper.map(leaveService.updateStatus(leaveId, leaveStatus));
    }

    // Get All By Status And User
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public Iterable<Leave> getAllByStatusAndUser(@PathVariable Long userId,
                                                 @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "status", defaultValue = Constants.RequestStatus.REQUESTED) String status) throws Exception {
        if(userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }
        if (status == null) {
            throw new EntityNotFoundException("Null status found!");
        }

        return leaveService.readAllByStatusAndUser(userId, status, page);
    }

    // Get Entity By id
    @RequestMapping(value = "/{leaveId}", method = RequestMethod.GET)
    public Leave getEntity(@PathVariable Long leaveId) throws Exception {
        if(leaveId == null) {
            throw new EntityNotFoundException("Null leave id found!");
        }

        return leaveService.read(leaveId);
    }

    // Get DTO By Id
    @RequestMapping(value = "/dto/{leaveId}", method = RequestMethod.GET)
    public LeaveResponseDTO getDTO(@PathVariable Long leaveId) throws Exception {
        if(leaveId == null) {
            throw new EntityNotFoundException("Null leave id found!");
        }

        return leaveResponseMapper.map(leaveService.read(leaveId));
    }

    // Get Number of Leaves by Status
    @RequestMapping(value = "/number/{userId}", method = RequestMethod.GET)
    public Integer getNumberByStatus(@PathVariable Long userId,
                                     @RequestParam String status,
                                     @RequestParam Integer year,
                                     @RequestParam Integer month) throws Exception {
        if (userId == null || status == null || year == null || month == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return leaveService.getNumberForMonthAndYear(userId, status, year, month);
    }

    // Get Leaves Taken by User in Year
    @RequestMapping(value = "/userSummary/{userId}", method = RequestMethod.GET)
    public LeaveUserSummaryDTO getApprovedLeavesByUserAndYear(@PathVariable Long userId, @RequestParam Integer year) throws Exception {
        if(userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return leaveService.getUserSummary(userId, year);
    }

    // Get leave count
    @RequestMapping(value = "/count/{companyId}", method = RequestMethod.POST)
    public Integer getLeaveCount(@PathVariable Long companyId,
                                 @RequestBody LeaveCountDTO dto) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }
        if (dto.getFromDate() == null || dto.getToDate() == null) {
            throw new EntityNotFoundException("Null dates found!");
        }

        if (!DateTimeUtils.isValidDate(dto.getFromDate().toString(), "E MMM dd HH:mm:ss z yyyy")
                || !DateTimeUtils.isValidDate(dto.getToDate().toString(), "E MMM dd HH:mm:ss z yyyy")) {
            throw new EntityNotFoundException("Invalid date format!");
        }

        //return leaveService.getLeaveCount(DateTimeUtils.toDate(fromDate), DateTimeUtils.toDate(toDate));
        return leaveService.getLeaveCount(dto.getFromDate(), dto.getToDate(), companyId);
    }

    // Get list of leaves by user per month
    @RequestMapping(value = "/list/{userId}", method = RequestMethod.GET)
    public List<LeaveSummaryDTO> getAllByUserAndMonth(@PathVariable Long userId,
                                                      @RequestParam Integer month,
                                                      @RequestParam Integer year) throws Exception {
        if (userId == null || month == null || year == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return leaveService.getAllMonthlyLeaveSummary(userId, year, month);
    }

    // Download monthly summary of user
    @RequestMapping(value = "/report/summary/{userId}/month", method = RequestMethod.GET)
    public ResponseEntity<Resource> getReportByUserAndMonth(@PathVariable Long userId,
                                                            @RequestParam Integer month,
                                                            @RequestParam Integer year) throws Exception {
        String downloadFilePath = generateMonthlyLeaveByUserReport("/monthlyLeaveByUser",
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
}
