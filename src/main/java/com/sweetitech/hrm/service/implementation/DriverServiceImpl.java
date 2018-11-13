package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Driver;
import com.sweetitech.hrm.domain.dto.DriverResponseDTO;
import com.sweetitech.hrm.domain.dto.page.DriverPage;
import com.sweetitech.hrm.repository.DriverRepository;
import com.sweetitech.hrm.service.DriverService;
import com.sweetitech.hrm.service.mapping.DriverResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private DriverRepository driverRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private DriverResponseMapper driverResponseMapper;

    @Autowired
    private CarScheduleServiceImpl carScheduleService;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public DriverResponseDTO create(Driver driver) throws Exception {
        if (driverRepository.getFirstByUserId(driver.getUser().getId()) != null) {
            throw new EntityNotFoundException("Driver already exists!");
        }

        return driverResponseMapper.map(driverRepository.save(driver));
    }

    @Override
    public DriverResponseDTO update(Long id, Driver driver) throws Exception {
        Driver driverOld = driverRepository.getFirstById(id);
        if (driverOld == null) {
            throw new EntityNotFoundException("No drivers with id: " + id);
        }

        driver.setId(id);
        return driverResponseMapper.map(driverRepository.save(driver));
    }

    @Override
    public Driver readEntity(Long id) throws Exception {
        return driverRepository.getFirstById(id);
    }

    @Override
    public DriverResponseDTO read(Long id) throws Exception {
        Driver driver = driverRepository.getFirstById(id);
        if (driver == null) {
            throw new EntityNotFoundException("No drivers with id: " + id);
        }

        return driverResponseMapper.map(driver);
    }

    @Override
    public void remove(Long id) throws Exception {
        Driver driver = driverRepository.getFirstById(id);
        if (driver == null) {
            throw new EntityNotFoundException("No drivers with id: " + id);
        }

        driver.setDeleted(true);
        driverRepository.save(driver);
    }

    @Override
    public DriverPage readAllActiveByCompanyId(Long companyId, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        Page<Driver> drivers = driverRepository.findAllByUserCompanyIdAndIsDeletedFalseOrderByUserBasicInfoFirstNameAsc(companyId,
                new PageRequest(page, PageAttr.PAGE_SIZE));

        List<DriverResponseDTO> dtos = new ArrayList<>();
        if (drivers.getContent() != null && drivers.getContent().size() > 0) {
            for (Driver driver: drivers.getContent()) {
                dtos.add(driverResponseMapper.map(driver));
            }
        }

        return new DriverPage(dtos, drivers);
    }

    @Override
    public DriverPage readAllInactiveByCompanyId(Long companyId, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        Page<Driver> drivers = driverRepository.findAllByUserCompanyIdAndIsDeletedTrueOrderByUserBasicInfoFirstNameAsc(companyId,
                new PageRequest(page, PageAttr.PAGE_SIZE));

        List<DriverResponseDTO> dtos = new ArrayList<>();
        if (drivers.getContent() != null && drivers.getContent().size() > 0) {
            for (Driver driver: drivers.getContent()) {
                dtos.add(driverResponseMapper.map(driver));
            }
        }

        return new DriverPage(dtos, drivers);
    }

    @Override
    public DriverResponseDTO reactivate(Long id) throws Exception {
        Driver driver = driverRepository.getFirstById(id);
        if (driver == null) {
            throw new EntityNotFoundException("No drivers with id: " + id);
        }

        driver.setDeleted(false);
        return driverResponseMapper.map(driverRepository.save(driver));
    }

    @Override
    public List<DriverResponseDTO> readAllAvailable(Long companyId, Date fromDate, Integer fromHour,
                                                    Date toDate, Integer toHour, Long carId) throws Exception {
        List<Driver> drivers = new ArrayList<>();
        Driver driverOccupied = carScheduleService.readDriverBySchedule(carId, fromDate, fromHour, toDate, toHour);

        List<DriverResponseDTO> dtos = new ArrayList<>();

        if (driverOccupied != null) {
            drivers.add(driverOccupied);
            dtos.add(driverResponseMapper.map(driverOccupied));
            return dtos;
        }
        else
            drivers = driverRepository.findAllByUserCompanyIdAndIsDeletedFalseOrderByUserBasicInfoFirstNameAsc(companyId);

        if (drivers != null && drivers.size() > 0) {
            for (Driver driver: drivers) {
                if (!carScheduleService.checkDriverStatus(driver.getId(), fromDate, fromHour, toDate, toHour)) {
                    dtos.add(driverResponseMapper.map(driver));
                }
            }
        }

        return dtos;
    }
}
