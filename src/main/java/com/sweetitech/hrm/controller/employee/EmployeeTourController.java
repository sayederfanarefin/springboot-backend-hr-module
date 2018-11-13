package com.sweetitech.hrm.controller.employee;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.TourDTO;
import com.sweetitech.hrm.domain.dto.TourResponseDTO;
import com.sweetitech.hrm.service.implementation.TourServiceImpl;
import com.sweetitech.hrm.service.mapping.TourMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee/tours")
public class EmployeeTourController {

    private TourServiceImpl tourService;

    @Autowired
    private TourMapper tourMapper;

    @Autowired
    public EmployeeTourController(TourServiceImpl tourService) {
        this.tourService = tourService;
    }

    // Request tour
    @RequestMapping(value = "", method = RequestMethod.POST)
    public TourResponseDTO create(@RequestBody TourDTO dto) throws Exception {
        if (dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tourService.create(dto);
    }

    // Edit tour
    @RequestMapping(value = "/{tourId}", method = RequestMethod.PUT)
    public TourResponseDTO update(@PathVariable Long tourId,
                                  @RequestBody TourDTO dto) throws Exception {
        if (tourId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tourService.update(tourId, dto, true);
    }

    // Cancel tour
    @RequestMapping(value = "/{tourId}", method = RequestMethod.DELETE)
    public void cancel(@PathVariable Long tourId) throws Exception {
        if (tourId == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        tourService.cancel(tourId);
    }

    // Get by id
    @RequestMapping(value = "/{tourId}", method = RequestMethod.GET)
    public TourResponseDTO getById(@PathVariable Long tourId) throws Exception {
        if (tourId == null) {
            throw new EntityNotFoundException("Null value found!");
        }

        return tourService.readDTO(tourId);
    }

    // Get all by user, status and month
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public List<TourResponseDTO> getAllByCompanyStatusAndMonth(@PathVariable Long userId,
                                                               @RequestParam String status,
                                                               @RequestParam Integer month,
                                                               @RequestParam Integer year) throws Exception {
        if (userId == null || status == null || month == null || year == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tourService.readByUserAndStatusAndMonth(userId, status, month, year);
    }

    // Get all by user and status
    @RequestMapping(value = "/user/{userId}/status", method = RequestMethod.GET)
    public List<TourResponseDTO> getAllByCompanyAndStatus(@PathVariable Long userId,
                                                          @RequestParam String status) throws Exception {
        if (userId == null || status == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return tourService.readByUserAndStatus(userId, status);
    }
}
