package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.domain.PayrollBreakdown;
import com.sweetitech.hrm.domain.dto.PayrollBreakdownDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayrollBreakdownMapper {

    @Autowired
    private PayrollMapper payrollMapper;

    /**
     *
     * @param entity
     * @return dto
     */
    // should only be used for readPayrollForUser in PayrollServiceImpl
    public PayrollBreakdownDTO map(PayrollBreakdown entity) throws Exception {
        PayrollBreakdownDTO dto = new PayrollBreakdownDTO();

//        if (entity.getPayroll() != null) {
//            dto.setPayroll(entity.getPayroll());
//        }

        dto.setBreakdown(entity.getBreakdown());
        dto.setAmount(entity.getAmount());

        return dto;
    }

    /**
     *
     * @param entity
     * @return dto
     */
    public PayrollBreakdownDTO map(PayrollBreakdown entity, boolean flag) throws Exception {
        PayrollBreakdownDTO dto = new PayrollBreakdownDTO();

        if (flag) {
            dto.setPayrollDTO(payrollMapper.map(entity.getPayroll()));
        } else {
            dto.setPayrollDTO(null);
        }

        dto.setBreakdown(entity.getBreakdown());
        dto.setAmount(entity.getAmount());

        return dto;
    }

}
