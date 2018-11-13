package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.Commission;
import com.sweetitech.hrm.domain.dto.CommissionResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommissionResponseMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    /**
     *
     * @param entity
     * @return dto
     */
    public CommissionResponseDTO map(Commission entity) throws Exception {
        CommissionResponseDTO dto = new CommissionResponseDTO();

        dto.setCommissionId(entity.getId());
        dto.setLocalCommission(entity.getLocalCommission());
        dto.setForeignCommission(entity.getForeignCommission());
        dto.setAmount(entity.getAmount());
        dto.setDateOfOrder(entity.getDateOfOrder());
        dto.setMonth(entity.getMonth());
        dto.setYear(entity.getYear());
        dto.setRemarks(entity.getRemarks());
        dto.setPaidToUser(userSmallResponseMapper.map(entity.getPaidTo()));
        dto.setApprovedByUser(userSmallResponseMapper.map(entity.getApprovedBy()));
        dto.setDateOfPayment(entity.getDateOfPayment());
        dto.setTypeOfCommission(entity.getTypeOfCommission());

        return dto;
    }

}
