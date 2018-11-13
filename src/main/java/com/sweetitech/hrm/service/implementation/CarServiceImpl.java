package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Car;
import com.sweetitech.hrm.domain.dto.CarResponseDTO;
import com.sweetitech.hrm.domain.dto.page.CarPage;
import com.sweetitech.hrm.repository.CarRepository;
import com.sweetitech.hrm.service.CarService;
import com.sweetitech.hrm.service.mapping.CarMapper;
import com.sweetitech.hrm.service.mapping.CarResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarResponseMapper carResponseMapper;

    @Autowired
    private CarScheduleServiceImpl carScheduleService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car readEntity(Long id) throws Exception {
        return carRepository.getFirstById(id);
    }

    @Override
    public CarResponseDTO read(Long id) throws Exception {
        Car car = carRepository.getFirstById(id);
        if (car == null) {
            throw new EntityNotFoundException("No cars with id: " + id);
        }

        return carResponseMapper.map(car);
    }

    @Override
    public CarResponseDTO create(Car car) throws Exception {
        if (carRepository.getFirstByRegNo(car.getRegNo()) != null) {
            throw new EntityNotFoundException("A car with same registration number exists!");
        }

        return carResponseMapper.map(carRepository.save(car));
    }

    @Override
    public CarResponseDTO update(Long id, Car car) throws Exception {
        Car carOld = carRepository.getFirstById(id);
        if (carOld == null) {
            throw new EntityNotFoundException("No cars with id: " + id);
        }

        car.setId(id);
        return carResponseMapper.map(carRepository.save(car));
    }

    @Override
    public void remove(Long id) throws Exception {
        Car car = carRepository.getFirstById(id);
        if (car == null) {
            throw new EntityNotFoundException("No cars with id: " + id);
        }

        car.setDeleted(true);
        carRepository.save(car);
    }

    @Override
    public CarPage readAllActiveByCompanyId(Long companyId, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        Page<Car> cars = carRepository.findAllByCompanyIdAndIsDeletedFalseOrderByModelAsc(companyId,
                new PageRequest(page, PageAttr.PAGE_SIZE));

        List<CarResponseDTO> dtos = new ArrayList<>();
        if (cars.getContent() != null && cars.getContent().size() > 0) {
            for (Car car: cars.getContent()) {
                dtos.add(carResponseMapper.map(car));
            }
        }

        return new CarPage(dtos, cars);
    }

    @Override
    public CarPage readAllInactiveByCompanyId(Long companyId, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        Page<Car> cars = carRepository.findAllByCompanyIdAndIsDeletedTrueOrderByModelAsc(companyId,
                new PageRequest(page, PageAttr.PAGE_SIZE));

        List<CarResponseDTO> dtos = new ArrayList<>();
        if (cars.getContent() != null && cars.getContent().size() > 0) {
            for (Car car: cars.getContent()) {
                dtos.add(carResponseMapper.map(car));
            }
        }

        return new CarPage(dtos, cars);
    }

    @Override
    public CarResponseDTO reactivate(Long id) throws Exception {
        Car car = carRepository.getFirstById(id);
        if (car == null) {
            throw new EntityNotFoundException("No cars with id: " + id);
        }

        car.setDeleted(false);
        return carResponseMapper.map(carRepository.save(car));
    }

    @Override
    public CarResponseDTO updateMaintenanceStatus(Long id, boolean status) throws Exception {
        Car car = carRepository.getFirstById(id);
        if (car == null) {
            throw new EntityNotFoundException("No cars with id: " + id);
        }

        car.setMaintenance(status);
        return carResponseMapper.map(carRepository.save(car));
    }

    @Override
    public boolean exists(String regNo) throws Exception {
        return carRepository.getFirstByRegNo(regNo) != null;
    }

    @Override
    public List<CarResponseDTO> readAllAvailable(Long companyId, Date fromDate, Integer fromHour,
                                                 Date toDate, Integer toHour) throws Exception {
        List<Car> cars = carRepository.findAllByCompanyIdAndIsDeletedFalseOrderByModelAsc(companyId);

        List<CarResponseDTO> dtos = new ArrayList<>();

        if (cars != null && cars.size() > 0) {
            for (Car car: cars) {
                if (!carScheduleService.checkCarStatus(car.getId(), fromDate, fromHour, toDate, toHour)
                        && !car.isMaintenance()) {
                    dtos.add(carResponseMapper.map(car));
                }
            }
        }

        return dtos;
    }
}
