package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.Commission;
import com.sweetitech.hrm.domain.TypeOfCommission;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.CommissionDTO;
import com.sweetitech.hrm.service.implementation.TypeOfCommissionServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommissionMapper {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TypeOfCommissionServiceImpl typeOfCommissionService;

    /**
     *
     * @param dto
     * @return entity
     */
    public Commission map(CommissionDTO dto) throws Exception {
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

        TypeOfCommission typeOfCommission = null;
        if (dto.getTypeOfCommissionId() != null) {
            typeOfCommission = typeOfCommissionService.read(dto.getTypeOfCommissionId());
        }

        Commission entity = new Commission();

        entity.setAmount(dto.getAmount());
        entity.setLocalCommission(dto.getLocalCommission());
        entity.setForeignCommission(dto.getForeignCommission());
        entity.setDateOfOrder(dto.getDateOfOrder());
        entity.setMonth(dto.getMonth());
        entity.setYear(dto.getYear());
        entity.setRemarks(dto.getRemarks());
        entity.setPaidTo(paidToUser);
        entity.setApprovedBy(approvedByUser);

        if (dto.getDateOfPayment() != null) {
            entity.setDateOfPayment(dto.getDateOfPayment());
        }

        entity.setTypeOfCommission(typeOfCommission);

        return entity;
    }

}
