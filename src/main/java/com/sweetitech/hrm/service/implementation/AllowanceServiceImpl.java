package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.ConstantsUtil;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.*;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.repository.AllowanceRepository;
import com.sweetitech.hrm.repository.AllowanceStatusRepository;
import com.sweetitech.hrm.repository.AllowanceSummaryRepository;
import com.sweetitech.hrm.service.AllowanceService;
import com.sweetitech.hrm.service.mapping.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AllowanceServiceImpl implements AllowanceService {

    private AllowanceRepository allowanceRepository;
    private AllowanceStatusRepository allowanceStatusRepository;
    private AllowanceSummaryRepository allowanceSummaryRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private AllowanceListObjectMapper allowanceListObjectMapper;

    @Autowired
    private LeaveServiceImpl leaveService;

    @Autowired
    private OfficeHourServiceImpl officeHourService;

    @Autowired
    private HolidayServiceImpl holidayService;

    @Autowired
    private AllowanceSummaryMapper allowanceSummaryMapper;

    @Autowired
    private AllowanceCreateMapper allowanceCreateMapper;

    @Autowired
    private AllowanceEditMapper allowanceEditMapper;

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    public AllowanceServiceImpl(AllowanceRepository allowanceRepository,
                                AllowanceStatusRepository allowanceStatusRepository,
                                AllowanceSummaryRepository allowanceSummaryRepository) {
        this.allowanceRepository = allowanceRepository;
        this.allowanceStatusRepository = allowanceStatusRepository;
        this.allowanceSummaryRepository = allowanceSummaryRepository;
    }

    boolean isValidMonth(int month) {
        return (month > 0 && month <= 12);
    }

    boolean isValidYear(int year) {
        return (year >= 1800 && year <= 2100);
    }

    private void updateAllowanceOnSummaryApprove(Allowance allowance, AllowanceSummary summary) throws Exception {
        this.updateStatus(allowance.getId(), summary.getConfirmedBy().getId(), Constants.RequestStatus.APPROVED);

        allowance.setAllowanceSummary(summary);
        allowanceRepository.save(allowance);
    }

    @Override
    public Allowance read(Long id) {
        return allowanceRepository.getFirstById(id);
    }

    @Override
    public Allowance create(Allowance allowance) throws Exception {
        return allowanceRepository.save(allowance);
    }

    @Override
    public Allowance update(Long id, Allowance allowance) throws Exception {
        Allowance allowanceOld = allowanceRepository.getFirstById(id);
        if (allowanceOld == null) {
            throw new EntityNotFoundException("No allowance with id: " + id);
        }

        allowance.setId(id);
        return allowanceRepository.save(allowance);
    }

    @Override
    public Allowance updateDestination(Long id, String destination) throws Exception {
        if (destination.length() < 2) {
            throw new EntityNotFoundException("Destination must by 2 characters or more!");
        }

        Allowance allowanceOld = allowanceRepository.getFirstById(id);
        if (allowanceOld == null) {
            throw new EntityNotFoundException("No allowance with id: " + id);
        }

        allowanceOld.setDestination(destination);
        return allowanceRepository.save(allowanceOld);
    }

    @Override
    public String readStatus(Long id) throws Exception {
        Allowance allowance = this.read(id);
        if (allowance == null) {
            throw new EntityNotFoundException("No allowance with id: " + id);
        }

        return allowance.getAllowanceStatus().getStatus();
    }

    @Override
    public void cancel(Long id) throws Exception {
        Allowance allowance = allowanceRepository.getFirstById(id);
        if (allowance == null) {
            throw new EntityNotFoundException("No allowance with id: " + id);
        }

        if (!this.readStatus(id).equals(Constants.RequestStatus.REQUESTED)) {
            throw new EntityNotFoundException("Allowance cannot be cancelled now!");
        }

        long statusId = allowance.getAllowanceStatus().getId();

        AllowanceStatus status = allowanceStatusRepository.getFirstById(statusId);
        if (status ==  null) {
            throw new EntityNotFoundException("No status found for this allowance!");
        }

        status.setStatus(Constants.RequestStatus.CANCELLED);
        status.setStatusDate(new Date());

        allowanceStatusRepository.save(status);
    }

    @Override
    public Allowance updateStatus(Long id, Long userId, String status) throws Exception {
        Allowance allowance = allowanceRepository.getFirstById(id);
        if (allowance == null) {
            throw new EntityNotFoundException("No allowance with id: " + id);
        }

        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status value!");
        }

        long statusId = allowance.getAllowanceStatus().getId();

        AllowanceStatus allowanceStatus = allowanceStatusRepository.getFirstById(statusId);
        if (allowanceStatus ==  null) {
            throw new EntityNotFoundException("No status found for this allowance!");
        }

        allowanceStatus.setStatus(status);
        allowanceStatus.setStatusDate(new Date());
        allowanceStatus.setDecisionBy(user);

        allowanceStatusRepository.save(allowanceStatus);

        return allowanceRepository.getFirstById(id);
    }

    @Override
    public Page<Allowance> readAllRequested(Long companyId, String status, Integer page) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        if (!status.equals(Constants.RequestStatus.REQUESTED)
                && !status.equals(Constants.RequestStatus.FORWARDED)
                && !status.equals(Constants.RequestStatus.APPROVED)
                && !status.equals(Constants.RequestStatus.DECLINED)
                && !status.equals(Constants.RequestStatus.CANCELLED)) {
            throw new EntityNotFoundException("Invalid status value!");
        }

        return allowanceRepository.findAllRequested(status, companyId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<Allowance> readAllRequestedByUserId(Long userId, String status, Integer page) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (!status.equals(Constants.RequestStatus.REQUESTED)
                && !status.equals(Constants.RequestStatus.FORWARDED)
                && !status.equals(Constants.RequestStatus.APPROVED)
                && !status.equals(Constants.RequestStatus.DECLINED)
                && !status.equals(Constants.RequestStatus.CANCELLED)) {
            throw new EntityNotFoundException("Invalid status value!");
        }

        return allowanceRepository.findAllRequestedByUser(status, userId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<Allowance> readAllByUserId(Long userId, Integer page) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        return allowanceRepository.findAllByUser(userId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<Allowance> readAllDecided(Long companyId, String status1, String status2, Integer page) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        if (!ConstantsUtil.isValidStatus(status1)) {
            throw new EntityNotFoundException("Invalid status value!");
        }

        if (!ConstantsUtil.isValidStatus(status2)) {
            throw new EntityNotFoundException("Invalid status value!");
        }

        return allowanceRepository.findAllDecided(status1, status2, companyId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public List<AllowanceListObjectDTO> readForMonthByUserId(Long userId, Integer month, Integer year) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (!isValidMonth(month) || !isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year found!");
        }

        List<Allowance> allowances = allowanceRepository.findMonthlyApprovedByUser(Constants.RequestStatus.APPROVED,
                Constants.RequestStatus.REQUESTED, userId, year, month);
//        return allowanceRepository.findMonthlyApprovedByUser(userId, year, month);

        List<AllowanceListObjectDTO> dtos = new ArrayList<>();
        for (Allowance allowance : allowances) {
            dtos.add(allowanceListObjectMapper.map(allowance));
        }

        return dtos;
    }

    @Override
    public Page<Allowance> readAllByStatusAndCompany(Long companyId, String status, Integer page) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status value!");
        }

        return allowanceRepository.findAllByStatusAndCompany(status, companyId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<Allowance> readAllByStatusAndUser(Long userId, String status, Integer page) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (!ConstantsUtil.isValidStatus(status)) {
            throw new EntityNotFoundException("Invalid status value!");
        }

        return allowanceRepository.findAllByStatusAndUser(status, userId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public AllowanceSummary approveSummary(AllowanceSummary allowanceSummary) throws Exception {
        if (allowanceSummaryRepository.getFirstByMonthAndYearAndPreparedForId(allowanceSummary.getMonth(),
                allowanceSummary.getYear(), allowanceSummary.getPreparedFor().getId()) != null) {
            throw new EntityNotFoundException("A summary already exists with these details!");
        }

        AllowanceSummary summary = allowanceSummaryRepository.save(allowanceSummary);
        if (summary == null) {
            throw new EntityNotFoundException("Failed to create summary!");
        }

        List<Allowance> allowances = allowanceRepository.findMonthlyApprovedByUser(Constants.RequestStatus.APPROVED,
                Constants.RequestStatus.REQUESTED, summary.getPreparedFor().getId(), summary.getYear(), summary.getMonth());

        if (allowances != null && allowances.size() > 0) {
            for (Allowance allowance: allowances) {
                updateAllowanceOnSummaryApprove(allowance, summary);
            }
        }

        return summary;
    }

    @Override
    public AllowanceSummary updateSummary(Long id, AllowanceSummary allowanceSummary) throws Exception {
        AllowanceSummary summary = allowanceSummaryRepository.getFirstById(id);
        if (summary == null) {
            throw new EntityNotFoundException("No summary with id: " + id);
        }

        allowanceSummary.setId(id);
        allowanceSummary.setMonth(summary.getMonth());
        allowanceSummary.setYear(summary.getYear());
        summary = allowanceSummaryRepository.save(allowanceSummary);
        if (summary == null) {
            throw new EntityNotFoundException("Failed to create summary!");
        }

        List<Allowance> allowances = allowanceRepository.findMonthlyApprovedByUser(Constants.RequestStatus.APPROVED,
                Constants.RequestStatus.REQUESTED, summary.getPreparedFor().getId(), summary.getYear(), summary.getMonth());

        if (allowances != null && allowances.size() > 0) {
            for (Allowance allowance: allowances) {
                updateAllowanceOnSummaryApprove(allowance, summary);
            }
        }

        return summary;
    }

    @Override
    public AllowanceSummary getSummary(Integer month, Integer year, Long userId) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (!isValidMonth(month) || !isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year found!");
        }

        return allowanceSummaryRepository.getFirstByMonthAndYearAndPreparedForId(month, year, userId);
    }

    @Override
    public Allowance confirmBySummary(Long allowanceId, Long summaryId) throws Exception {
        Allowance allowance = allowanceRepository.getFirstById(allowanceId);
        if (allowance == null) {
            throw new EntityNotFoundException("No allowance with id: " + allowanceId);
        }

        AllowanceSummary summary = allowanceSummaryRepository.getFirstById(summaryId);
        if (summary == null) {
            throw new EntityNotFoundException("No summary with id: " + summaryId);
        }

        allowance.setAllowanceSummary(summary);
        return allowanceRepository.save(allowance);
    }

    @Override
    public AllowanceMonthlyDTO getMonthlyAllowanceSummaryByUser(Long userId, Integer month, Integer year) throws Exception {
        AllowanceMonthlyDTO dto = new AllowanceMonthlyDTO();

        if (this.getSummary(month, year, userId) != null)
            dto.setAllowanceSummary(allowanceSummaryMapper.map(this.getSummary(month, year, userId)));

        dto.setAllowances(this.readForMonthByUserId(userId, month, year));

        dto.setLeaves(leaveService.getAllMonthlyLeaveSummary(userId, year, month));

        dto.setOfficeHours(officeHourService.readAllDatesByWeekends(month, year));

        dto.setHolidays(holidayService.fetchDTOByMonthAndYear(month,year, userId));

        return dto;
    }

    @Override
    public List<AllowanceListObjectDTO> bulkRequestAllowances(AllowanceListDTO allowances) throws Exception {
        List<AllowanceListObjectDTO> dtos = new ArrayList<>();

        if (allowances != null && allowances.getAllowances().size() > 0) {
            for (AllowanceDTO allowance: allowances.getAllowances()) {
                if (allowance.getAllowanceId() == null) {
                    dtos.add(allowanceListObjectMapper.map(this.create(allowanceCreateMapper.map(allowance))));
                } else {
                    allowance.setStatus(Constants.RequestStatus.REQUESTED);
                    dtos.add(allowanceListObjectMapper.map(this.update(allowance.getAllowanceId(),
                            allowanceCreateMapper.map(allowance))));
                }
            }
        }

        return dtos;
    }

    @Override
    public List<UserSmallResponseDTO> listUsersByAllowanceRequestsForMonth(Long companyId,
                                                                           Long departmentId,
                                                                           Long officeCodeId,
                                                                           Integer month,
                                                                           Integer year) throws Exception {
        if (!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        List<User> users = userService.listActiveUsers(companyId, departmentId, officeCodeId);

        List<UserSmallResponseDTO> dtos = new ArrayList<>();

        if (users != null && users.size() > 0) {
            for (User user: users) {
                if (allowanceRepository.getFirstByUserIdAndMonthAndYear(user.getId(), year, month) != null) {
                    dtos.add(userSmallResponseMapper.map(user));
                }
            }
        }

        return dtos;
    }

    @Override
    public Allowance checkIfExistsForUserAndDate(Long userId,
                                                 Integer day,
                                                 Integer month,
                                                 Integer year) throws Exception {
        if (!DateValidator.isValidDay(day) || !DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid date!");
        }

        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        return allowanceRepository.findDailyApprovedByUser(Constants.RequestStatus.APPROVED,
                Constants.RequestStatus.REQUESTED, userId, year, month, day);
    }
}
