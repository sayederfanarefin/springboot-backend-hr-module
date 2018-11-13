package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.ConstantsUtil;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.Commission;
import com.sweetitech.hrm.domain.TypeOfCommission;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.repository.CommissionRepository;
import com.sweetitech.hrm.service.CommissionService;
import com.sweetitech.hrm.service.PushNotificationService;
import com.sweetitech.hrm.service.mapping.CommissionMapper;
import com.sweetitech.hrm.service.mapping.CommissionResponseMapper;
import com.sweetitech.hrm.service.mapping.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommissionServiceImpl implements CommissionService {

    private CommissionRepository commissionRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private OfficeCodeServiceImpl officeCodeService;

    @Autowired
    private TypeOfCommissionServiceImpl typeOfCommissionService;

    @Autowired
    private CommissionResponseMapper commissionResponseMapper;

    @Autowired
    private CommissionMapper commissionMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    public CommissionServiceImpl(CommissionRepository commissionRepository) {
        this.commissionRepository = commissionRepository;
    }

    @Override
    public CommissionResponseDTO create(Commission commission) throws Exception {
        return commissionResponseMapper.map(commissionRepository.save(commission));
    }

    @Override
    public CommissionResponseDTO update(Long id, Commission commission) throws Exception {
        Commission commissionOld = commissionRepository.getFirstById(id);
        if (commissionOld == null) {
            throw new EntityNotFoundException("No commissions with id: " + id);
        }

        commission.setId(commissionOld.getId());
        return commissionResponseMapper.map(commissionRepository.save(commission));
    }

    @Override
    public CommissionResponseDTO read(Long id) throws Exception {
        return commissionResponseMapper.map(commissionRepository.getFirstById(id));
    }

    @Override
    public CommissionResponseDTO updatePaymentDate(Long id, Date paymentDate) throws Exception {
        Commission commission = commissionRepository.getFirstById(id);
        if (commission == null) {
            throw new EntityNotFoundException("No commissions with id: " + id);
        }

        if (!DateTimeUtils.isValidDate(paymentDate.toString(), Constants.Dates.VALID_FORMAT)) {
            throw new EntityNotFoundException("Invalid date!");
        }

        commission.setDateOfPayment(paymentDate);
        return commissionResponseMapper.map(commissionRepository.save(commission));
    }

    @Override
    public void remove(Long id) throws Exception {
        Commission commission = commissionRepository.getFirstById(id);
        if (commission == null) {
            throw new EntityNotFoundException("No commissions with id: " + id);
        }

        commissionRepository.delete(commission);
    }

    @Override
    public Page<Commission> readAllByPaidToAndYear(Long paidToUserId, Integer year, Integer page) throws Exception {
        if (userService.read(paidToUserId) == null) {
            throw new EntityNotFoundException("No user with id: " + paidToUserId);
        }

        if (!DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid year!");
        }

        return commissionRepository.findAllByPaidToIdAndYearOrderByDateOfOrderAsc(paidToUserId,
                year, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<Commission> readAllByApprovedByMonthAndYear(Long approvedById, Integer month, Integer year, Integer page) throws Exception {
        if (userService.read(approvedById) == null) {
            throw new EntityNotFoundException("No user with id: " + approvedById);
        }

        if (!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        return commissionRepository.findAllByApprovedByIdAndMonthAndYearOrderByDateOfOrderAsc(approvedById,
                month, year, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<Commission> readAllByCompanyMonthAndYear(Long companyId, Integer month, Integer year, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        if (!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        return commissionRepository.findAllByCompanyMonthAndYear(month, year, companyId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<Commission> readAllByCompanyAndYear(Long companyId, Integer year, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        if (!DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid year!");
        }

        return commissionRepository.findAllByCompanyAndYear(year, companyId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public List<CommissionResponseDTO> readAllByCompanyDepartmentOfficeCodeTypeAndYear(Long companyId,
                                                                                       String department,
                                                                                       Long officeCodeId,
                                                                                       Long typeId,
                                                                                       Integer year) throws Exception {
        if (companyService.read(companyId) == null
                || !ConstantsUtil.isValidCustomDepartment(department)
                || officeCodeService.read(officeCodeId) == null
                || typeOfCommissionService.read(typeId) == null) {
            throw new EntityNotFoundException("Invalid mandatory values found!");
        }

        List<UserSmallResponseDTO> users = new ArrayList<>();

        users = userService.readAllByCompanyDepartmentAndOfficeCode(companyId,
                department, officeCodeId);

        List<CommissionResponseDTO> dtos = new ArrayList<>();
        if (users != null && users.size() > 0) {
            for (UserSmallResponseDTO user: users) {
                Commission commission = commissionRepository.getFirstByPaidToIdAndYearAndTypeOfCommissionId(user.getUserId(),
                        year, typeId);

                if (commission != null) {
                    dtos.add(commissionResponseMapper.map(commission));
                } else {
                    CommissionResponseDTO dto = new CommissionResponseDTO();
                    dto.setPaidToUser(user);

                    dtos.add(dto);
                }
            }
        }

        return dtos;
    }

    @Override
    public List<CommissionResponseDTO> createOrUpdateCommissionsByYear(CommissionListDTO dto) throws Exception {
        List<CommissionResponseDTO> dtos = new ArrayList<>();

        if (dto.getNumber() > 0) {
            List<Commission> commissions = new ArrayList<>();
            for (CommissionDTO commissionDTO: dto.getCommissions()) {
                commissions.add(commissionMapper.map(commissionDTO));
            }

            CommissionResponseDTO commissionResponseDTO;
            NotificationResponseDTO notification;
            for (Commission newCommission: commissions) {
                Commission oldCommission = commissionRepository
                        .getFirstByPaidToIdAndYearAndTypeOfCommissionId(newCommission.getPaidTo().getId(),
                                newCommission.getYear(), newCommission.getTypeOfCommission().getId());

                if (oldCommission == null) {
                    if (newCommission.getAmount() != 0) {
                        commissionResponseDTO = this.create(newCommission);
                        dtos.add(commissionResponseDTO);

                        notification  = notificationService.create(notificationMapper.map(commissionResponseDTO, Constants.Operations.CREATED));

                        pushNotificationService.pushNotification(notification, commissionResponseDTO.getPaidToUser().getUserId());
                    }
                } else {
                    if (newCommission.getAmount() != 0) {
                        commissionResponseDTO = this.update(oldCommission.getId(), newCommission);
                        dtos.add(commissionResponseDTO);

                        notification  = notificationService.create(notificationMapper.map(commissionResponseDTO, Constants.Operations.UPDATED));
                    }
                    else {
                        this.remove(oldCommission.getId());
                        commissionResponseDTO = commissionResponseMapper.map(oldCommission);

                        notification  = notificationService.create(notificationMapper.map(commissionResponseDTO, Constants.Operations.REMOVED));
                    }

                    pushNotificationService.pushNotification(notification, commissionResponseDTO.getPaidToUser().getUserId());
                }
            }
        }

        return dtos;
    }

    @Override
    public List<CommissionResponseDTO> generateReportForYearByCompanyDepartmentOfficeCodeTypeAndCategory(Long companyId,
                                                                                                         String department,
                                                                                                         Long officeCodeId,
                                                                                                         Long typeId,
                                                                                                         Integer year,
                                                                                                         String category) throws Exception {
        if (companyService.read(companyId) == null
                || !ConstantsUtil.isValidCustomDepartment(department)
                || officeCodeService.read(officeCodeId) == null
                || typeOfCommissionService.read(typeId) == null
                || (!category.equals(Constants.CommissionCategory.FOREIGN) && !category.equals(Constants.CommissionCategory.LOCAL))) {
            throw new EntityNotFoundException("Invalid mandatory values found!");
        }

        List<UserSmallResponseDTO> users = new ArrayList<>();

        users = userService.readAllByCompanyDepartmentAndOfficeCode(companyId,
                department, officeCodeId);

        List<CommissionResponseDTO> dtos = new ArrayList<>();
        if (users != null && users.size() > 0) {
            for (UserSmallResponseDTO user: users) {
                Commission commission = commissionRepository.getFirstByPaidToIdAndYearAndTypeOfCommissionId(user.getUserId(),
                        year, typeId);

                if (commission != null) {
                    switch (category) {
                        case Constants.CommissionCategory.LOCAL:
                            if (commission.getLocalCommission() != null && commission.getLocalCommission() != 0)
                                dtos.add(commissionResponseMapper.map(commission));
                            break;
                        case Constants.CommissionCategory.FOREIGN:
                            if (commission.getForeignCommission() != null && commission.getForeignCommission() != 0)
                                dtos.add(commissionResponseMapper.map(commission));
                            break;
                    }
                }
            }
        }

        return dtos;
    }
}
