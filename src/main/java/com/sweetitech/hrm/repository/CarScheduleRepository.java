package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.CarSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CarScheduleRepository extends CrudRepository<CarSchedule, Long> {

    CarSchedule getFirstById(Long id);

    Page<CarSchedule> findAllByCarRequestRequestedByCompanyIdAndCarRequestRequestedFromOrderByCarRequestRequestedFromDesc(Long companyId, Date requestedFrom, Pageable pageable);

    @Query(value = "SELECT * FROM car_schedules cs WHERE (SELECT company_id FROM users WHERE id = (SELECT requested_by_id FROM car_requests WHERE id = cs.car_request_id)) = :companyId AND DAY((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id)) = :day AND MONTH((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id)) = :month AND YEAR((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id)) = :year ORDER BY (SELECT requested_from FROM car_requests WHERE id = cs.car_request_id) DESC", nativeQuery = true)
    Page<CarSchedule> findAllByCompanyIdAndDate(@Param("companyId") Long companyId,
                                                @Param("day") Integer day,
                                                @Param("month") Integer month,
                                                @Param("year") Integer year,
                                                Pageable pageable);

    @Query(value = "SELECT * FROM car_schedules cs WHERE (SELECT company_id FROM users WHERE id = (SELECT requested_by_id FROM car_requests WHERE id = cs.car_request_id)) = :companyId AND cs.car_id = :carId AND DAY((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id)) = :day AND MONTH((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id)) = :month AND YEAR((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id)) = :year AND (((SELECT from_hour FROM car_requests WHERE id = cs.car_request_id) <= :fromHour) AND (SELECT to_hour FROM car_requests WHERE id = cs.car_request_id) >= :fromHour) OR (((SELECT from_hour FROM car_requests WHERE id = cs.car_request_id) <= :toHour) AND (SELECT to_hour FROM car_requests WHERE id = cs.car_request_id) >= :toHour) LIMIT 1", nativeQuery = true)
    CarSchedule checkOccupiedCar(@Param("companyId") Long companyId,
                                 @Param("carId") Long carId,
                                 @Param("day") Integer day,
                                 @Param("month") Integer month,
                                 @Param("year") Integer year,
                                 @Param("fromHour") Integer fromHour,
                                 @Param("toHour") Integer toHour);

    @Query(value = "SELECT * FROM car_schedules cs WHERE cs.car_id = :carId AND (DAY((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id)) = :day) AND (MONTH((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id)) = :month) AND (YEAR((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id)) = :year)", nativeQuery = true)
    List<CarSchedule> findAllByCarAndDate(@Param("carId") Long carId,
                                          @Param("day") Integer day,
                                          @Param("month") Integer month,
                                          @Param("year") Integer year);

    CarSchedule getFirstByCarIdAndCarRequestRequestedFromBeforeAndCarRequestRequestedToAfter(Long carId, Date fDate, Date tDate);

    @Query(value = "SELECT * FROM car_schedules cs WHERE cs.car_id = :carId AND ((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id) > :fDate) AND ((SELECT requested_to FROM car_requests WHERE id = cs.car_request_id) < :tDate) LIMIT 1", nativeQuery = true)
    CarSchedule dateCondition1(@Param("carId") Long carId,
                               @Param("fDate") Date fDate,
                               @Param("tDate") Date tDate);

    @Query(value = "SELECT * FROM car_schedules cs WHERE cs.car_id = :carId AND ((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id) > :fDate) AND ((SELECT requested_to FROM car_requests WHERE id = cs.car_request_id) >= :tDate) LIMIT 1", nativeQuery = true)
    CarSchedule dateCondition2(@Param("carId") Long carId,
                               @Param("fDate") Date fDate,
                               @Param("tDate") Date tDate);

    @Query(value = "SELECT * FROM car_schedules cs WHERE cs.car_id = :carId AND ((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id) <= :fDate) AND ((SELECT requested_to FROM car_requests WHERE id = cs.car_request_id) < :tDate) LIMIT 1", nativeQuery = true)
    CarSchedule dateCondition3(@Param("carId") Long carId,
                               @Param("fDate") Date fDate,
                               @Param("tDate") Date tDate);

    @Query(value = "SELECT * FROM car_schedules cs WHERE cs.car_id = :carId AND ((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id) = :fDate) AND ((SELECT requested_to FROM car_requests WHERE id = cs.car_request_id) = :tDate) LIMIT 1", nativeQuery = true)
    CarSchedule dateCondition4(@Param("carId") Long carId,
                               @Param("fDate") Date fDate,
                               @Param("tDate") Date tDate);

    @Query(value = "SELECT * FROM car_schedules cs WHERE cs.car_id = :carId AND ((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id) < :fDate) AND ((SELECT requested_to FROM car_requests WHERE id = cs.car_request_id) > :tDate) LIMIT 1", nativeQuery = true)
    CarSchedule dateCondition5(@Param("carId") Long carId,
                               @Param("fDate") Date fDate,
                               @Param("tDate") Date tDate);

    CarSchedule getFirstByCarId(Long carId);

    @Query(value = "SELECT * FROM car_schedules cs WHERE cs.driver_id = :driverId AND (DAY((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id)) = :day) AND (MONTH((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id)) = :month) AND (YEAR((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id)) = :year)", nativeQuery = true)
    List<CarSchedule> findAllByDriverAndDate(@Param("driverId") Long driverId,
                                          @Param("day") Integer day,
                                          @Param("month") Integer month,
                                          @Param("year") Integer year);

    @Query(value = "SELECT * FROM car_schedules cs WHERE cs.driver_id = :driverId AND ((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id) > :fDate) AND ((SELECT requested_to FROM car_requests WHERE id = cs.car_request_id) < :tDate) LIMIT 1", nativeQuery = true)
    CarSchedule dateDriverCondition1(@Param("driverId") Long driverId,
                               @Param("fDate") Date fDate,
                               @Param("tDate") Date tDate);

    @Query(value = "SELECT * FROM car_schedules cs WHERE cs.driver_id = :driverId AND ((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id) > :fDate) AND ((SELECT requested_to FROM car_requests WHERE id = cs.car_request_id) >= :tDate) LIMIT 1", nativeQuery = true)
    CarSchedule dateDriverCondition2(@Param("driverId") Long driverId,
                               @Param("fDate") Date fDate,
                               @Param("tDate") Date tDate);

    @Query(value = "SELECT * FROM car_schedules cs WHERE cs.driver_id = :driverId AND ((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id) <= :fDate) AND ((SELECT requested_to FROM car_requests WHERE id = cs.car_request_id) < :tDate) LIMIT 1", nativeQuery = true)
    CarSchedule dateDriverCondition3(@Param("driverId") Long driverId,
                               @Param("fDate") Date fDate,
                               @Param("tDate") Date tDate);

    @Query(value = "SELECT * FROM car_schedules cs WHERE cs.driver_id = :driverId AND ((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id) = :fDate) AND ((SELECT requested_to FROM car_requests WHERE id = cs.car_request_id) = :tDate) LIMIT 1", nativeQuery = true)
    CarSchedule dateDriverCondition4(@Param("driverId") Long driverId,
                               @Param("fDate") Date fDate,
                               @Param("tDate") Date tDate);

    @Query(value = "SELECT * FROM car_schedules cs WHERE cs.driver_id = :driverId AND ((SELECT requested_from FROM car_requests WHERE id = cs.car_request_id) < :fDate) AND ((SELECT requested_to FROM car_requests WHERE id = cs.car_request_id) > :tDate) LIMIT 1", nativeQuery = true)
    CarSchedule dateDriverCondition5(@Param("driverId") Long driverId,
                               @Param("fDate") Date fDate,
                               @Param("tDate") Date tDate);

    CarSchedule getFirstByDriverId(Long driverId);

    Page<CarSchedule> findAllByCarIdOrderByCarRequestRequestedFromDesc(Long carId, Pageable pageable);

    Page<CarSchedule> findAllByDriverIdOrderByCarRequestRequestedFromDesc(Long driverId, Pageable pageable);

    CarSchedule getFirstByCarRequestId(Long carRequestId);

    Page<CarSchedule> findAllByCarRequestRequestedByCompanyIdAndCompletedOnNotNullOrderByCarRequestRequestedFromDesc(Long companyId,
                                                                                                                     Pageable pageable);

    Page<CarSchedule> findAllByCarRequestRequestedByCompanyIdAndCompletedOnNullOrderByCarRequestRequestedFromDesc(Long companyId,
                                                                                                                  Pageable pageable);

    Page<CarSchedule> findAllByCarRequestRequestedByIdOrderByCarRequestRequestedFromDesc(Long userId, Pageable pageable);
}
