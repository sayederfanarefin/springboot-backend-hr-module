package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.domain.*;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.service.implementation.CompanyServiceImpl;
import com.sweetitech.hrm.service.implementation.NotificationServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NotificationMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    private CompanySmallResponseMapper companySmallResponseMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private NotificationServiceImpl notificationService;

    /**
     *
     * @param entity
     * @return dto
     */
    public NotificationResponseDTO map(Notification entity) throws Exception {
        NotificationResponseDTO dto = new NotificationResponseDTO();

        dto.setNotificationId(entity.getId());

        if (entity.getUser() != null)
            dto.setUser(userSmallResponseMapper.map(entity.getUser()));

        dto.setForAll(entity.isForAll());

        dto.setCompany(companySmallResponseMapper.map(entity.getCompany()));

        dto.setResourceType(entity.getResourceType());
        dto.setResourceId(entity.getResourceId());

        dto.setTitle(entity.getTitle());

        dto.setBody(entity.getBody());
        dto.setCreatedOn(entity.getCreatedOn());

        return dto;
    }

    /**
     *
     * @param dto
     * @return entity
     */
    public Notification map(NotificationDTO dto) throws Exception {
        if (dto.getUserId() != null) {
            if (userService.read(dto.getUserId()) == null) {
                throw new EntityNotFoundException("No users with id: " + dto.getUserId());
            }
        }

        if (dto.getCompanyId() != null) {
            if (companyService.read(dto.getCompanyId()) == null) {
                throw new EntityNotFoundException("No companies with id: " + dto.getCompanyId());
            }
        }

        if (dto.isForAll() && dto.getCompanyId() == null) {
            throw new EntityNotFoundException("Company not present for forAll notification!");
        }

        if (!dto.isForAll() && dto.getUserId() == null) {
            throw new EntityNotFoundException("Receiver of notification absent!");
        }

        Notification entity = new Notification();

        entity.setCompany(companyService.read(dto.getCompanyId()));
        entity.setResourceType(dto.getResourceType());

        if (dto.getResourceId() != null)
            entity.setResourceId(dto.getResourceId());
        else entity.setResourceId((long) -1);

        entity.setForAll(dto.isForAll());

        if (!dto.getResourceType().equals(Constants.ResourceType.ATTENDANCE)) {
            entity.setTitle(dto.getTitle());
        } else {
            if (DateTimeUtils.getMonth(dto.getTitle()).isEmpty()) {
                entity.setTitle(dto.getTitle());
            } else {
                int count = 1;
                count += notificationService.getWarningCount(dto.getUserId(),
                        DateTimeUtils.getMonth(dto.getTitle()));

                entity.setTitle("Warning " + count + " regarding attendance of month " + DateTimeUtils.getMonth(dto.getTitle()));
            }
        }

        entity.setBody(dto.getBody());

        if (!dto.isForAll()) {
            entity.setUser(userService.read(dto.getUserId()));
        }

        return entity;
    }

    /**
     * For tasks
     * @param object
     * @return entity
     */
    public Notification map(Task object, boolean forAll, Long userId, Long companyId) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        Notification entity = new Notification();

        entity.setCompany(companyService.read(companyId));
        entity.setResourceType(Constants.ResourceType.TASK);
        entity.setResourceId(object.getId());
        entity.setForAll(forAll);

        String title = "Task by " + object.getAssignedBy().getBasicInfo().getFirstName();
        if (object.getAssignedBy().getBasicInfo().getMiddleName() != null)
            title = title + " " + object.getAssignedBy().getBasicInfo().getMiddleName();
        title = title + " " + object.getAssignedBy().getBasicInfo().getLastName();

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);

        Date temp = object.getScheduledDate();
        Date date = sdf.parse(sdf1.format(temp));

        entity.setTitle(title);
        entity.setBody(object.getDescription() + " for " + sdf.format(date));

        if (!forAll) {
            if (userService.read(userId) == null) {
                throw new EntityNotFoundException("No users with id: " + userId);
            }

            entity.setUser(userService.read(userId));
        }

        return entity;
    }

    /**
     * For tenders
     * @param object
     * @return entity
     */
    public Notification map(List<TenderResponseDTO> object, Long userId) throws Exception {
        Notification entity = new Notification();

        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }
        UserSmallResponseDTO userDTO = userSmallResponseMapper.map(user);

        Company company = user.getCompany();
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + user.getCompany().getId());
        }

        entity.setCompany(company);
        entity.setResourceType(Constants.ResourceType.TENDER);
        entity.setResourceId((long) -1);
        entity.setForAll(false);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);

        String title = object.size() + " tenders are nearing expiry";
        entity.setTitle(title);

        StringBuilder body = new StringBuilder("The following tenders are nearing expiry: \n");

        for (TenderResponseDTO tender: object) {
            body.append(tender.getInstitutionName()).append(" ");
            if (tender.getTenderSecurityReleaseDate() != null) {
                body.append("with tender security release date ");
                body.append(sdf.parse(sdf1.format(tender.getTenderSecurityReleaseDate())));
                body.append("\n");
            }
            else if (tender.getPerformanceSecurityReleaseDate() != null) {
                body.append("with performance security release date ");
                body.append(sdf.parse(sdf1.format(tender.getPerformanceSecurityReleaseDate())));
                body.append("\n");
            }
        }

        body.append("Please take necessary actions.");

        entity.setBody(body.toString());

        entity.setUser(user);

        return entity;
    }

    /**
     * For bonuses
     * @param object
     * @return entity
     */
    public Notification map(BonusResponseDTO object, String operation) throws Exception {
        Notification entity = new Notification();

        User user = userService.read(object.getPaidToUser().getUserId());
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + object.getPaidToUser().getUserId());
        }
        UserSmallResponseDTO userDTO = userSmallResponseMapper.map(user);

        Company company = companyService.read(user.getCompany().getId());
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + user.getCompany().getId());
        }

        entity.setCompany(company);
        entity.setResourceType(Constants.ResourceType.BONUS);
        entity.setResourceId(object.getBonusId());
        entity.setForAll(false);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);

        Date temp = object.getDateOfOrder();
        Date dateOfOrder = sdf.parse(sdf1.format(temp));

        String title = "Bonus " + operation + " by " + object.getApprovedByUser().getName();
        entity.setTitle(title);

        String body = "Bonus " + operation + " on " + sdf.format(dateOfOrder) + " by " + object.getApprovedByUser().getName() + " " +
                "for " + object.getTypeOfBonus().getName() + " in " + object.getYear() +
                " of amount BDT " + object.getAmount() + ".";
        entity.setBody(body);

        entity.setUser(user);

        return entity;
    }

    /**
     * For bonuses
     * @param object
     * @return entity
     */
    public Notification map(CommissionResponseDTO object, String operation) throws Exception {
        Notification entity = new Notification();

        User user = userService.read(object.getPaidToUser().getUserId());
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + object.getPaidToUser().getUserId());
        }
        UserSmallResponseDTO userDTO = userSmallResponseMapper.map(user);

        Company company = companyService.read(user.getCompany().getId());
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + user.getCompany().getId());
        }

        entity.setCompany(company);
        entity.setResourceType(Constants.ResourceType.COMMISSION);
        entity.setResourceId(object.getCommissionId());
        entity.setForAll(false);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);

        Date temp = object.getDateOfOrder();
        Date dateOfOrder = sdf.parse(sdf1.format(temp));

        String title = "Commission " + operation + " by " + object.getApprovedByUser().getName();
        entity.setTitle(title);

        String body = "Commission " + operation + " on " + sdf.format(dateOfOrder) + " by " + object.getApprovedByUser().getName() + " " +
                "for " + object.getTypeOfCommission().getName() + " in " + object.getYear();

        if (object.getLocalCommission() != null)
               body = body + " of amount BDT " + object.getAmount() + "(Local).";
        if (object.getForeignCommission() != null)
            body = body + " of amount BDT " + object.getAmount() + "(Foreign).";

        entity.setBody(body);

        entity.setUser(user);

        return entity;
    }

    /**
     * For Leave Request
     * @param object
     * @return entity
     */
    public Notification map(Leave object, Long userId) throws Exception {
        Notification entity = new Notification();

        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + object.getRequestedBy().getId());
        }
        UserSmallResponseDTO userDTO = userSmallResponseMapper.map(object.getRequestedBy());

        Company company = companyService.read(object.getRequestedBy().getCompany().getId());
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + object.getRequestedBy().getCompany().getId());
        }

        entity.setCompany(company);
        entity.setResourceType(Constants.ResourceType.LEAVE);
        entity.setResourceId(object.getId());
        entity.setForAll(false);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);

        Date temp = object.getFromDate();
        Date dateStart = sdf.parse(sdf1.format(temp));
        temp = object.getToDate();
        Date dateEnd = sdf.parse(sdf1.format(temp));
        temp = object.getRequestedOn();
        Date requestedOn = sdf.parse(sdf1.format(temp));

        String title = "Leave requested by " + userDTO.getName();
        entity.setTitle(title);

        String body = "Leave requested on " + sdf.format(requestedOn) + " by " + userDTO.getName() + ". " +
                "Leave will start on " + sdf.format(dateStart) +
                " and will end on " + sdf.format(dateEnd) + " for " + object.getTotalDays() + " days.";
        entity.setBody(body);

        entity.setUser(user);

        return entity;
    }

    /**
     * For Accepting or Declining Leave Request
     * @param object
     * @return entity
     */
    public Notification map(Leave object, String status) throws Exception {
        Notification entity = new Notification();

        User user = userService.read(object.getRequestedBy().getId());
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + object.getRequestedBy().getId());
        }
        UserSmallResponseDTO userDTO = userSmallResponseMapper.map(object.getRequestedBy());

        Company company = companyService.read(object.getRequestedBy().getCompany().getId());
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + object.getRequestedBy().getCompany().getId());
        }

        entity.setCompany(company);
        entity.setResourceType(Constants.ResourceType.LEAVE);
        entity.setResourceId(object.getId());
        entity.setForAll(false);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);

        Date temp = object.getFromDate();
        Date dateStart = sdf.parse(sdf1.format(temp));
        temp = object.getToDate();
        Date dateEnd = sdf.parse(sdf1.format(temp));
        temp = object.getRequestedOn();
        Date requestedOn = sdf.parse(sdf1.format(temp));

        String title = "Leave requested by " + userDTO.getName();
        entity.setTitle(title);

        String body = "Leave requested on " + sdf.format(requestedOn) + " by " + userDTO.getName() + " " +
                "has been " + status + ". " +
                "Leave start date is " + sdf.format(dateStart) +
                " and end date is " + sdf.format(dateEnd) + " for " + object.getTotalDays() + " days.";
        entity.setBody(body);

        entity.setUser(user);

        return entity;
    }

    /**
     * For car request
     * @param object
     * @return entity
     */
    public Notification map(CarRequestResponseDTO object, Long userId) throws Exception {
        Notification entity = new Notification();

        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + object.getRequestedByUser().getUserId());
        }

        Company company = companyService.read(object.getRequestedByUser().getCompanyId());
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + object.getRequestedByUser().getCompanyId());
        }

        entity.setCompany(company);
        entity.setResourceType(Constants.ResourceType.CAR_REQUEST);
        entity.setResourceId(object.getCarRequestId());
        entity.setForAll(false);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);

        Date temp = object.getRequestedFrom();
        Date dateStart = sdf.parse(sdf1.format(temp));
        temp = object.getRequestedTo();
        Date dateEnd = sdf.parse(sdf1.format(temp));
        temp = object.getRequestedFrom();
        Date requestedOn = sdf.parse(sdf1.format(temp));

        String title = "Car requested by " + object.getRequestedByUser().getName();
        entity.setTitle(title);

        String body = "Car requested on " + sdf.format(requestedOn) + " by " + object.getRequestedByUser().getName() + ". " +
                "Requested trip will start on " + sdf.format(dateStart) + " at " + object.getFromHour() + " " +
                "hours and will end on " + sdf.format(dateEnd) + " at " + object.getToHour() + " hours.";
        entity.setBody(body);

        entity.setUser(user);

        return entity;
    }

    /**
     * For cancellation of approved car request
     * @param object
     * @return entity
     */
    public Notification map(CarScheduleResponseDTO object, Long userId) throws Exception {
        Notification entity = new Notification();

        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        Company company = companyService.read(object.getCarRequest().getRequestedByUser().getCompanyId());
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + object.getCarRequest().getRequestedByUser().getCompanyId());
        }

        entity.setCompany(company);
        entity.setResourceType(Constants.ResourceType.CAR_SCHEDULE);
        entity.setResourceId(object.getCarScheduleId());
        entity.setForAll(false);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);

        Date temp = object.getCarRequest().getRequestedFrom();
        Date dateStart = sdf.parse(sdf1.format(temp));
        temp = object.getCarRequest().getRequestedTo();
        Date dateEnd = sdf.parse(sdf1.format(temp));
        temp = object.getCarRequest().getRequestedFrom();
        Date requestedOn = sdf.parse(sdf1.format(temp));

        String title = "Car requested cancelled by " + userSmallResponseMapper.map(user).getName();
        entity.setTitle(title);

        String body = "Car requested on " + sdf.format(requestedOn) + " by user " + userSmallResponseMapper.map(user).getName() +
                "has been cancelled by user," +
                "freeing car " + object.getCar().getRegNo() + " and driver " + object.getDriver().getUser().getName() + " " +
                "for the scheduled trip that starts on " + sdf.format(dateStart) + " at " + object.getCarRequest().getFromHour() + " " +
                "hours and ends on " + sdf.format(dateEnd) + " at " + object.getCarRequest().getToHour() + " hours.";

        entity.setBody(body);

        entity.setUser(user);

        return entity;
    }

    /**
     * For approval of car request
     * @param object
     * @return entity
     */
    public Notification map(CarScheduleResponseDTO object) throws Exception {
        Notification entity = new Notification();

        User user = userService.read(object.getCarRequest().getRequestedByUser().getUserId());
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + object.getCarRequest().getRequestedByUser().getUserId());
        }

        Company company = companyService.read(object.getCarRequest().getRequestedByUser().getCompanyId());
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + object.getCarRequest().getRequestedByUser().getCompanyId());
        }

        entity.setCompany(company);
        entity.setResourceType(Constants.ResourceType.CAR_SCHEDULE);
        entity.setResourceId(object.getCarScheduleId());
        entity.setForAll(false);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);

        Date temp = object.getCarRequest().getRequestedFrom();
        Date dateStart = sdf.parse(sdf1.format(temp));
        temp = object.getCarRequest().getRequestedTo();
        Date dateEnd = sdf.parse(sdf1.format(temp));
        temp = object.getCarRequest().getRequestedFrom();
        Date requestedOn = sdf.parse(sdf1.format(temp));

        String title = "Car request accepted for " + sdf.format(requestedOn);
        entity.setTitle(title);

        String body = "Car requested on " + sdf.format(requestedOn) + " has been approved. " +
                "Use car " + object.getCar().getRegNo() + " and driver " + object.getDriver().getUser().getName() + ". " +
                "Your scheduled trip starts on " + sdf.format(dateStart) + " at " + object.getCarRequest().getFromHour() + " " +
                "hours and ends on " + sdf.format(dateEnd) + " at " + object.getCarRequest().getToHour() + " hours.";
        entity.setBody(body);

        entity.setUser(user);

        return entity;
    }

    /**
     * For Tour Request
     * @param object
     * @return entity
     */
    public Notification map(Tour object, Long userId) throws Exception {
        Notification entity = new Notification();

        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + object.getRequestedBy().getId());
        }
        UserSmallResponseDTO userDTO = userSmallResponseMapper.map(object.getRequestedBy());

        Company company = companyService.read(object.getRequestedBy().getCompany().getId());
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + object.getRequestedBy().getCompany().getId());
        }

        entity.setCompany(company);
        entity.setResourceType(Constants.ResourceType.TOUR);
        entity.setResourceId(object.getId());
        entity.setForAll(false);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);

        Date temp = object.getFromDate();
        Date dateStart = sdf.parse(sdf1.format(temp));
        temp = object.getToDate();
        Date dateEnd = sdf.parse(sdf1.format(temp));
        temp = object.getCreatedOn();
        Date createdOn = sdf.parse(sdf1.format(temp));

        String title = "Tour requested by " + userDTO.getName();
        entity.setTitle(title);

        String body = "Tour requested on " + sdf.format(createdOn) + " by " + userDTO.getName() + ". " +
                "Tour will start on " + sdf.format(dateStart) +
                " and will end on " + sdf.format(dateEnd) + " for " + object.getDuration() + " days.";
        entity.setBody(body);

        entity.setUser(user);

        return entity;
    }

    /**
     * For Accepting or Declining Tour Request
     * @param object
     * @return entity
     */
    public Notification map(Tour object, String status) throws Exception {
        Notification entity = new Notification();

        User user = userService.read(object.getRequestedBy().getId());
        if (user == null) {
            throw new EntityNotFoundException("No users with id: " + object.getRequestedBy().getId());
        }
        UserSmallResponseDTO userDTO = userSmallResponseMapper.map(object.getRequestedBy());

        Company company = companyService.read(object.getRequestedBy().getCompany().getId());
        if (company == null) {
            throw new EntityNotFoundException("No companies with id: " + object.getRequestedBy().getCompany().getId());
        }

        entity.setCompany(company);
        entity.setResourceType(Constants.ResourceType.TOUR);
        entity.setResourceId(object.getId());
        entity.setForAll(false);

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);

        Date temp = object.getFromDate();
        Date dateStart = sdf.parse(sdf1.format(temp));
        temp = object.getToDate();
        Date dateEnd = sdf.parse(sdf1.format(temp));
        temp = object.getCreatedOn();
        Date createdOn = sdf.parse(sdf1.format(temp));

        String title = "Tour requested by " + userDTO.getName();
        entity.setTitle(title);

        String body = "Tour requested on " + sdf.format(createdOn) + " by " + userDTO.getName() + " " +
                "has been " + status + ". " +
                "Tour start date is " + sdf.format(dateStart) +
                " and end date is " + sdf.format(dateEnd) + " for " + object.getDuration() + " days.";
        entity.setBody(body);

        entity.setUser(user);

        return entity;
    }

}
