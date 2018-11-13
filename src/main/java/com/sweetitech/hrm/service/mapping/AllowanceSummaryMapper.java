package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Allowance;
import com.sweetitech.hrm.domain.AllowanceSummary;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.AllowanceSummaryDTO;
import com.sweetitech.hrm.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllowanceSummaryMapper {

    @Autowired
    private UserServiceImpl userService;

    /**
     * Maps DTO to Entity
     *
     * @param dto
     * @return entity
     */
    public AllowanceSummary map(AllowanceSummaryDTO dto) throws Exception {
        if (dto.getMonth() == null || dto.getYear() ==  null) {
            throw new EntityNotFoundException("Invalid month or year!");
        }

        if (dto.getPreparedForUserId() == null) {
            throw new EntityNotFoundException("The summary is missing the person prepared for!");
        }
        User preparedFor = userService.read(dto.getPreparedForUserId());
        if (preparedFor == null) {
            throw new EntityNotFoundException("No user with id: " + dto.getPreparedForUserId());
        }

        if (dto.getConfirmedByUserId() == null) {
            throw new EntityNotFoundException("The summary is missing the person confirming it!");
        }
        User confirmedBy = userService.read(dto.getConfirmedByUserId());
        if (confirmedBy == null) {
            throw new EntityNotFoundException("No user with id: " + dto.getConfirmedByUserId());
        }

        if (dto.getTotal() == null) {
            throw new EntityNotFoundException("The summary is missing a total value!");
        }

        AllowanceSummary entity = new AllowanceSummary();
        entity.setMonth(dto.getMonth());
        entity.setYear(dto.getYear());

        if (dto.getTerritory() != null) {
            entity.setTerritory(dto.getTerritory());
        }

        entity.setTotal(dto.getTotal());
        entity.setConfirmDate(dto.getConfirmDate());
        entity.setPreparedFor(preparedFor);

        if (preparedFor.getGrade() != null)
            entity.setForGrade(preparedFor.getGrade());

        entity.setConfirmedBy(confirmedBy);

        return entity;
    }

    /**
     * Maps DTO to Entity
     *
     * @param entity
     * @return dto
     */
    public AllowanceSummaryDTO map(AllowanceSummary entity) throws Exception {
        AllowanceSummaryDTO dto = new AllowanceSummaryDTO();

        dto.setSummaryId(entity.getId());
        dto.setMonth(dto.getMonth());
        dto.setYear(dto.getYear());
        dto.setTerritory(entity.getTerritory());
        dto.setTotal(entity.getTotal());
        dto.setConfirmDate(entity.getConfirmDate());

        if (entity.getPreparedFor().getGrade() != null) {
            dto.setForGrade(entity.getPreparedFor().getGrade());
        }

        dto.setPreparedForUserId(entity.getPreparedFor().getId());
        dto.setConfirmedByUserId(entity.getConfirmedBy().getId());

        return dto;
    }

}
