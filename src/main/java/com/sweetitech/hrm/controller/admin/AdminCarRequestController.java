package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.CarRequestDTO;
import com.sweetitech.hrm.domain.dto.CarRequestResponseDTO;
import com.sweetitech.hrm.domain.dto.LeaveCountDTO;
import com.sweetitech.hrm.domain.dto.page.CarRequestPage;
import com.sweetitech.hrm.service.implementation.CarRequestServiceImpl;
import com.sweetitech.hrm.service.mapping.CarRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/car-requests")
public class AdminCarRequestController {

    private CarRequestServiceImpl carRequestService;

    @Autowired
    private CarRequestMapper carRequestMapper;

    @Autowired
    public AdminCarRequestController(CarRequestServiceImpl carRequestService) {
        this.carRequestService = carRequestService;
    }

    // Edit car request
    @RequestMapping(value = "/{requestId}", method = RequestMethod.PUT)
    public CarRequestResponseDTO edit(@PathVariable Long requestId, @RequestBody CarRequestDTO dto) throws Exception {
        if (requestId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return carRequestService.checkIfNotCancelledAndUpdate(requestId, carRequestMapper.map(dto));
    }

    // Get all by date and company id
    @RequestMapping(value = "/company/{companyId}/date", method = RequestMethod.POST)
    public CarRequestPage getAllByDateAndCompanyId(@PathVariable Long companyId,
                                                   @RequestBody LeaveCountDTO dto,
                                                   @RequestParam String status,
                                                   @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null || dto == null || dto.getFromDate() == null || status == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return carRequestService.readAllByCompanyIdAndDate(companyId, dto.getFromDate(), status, page);
    }

    // Get all by month and company id
    @RequestMapping(value = "/company/{companyId}/month", method = RequestMethod.GET)
    public CarRequestPage getAllByMonthAndCompanyId(@PathVariable Long companyId,
                                                    @RequestParam Integer month,
                                                    @RequestParam Integer year,
                                                    @RequestParam String status,
                                                    @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null || month == null || year == null || status == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return carRequestService.readAllByCompanyIdAndMonth(companyId, year, month, status, page);
    }

    // Update status
    @RequestMapping(value = "/status/{requestId}", method = RequestMethod.PUT)
    public CarRequestResponseDTO updateStatus(@PathVariable Long requestId,
                                              @RequestParam String status) throws Exception {
        if (requestId == null || status == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return carRequestService.updateStatus(requestId, status);
    }

}
