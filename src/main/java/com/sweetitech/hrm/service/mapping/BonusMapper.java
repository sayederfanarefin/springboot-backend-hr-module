package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.Bonus;
import com.sweetitech.hrm.domain.TypeOfBonus;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.BonusDTO;
import com.sweetitech.hrm.service.implementation.TypeOfBonusServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BonusMapper {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TypeOfBonusServiceImpl typeOfBonusService;

    /**
     *
     * @param dto
     * @return entity
     */
    public Bonus map(BonusDTO dto) throws Exception {
        User approvedByUser = userService.read(dto.getApprovedByUserId());
        if (approvedByUser == null) {
            throw new EntityNotFoundException("No user with id: " + dto.getApprovedByUserId());
        }

        if (!DateValidator.isValidMonth(dto.getMonth()) || !DateValidator.isValidYear(dto.getYear())) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        User paidToUser = userService.read(dto.getPaidToUserId());
        if (paidToUser == null) {
            throw new EntityNotFoundException("No user with id: " + dto.getPaidToUserId());
        }

        if (!DateTimeUtils.isValidDate(dto.getDateOfOrder().toString(), Constants.Dates.VALID_FORMAT)) {
            throw new EntityNotFoundException("Invalid date!");
        }

        if (dto.getDateOfPayment() != null && !DateTimeUtils.isValidDate(dto.getDateOfPayment().toString(),
                Constants.Dates.VALID_FORMAT)) {
            throw new EntityNotFoundException("Invalid date!");
        }

        TypeOfBonus typeOfBonus = null;
        if (dto.getTypeOfBonusId() != null) {
            typeOfBonus = typeOfBonusService.read(dto.getTypeOfBonusId());
        }

        Bonus entity = new Bonus();

        entity.setAmount(dto.getAmount());
        entity.setDateOfOrder(dto.getDateOfOrder());
        entity.setMonth(dto.getMonth());
        entity.setYear(dto.getYear());
        entity.setRemarks(dto.getRemarks());
        entity.setPaidTo(paidToUser);
        entity.setApprovedBy(approvedByUser);

        if (dto.getDateOfPayment() != null) {
            entity.setDateOfPayment(dto.getDateOfPayment());
        }

        entity.setTypeOfBonus(typeOfBonus);

        return entity;
    }

}
