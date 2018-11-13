package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.CarRequestDTO;
import com.sweetitech.hrm.domain.dto.CarRequestResponseDTO;
import com.sweetitech.hrm.domain.dto.page.CarRequestPage;
import com.sweetitech.hrm.service.implementation.CarRequestServiceImpl;
import com.sweetitech.hrm.service.mapping.CarRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee/car-requests")
public class EmployeeCarRequestController {

    private CarRequestServiceImpl carRequestService;

    @Autowired
    private CarRequestMapper carRequestMapper;

    @Autowired
    public EmployeeCarRequestController(CarRequestServiceImpl carRequestService) {
        this.carRequestService = carRequestService;
    }

    // Request car
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CarRequestResponseDTO requestCar(@RequestBody CarRequestDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return carRequestService.create(carRequestMapper.map(dto));
    }

    // Edit car request
    @RequestMapping(value = "/{requestId}", method = RequestMethod.PUT)
    public CarRequestResponseDTO edit(@PathVariable Long requestId, @RequestBody CarRequestDTO dto) throws Exception {
        if (requestId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return carRequestService.checkIfRequestedAndUpdate(requestId, carRequestMapper.map(dto));
    }

    // Cancel car request
    @RequestMapping(value = "/{requestId}", method = RequestMethod.DELETE)
    public void cancelRequest(@PathVariable Long requestId) throws Exception {
        if (requestId == null) {
            throw new EntityNotFoundException("Null request id found!");
        }

        carRequestService.checkIfRequestedAndCancel(requestId);
    }

    // Get dto by id
    @RequestMapping(value = "/dto/{requestId}", method = RequestMethod.GET)
    public CarRequestResponseDTO getDTO(@PathVariable Long requestId) throws Exception {
        if (requestId == null) {
            throw new EntityNotFoundException("Null request id found!");
        }

        return carRequestService.readDTO(requestId);
    }

    // Get all my car requests
    @RequestMapping(value = "/my-requests/{userId}", method = RequestMethod.GET)
    public CarRequestPage getAllByUserId(@PathVariable Long userId,
                                         @RequestParam String status,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (userId == null || status == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return carRequestService.readAllByRequestedByUserId(userId, status, page);
    }
}
