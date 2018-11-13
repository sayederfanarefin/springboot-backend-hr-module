package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.ConstantsUtil;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.Bonus;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.*;
import com.sweetitech.hrm.repository.BonusRepository;
import com.sweetitech.hrm.service.BonusService;
import com.sweetitech.hrm.service.PushNotificationService;
import com.sweetitech.hrm.service.UserService;
import com.sweetitech.hrm.service.mapping.BonusMapper;
import com.sweetitech.hrm.service.mapping.BonusResponseMapper;
import com.sweetitech.hrm.service.mapping.NotificationMapper;
import com.sweetitech.hrm.service.mapping.UserSmallResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BonusServiceImpl implements BonusService {

    private BonusRepository bonusRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private OfficeCodeServiceImpl officeCodeService;

    @Autowired
    private TypeOfBonusServiceImpl typeOfBonusService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private BonusMapper bonusMapper;

    @Autowired
    private BonusResponseMapper bonusResponseMapper;

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private NotificationServiceImpl notificationService;

    @Autowired
    private PushNotificationService pushNotificationService;

    @Autowired
    public BonusServiceImpl(BonusRepository bonusRepository) {
        this.bonusRepository = bonusRepository;
    }

    @Override
    public BonusResponseDTO create(Bonus bonus) throws Exception {
        return bonusResponseMapper.map(bonusRepository.save(bonus));
    }

    @Override
    public BonusResponseDTO update(Long id, Bonus bonus) throws Exception {
        Bonus bonusOld = bonusRepository.getFirstById(id);
        if (bonusOld == null) {
            throw new EntityNotFoundException("No bonuses with id: " + id);
        }

        bonus.setId(bonusOld.getId());
        return bonusResponseMapper.map(bonusRepository.save(bonus));
    }

    @Override
    public BonusResponseDTO read(Long id) throws Exception {
        return bonusResponseMapper.map(bonusRepository.getFirstById(id));
    }

    @Override
    public BonusResponseDTO updatePaymentDate(Long id, Date paymentDate) throws Exception {
        Bonus bonus = bonusRepository.getFirstById(id);
        if (bonus == null) {
            throw new EntityNotFoundException("No bonuses with id: " + id);
        }

        if (!DateTimeUtils.isValidDate(paymentDate.toString(), Constants.Dates.VALID_FORMAT)) {
            throw new EntityNotFoundException("Invalid date!");
        }

        bonus.setDateOfPayment(paymentDate);
        return bonusResponseMapper.map(bonusRepository.save(bonus));
    }

    @Override
    public void remove(Long id) throws Exception {
        Bonus bonus = bonusRepository.getFirstById(id);
        if (bonus == null) {
            throw new EntityNotFoundException("No bonuses with id: " + id);
        }

        bonusRepository.delete(bonus);
    }

    @Override
    public Page<Bonus> readAllByPaidToAndYear(Long paidToUserId, Integer year, Integer page) throws Exception {
        if (userService.read(paidToUserId) == null) {
            throw new EntityNotFoundException("No user with id: " + paidToUserId);
        }

        if (!DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid year!");
        }

        return bonusRepository.findAllByPaidToIdAndYearOrderByDateOfOrderAsc(paidToUserId,
                year, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<Bonus> readAllByApprovedByMonthAndYear(Long approvedById, Integer month, Integer year, Integer page) throws Exception {
        if (userService.read(approvedById) == null) {
            throw new EntityNotFoundException("No user with id: " + approvedById);
        }

        if (!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        return bonusRepository.findAllByApprovedByIdAndMonthAndYearOrderByDateOfOrderAsc(approvedById,
                month, year, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<Bonus> readAllByCompanyMonthAndYear(Long companyId, Integer month, Integer year, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        if (!DateValidator.isValidMonth(month) || !DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        return bonusRepository.findAllByCompanyMonthAndYear(month, year, companyId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<Bonus> readAllByCompanyAndYear(Long companyId, Integer year, Integer page) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        if (!DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid year!");
        }

        return bonusRepository.findAllByCompanyAndYear(year, companyId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public List<BonusResponseDTO> readAllByCompanyDepartmentOfficeCodeTypeAndYear(Long companyId,
                                                                                  Long departmentId,
                                                                                  Long officeCodeId,
                                                                                  Long typeId,
                                                                                  Integer year) throws Exception {
        if (companyService.read(companyId) == null
                || departmentId == null
                || officeCodeId == null
                || typeOfBonusService.read(typeId) == null) {
            throw new EntityNotFoundException("Invalid mandatory values found!");
        }

        List<UserSmallResponseDTO> users = new ArrayList<>();

        List<User> temp = userService.listActiveUsers(companyId,
                departmentId, officeCodeId);

        if (temp != null && temp.size() > 0) {
            for (User user: temp) {
                users.add(userSmallResponseMapper.map(user));
            }
        }

        List<BonusResponseDTO> dtos = new ArrayList<>();
        if (users != null && users.size() > 0) {
            for (UserSmallResponseDTO user: users) {
                Bonus bonus = bonusRepository.getFirstByPaidToIdAndYearAndTypeOfBonusId(user.getUserId(),
                        year, typeId);

                if (bonus != null) {
                    dtos.add(bonusResponseMapper.map(bonus));
                } else {
                    BonusResponseDTO dto = new BonusResponseDTO();
                    dto.setPaidToUser(user);

                    dtos.add(dto);
                }
            }
        }

        return dtos;
    }

    @Override
    public List<BonusResponseDTO> createOrUpdateBonusesByYear(BonusListDTO dto) throws Exception {
        List<BonusResponseDTO> dtos = new ArrayList<>();

        if (dto.getNumber() > 0) {
            List<Bonus> bonuses = new ArrayList<>();
            for (BonusDTO bonusDTO: dto.getBonuses()) {
                bonuses.add(bonusMapper.map(bonusDTO));
            }

            BonusResponseDTO bonusResponseDTO;
            NotificationResponseDTO notification;
            for (Bonus newBonus: bonuses) {
                Bonus oldBonus = bonusRepository
                        .getFirstByPaidToIdAndYearAndTypeOfBonusId(newBonus.getPaidTo().getId(),
                                newBonus.getYear(), newBonus.getTypeOfBonus().getId());

                if (oldBonus == null) {
                    if (newBonus.getAmount() != 0) {
                        bonusResponseDTO = this.create(newBonus);
                        dtos.add(bonusResponseDTO);

                        notification  = notificationService.create(notificationMapper.map(bonusResponseDTO, Constants.Operations.CREATED));

                        pushNotificationService.pushNotification(notification, bonusResponseDTO.getPaidToUser().getUserId());
                    }
                } else {
                    if (newBonus.getAmount() != 0) {
                        bonusResponseDTO = this.update(oldBonus.getId(), newBonus);
                        dtos.add(bonusResponseDTO);

                        notification  = notificationService.create(notificationMapper.map(bonusResponseDTO, Constants.Operations.UPDATED));
                    }
                    else {
                        this.remove(oldBonus.getId());
                        bonusResponseDTO = bonusResponseMapper.map(oldBonus);

                        notification  = notificationService.create(notificationMapper.map(bonusResponseDTO, Constants.Operations.REMOVED));
                    }

                    pushNotificationService.pushNotification(notification, bonusResponseDTO.getPaidToUser().getUserId());
                }
            }
        }

        return dtos;
    }

    @Override
    public List<BonusResponseDTO> generateReportForYearByCompanyDepartmentOfficeCodeTypeAndCategory(Long companyId,
                                                                                                    Long departmentId,
                                                                                                    Long officeCodeId,
                                                                                                    Long typeId,
                                                                                                    Integer year) throws Exception {
        if (companyService.read(companyId) == null
                || typeOfBonusService.read(typeId) == null) {
            throw new EntityNotFoundException("Invalid mandatory values found!");
        }

        List<User> users = new ArrayList<>();

        users = userService.listActiveUsers(companyId,
                departmentId, officeCodeId);

        List<BonusResponseDTO> dtos = new ArrayList<>();
        if (users != null && users.size() > 0) {
            for (User user: users) {
                Bonus bonus = bonusRepository.getFirstByPaidToIdAndYearAndTypeOfBonusId(user.getId(),
                        year, typeId);

                if (bonus != null) {
                    dtos.add(bonusResponseMapper.map(bonus));
                }
            }
        }

        return dtos;
    }
}
