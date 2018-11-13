package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.MobileLogs;
import com.sweetitech.hrm.domain.dto.LeaveCountDTO;
import com.sweetitech.hrm.domain.dto.MobileLogsDTO;
import com.sweetitech.hrm.domain.dto.MobileLogsResponseDTO;
import com.sweetitech.hrm.domain.dto.page.MobileLogsPage;
import com.sweetitech.hrm.service.implementation.MobileLogsServiceImpl;
import com.sweetitech.hrm.service.mapping.MobileLogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee/mobile-logs")
public class EmployeeMobileLogsController {

    private MobileLogsServiceImpl mobileLogsService;

    @Autowired
    private MobileLogsMapper mobileLogsMapper;

    @Autowired
    public EmployeeMobileLogsController(MobileLogsServiceImpl mobileLogsService) {
        this.mobileLogsService = mobileLogsService;
    }

    // Create log entry
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
    public MobileLogsResponseDTO create(@PathVariable Long userId,
                                        @RequestBody MobileLogsDTO dto) throws Exception {
        if (userId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return mobileLogsService.create(mobileLogsMapper.map(dto, userId));
    }

    // Get DTO
    @RequestMapping(value = "/dto/{logId}", method = RequestMethod.GET)
    public MobileLogsResponseDTO getDTO(@PathVariable Long logId) throws Exception {
        if (logId == null) {
            throw new EntityNotFoundException("Null log id found!");
        }

        return mobileLogsService.readDTO(logId);
    }

    // Get all by user id
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public MobileLogsPage getAllByUser(@PathVariable Long userId,
                                       @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (userId == null) {
            throw new EntityNotFoundException("Null user id found!");
        }

        return mobileLogsService.readAllByUser(userId, page);
    }

    // Bulk add mobile logs
    @RequestMapping(value = "/bulk/{userId}", method = RequestMethod.POST)
    public List<MobileLogsResponseDTO> bulkAdd(@PathVariable Long userId,
                                               @RequestBody List<MobileLogsDTO> mobileLogs) throws Exception {
        if (userId == null || mobileLogs == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return mobileLogsService.bulkAdd(mobileLogs, userId);
    }

    // Get all by user and day
    @RequestMapping(value = "/user/day/{userId}", method = RequestMethod.POST)
    public List<MobileLogsResponseDTO> getAllByUserAndDay(@PathVariable Long userId,
                                                          @RequestBody LeaveCountDTO dto) throws Exception {
        if (userId == null || dto == null || dto.getFromDate() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return mobileLogsService.readAllByUserAndDate(userId, dto.getFromDate());
    }

}
