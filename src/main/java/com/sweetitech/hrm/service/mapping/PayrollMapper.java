package com.sweetitech.hrm.service.mapping;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Payroll;
import com.sweetitech.hrm.domain.Salary;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.PayrollDTO;
import com.sweetitech.hrm.domain.dto.UserSmallResponseDTO;
import com.sweetitech.hrm.service.implementation.SalaryServiceImpl;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PayrollMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SalaryServiceImpl salaryService;

    /**
     *
     * @param entity
     * @return dto
     */
    public PayrollDTO map(Payroll entity) throws Exception {
        PayrollDTO dto = new PayrollDTO();

        dto.setPayrollId(entity.getId());

        dto.setCreatedOn(entity.getCreatedOn());

        dto.setMonth(entity.getMonth());

        dto.setYear(entity.getYear());

        dto.setSalary(entity.getSalary());

        dto.setPaidToUser(userSmallResponseMapper.map(entity.getPaidToUser()));

        dto.setApprovedByUser(userSmallResponseMapper.map(entity.getApprovedByUser()));

        dto.setAbsent(entity.getAbsent());

        dto.setAbsentPenalty(entity.getAbsentPenalty());

        dto.setLate(entity.getLate());

        dto.setLatePenalty(entity.getLatePenalty());

        dto.setLeavesTaken(entity.getLeavesTaken());

        dto.setUnpaidLeavesTaken(entity.getUnpaidLeavesTaken());

        dto.setSpecialLeavesTaken(entity.getSpecialLeavesTaken());

        dto.setAccountedLeaves(entity.getAccountedLeaves());

        dto.setLeavePenalty(entity.getLeavePenalty());

        dto.setAccountedEarnedLeaves(entity.getAccountedEarnedLeaves());

        dto.setEarnedLeaveBonus(entity.getEarnedLeaveBonus());

        dto.setApprovedAllowance(entity.getApprovedAllowance());

        dto.setTotalPayable(entity.getTotalPayable());

        dto.setPayedOn(entity.getPayedOn());

        return dto;

    }

    /**
     *
     * @param dto
     * @return entity
     */
    public Payroll map(PayrollDTO dto) throws Exception {
        if (dto.getMonth() == null
                || dto.getYear() == null
                || dto.getPaidToUserId() == null
                || dto.getApprovedByUserId() == null
                || dto.getTotalPayable() == null) {
            throw new EntityNotFoundException("Null values found!");
        }

        User paidToUser = userService.read(dto.getPaidToUserId());
        if (paidToUser == null) {
            throw new EntityNotFoundException("No users with id: " + dto.getPaidToUserId());
        }

        User approvedByUser = userService.read(dto.getApprovedByUserId());
        if (approvedByUser == null) {
            throw new EntityNotFoundException("No users with id: " + dto.getApprovedByUserId());
        }

        Salary salary = salaryService.read(dto.getSalaryId());
        if (salary == null) {
            throw new EntityNotFoundException("No salaries with id: " + dto.getSalaryId());
        }

        Payroll entity = new Payroll();

        entity.setMonth(dto.getMonth());
        entity.setYear(dto.getYear());
        entity.setPaidToUser(paidToUser);
        entity.setSalary(salary);
        entity.setApprovedByUser(approvedByUser);
        entity.setTotalPayable(dto.getTotalPayable());

        if (dto.getAbsent() != null)
            entity.setAbsent(dto.getAbsent());
        if (dto.getAbsentPenalty() != null)
            entity.setAbsentPenalty(dto.getAbsentPenalty());

        if (dto.getLate() != null)
            entity.setLate(dto.getLate());
        if (dto.getLatePenalty() != null)
            entity.setLatePenalty(dto.getLatePenalty());

        if (dto.getLeavesTaken() != null)
            entity.setLeavesTaken(dto.getLeavesTaken());
        if (dto.getUnpaidLeavesTaken() != null)
            entity.setUnpaidLeavesTaken(dto.getUnpaidLeavesTaken());
        if (dto.getSpecialLeavesTaken() != null)
            entity.setSpecialLeavesTaken(dto.getSpecialLeavesTaken());
        if (dto.getAccountedLeaves() != null)
            entity.setAccountedLeaves(dto.getAccountedLeaves());
        if (dto.getLeavePenalty() != null)
            entity.setLeavePenalty(dto.getLeavePenalty());

        if (dto.getAccountedEarnedLeaves() != null)
            entity.setAccountedEarnedLeaves(dto.getAccountedEarnedLeaves());
        if (dto.getEarnedLeaveBonus() != null)
            entity.setEarnedLeaveBonus(dto.getEarnedLeaveBonus());

        if (dto.getApprovedAllowance() != null)
            entity.setApprovedAllowance(dto.getApprovedAllowance());

        if (dto.getPayedOn() != null)
            entity.setPayedOn(dto.getPayedOn());

        return entity;
    }

}
