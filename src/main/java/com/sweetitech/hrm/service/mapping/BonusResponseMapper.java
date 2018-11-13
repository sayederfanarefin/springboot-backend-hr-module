package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.Bonus;
import com.sweetitech.hrm.domain.dto.BonusResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BonusResponseMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    /**
     *
     * @param entity
     * @return dto
     */
    public BonusResponseDTO map(Bonus entity) throws Exception {
        BonusResponseDTO dto = new BonusResponseDTO();

        dto.setBonusId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setDateOfOrder(entity.getDateOfOrder());
        dto.setMonth(entity.getMonth());
        dto.setYear(entity.getYear());
        dto.setRemarks(entity.getRemarks());
        dto.setPaidToUser(userSmallResponseMapper.map(entity.getPaidTo()));
        dto.setApprovedByUser(userSmallResponseMapper.map(entity.getApprovedBy()));
        dto.setDateOfPayment(entity.getDateOfPayment());
        dto.setTypeOfBonus(entity.getTypeOfBonus());

        return dto;
    }

}
