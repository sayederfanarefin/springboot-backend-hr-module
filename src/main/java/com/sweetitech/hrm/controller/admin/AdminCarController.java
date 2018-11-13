package com.sweetitech.hrm.controller.admin;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.dto.CarDTO;
import com.sweetitech.hrm.domain.dto.CarResponseDTO;
import com.sweetitech.hrm.domain.dto.LeaveCountDTO;
import com.sweetitech.hrm.domain.dto.SingleStringObjectDTO;
import com.sweetitech.hrm.domain.dto.page.CarPage;
import com.sweetitech.hrm.service.implementation.CarServiceImpl;
import com.sweetitech.hrm.service.mapping.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/cars")
public class AdminCarController {

    private CarServiceImpl carService;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    public AdminCarController(CarServiceImpl carService) {
        this.carService = carService;
    }

    // Get DTO by id
    @RequestMapping(value = "/dto/{carId}", method = RequestMethod.GET)
    public CarResponseDTO getDTO(@PathVariable Long carId) throws Exception {
        if (carId == null) {
            throw new EntityNotFoundException("Null car id found!");
        }

        return carService.read(carId);
    }

    // Create entity
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CarResponseDTO create(@RequestBody CarDTO dto) throws Exception {
        if (dto == null) {
            throw new  EntityNotFoundException("Null values found!");
        }

        return carService.create(carMapper.map(dto));
    }

    // Update entity
    @RequestMapping(value = "/{carId}", method = RequestMethod.PUT)
    public CarResponseDTO update(@PathVariable Long carId, @RequestBody CarDTO dto) throws Exception {
        if (carId == null || dto == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return carService.update(carId, carMapper.map(dto));
    }

    // Deactivate entity
    @RequestMapping(value = "/{carId}", method = RequestMethod.DELETE)
    public void remove(@PathVariable Long carId) throws Exception {
        if (carId == null) {
            throw new EntityNotFoundException("Null car id found!");
        }

        carService.remove(carId);
    }

    // Get all active by company id
    @RequestMapping(value = "/active/{companyId}", method = RequestMethod.GET)
    public CarPage getAllActiveByCompanyId(@PathVariable Long companyId,
                                           @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return carService.readAllActiveByCompanyId(companyId, page);
    }

    // Get all inactive by company id
    @RequestMapping(value = "/inactive/{companyId}", method = RequestMethod.GET)
    public CarPage getAllInactiveByCompanyId(@PathVariable Long companyId,
                                             @RequestParam(value = "page", defaultValue = "0") Integer page) throws Exception {
        if (companyId == null) {
            throw new EntityNotFoundException("Null company id found!");
        }

        return carService.readAllInactiveByCompanyId(companyId, page);
    }

    // Reactivate entity
    @RequestMapping(value = "/activate/{carId}", method = RequestMethod.PUT)
    public CarResponseDTO reactivate(@PathVariable Long carId) throws Exception {
        if (carId == null) {
            throw new EntityNotFoundException("Null car id found!");
        }

        return carService.reactivate(carId);
    }

    // Update maintenance status
    @RequestMapping(value = "/maintenance/{carId}", method = RequestMethod.PUT)
    public CarResponseDTO updateMaintenanceStatus(@PathVariable Long carId,
                                                  @RequestParam(value = "status", defaultValue = "false") boolean status) throws Exception {
        if (carId == null) {
            throw new EntityNotFoundException("Null car id found!");
        }

        return carService.updateMaintenanceStatus(carId, status);
    }

    // Check if exists
    @RequestMapping(value = "/exists", method = RequestMethod.POST)
    public boolean checkIfExists(@RequestBody SingleStringObjectDTO dto) throws Exception {
        if (dto == null || dto.getObject() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        return carService.exists(dto.getObject());
    }

    // Get all available
    @RequestMapping(value = "/available/{companyId}", method = RequestMethod.POST)
    public List<CarResponseDTO> getAllAvailableByCompany(@PathVariable Long companyId,
                                                         @RequestBody LeaveCountDTO dto,
                                                         @RequestParam Integer fromHour,
                                                         @RequestParam Integer toHour) throws Exception {
        if (companyId == null || dto == null || dto.getFromDate() == null || dto.getToDate() == null
                || fromHour == null || toHour == null) {
            throw new EntityNotFoundException("Invalid values found!");
        }

        return carService.readAllAvailable(companyId, dto.getFromDate(), fromHour, dto.getToDate(), toHour);
    }
}
