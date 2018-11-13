package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.SalaryBreakdown;
import com.sweetitech.hrm.domain.dto.SalaryBreakdownCheckDTO;
import com.sweetitech.hrm.domain.dto.SalaryBreakdownCheckListDTO;
import com.sweetitech.hrm.domain.dto.SalaryBreakdownRelationDTO;
import com.sweetitech.hrm.domain.relation.SalaryBreakdownRelation;
import com.sweetitech.hrm.repository.SalaryBreakdownRelationRepository;
import com.sweetitech.hrm.repository.SalaryBreakdownRepository;
import com.sweetitech.hrm.service.SalaryBreakdownService;
import com.sweetitech.hrm.service.mapping.SalaryBreakdownRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SalaryBreakdownServiceImpl implements SalaryBreakdownService {

    private SalaryBreakdownRepository salaryBreakdownRepository;
    private SalaryBreakdownRelationRepository salaryBreakdownRelationRepository;

    @Autowired
    private SalaryServiceImpl salaryService;

    @Autowired
    private SalaryBreakdownRelationMapper salaryBreakdownRelationMapper;

    @Autowired
    public SalaryBreakdownServiceImpl(SalaryBreakdownRepository salaryBreakdownRepository,
                                      SalaryBreakdownRelationRepository salaryBreakdownRelationRepository) {
        this.salaryBreakdownRepository = salaryBreakdownRepository;
        this.salaryBreakdownRelationRepository = salaryBreakdownRelationRepository;
    }

    private boolean match(SalaryBreakdownRelation salaryBreakdownRelation1, SalaryBreakdownRelation salaryBreakdownRelation2) {
        if (Objects.equals(salaryBreakdownRelation1.getSalary().getId(), salaryBreakdownRelation2.getSalary().getId())) {
            if (Objects.equals(salaryBreakdownRelation1.getBreakdown().getId(), salaryBreakdownRelation2.getBreakdown().getId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public SalaryBreakdown create(SalaryBreakdown salaryBreakdown) throws Exception {
        if (this.readByTitle(salaryBreakdown.getTitle()) != null) {
            throw new EntityNotFoundException("An object with same title already exists!");
        }

        return salaryBreakdownRepository.save(salaryBreakdown);
    }

    @Override
    public SalaryBreakdown read(Long id) throws Exception {
        return salaryBreakdownRepository.getFirstById(id);
    }

    @Override
    public SalaryBreakdown readByTitle(String title) throws Exception {
        return salaryBreakdownRepository.getFirstByTitle(title);
    }

    @Override
    public List<SalaryBreakdown> readAll() {
        return salaryBreakdownRepository.findAllByOrderByTitleAsc();
    }

    @Override
    public List<SalaryBreakdownRelation> readAllBySalary(Long salaryId) throws Exception {
        if (salaryService.read(salaryId) == null) {
            throw new EntityNotFoundException("No salaries with id: " + salaryId);
        }

        return salaryBreakdownRelationRepository.findAllBySalaryIdAndIsDeletedFalse(salaryId);
    }

    @Override
    public void removeRelation(Long relationId) throws Exception {
        SalaryBreakdownRelation relation = salaryBreakdownRelationRepository.getFirstById(relationId);
        if (relation == null) {
            throw new EntityNotFoundException("No relations with id: " + relationId);
        }

        relation.setDeleted(true);
        salaryBreakdownRelationRepository.save(relation);
    }

    @Override
    public List<SalaryBreakdownRelation> bulkAddRelations(List<SalaryBreakdownRelationDTO> dtos) throws Exception {
        List<SalaryBreakdownRelation> relations = new ArrayList<>();
        List<SalaryBreakdownRelation> allRelations = new ArrayList<>();

        if (dtos.size() == 0) {
            return relations;
        }

        List<SalaryBreakdownRelation> oldRelations = this.readAllBySalary(dtos.get(0).getSalaryId());

        double total = 0.0;
        for (SalaryBreakdownRelationDTO dto: dtos) {
            SalaryBreakdownRelation temp = salaryBreakdownRelationMapper.map(dto);
            total += temp.getBreakdown().getPercentage();

            allRelations.add(temp);

            boolean flag = false;
            if (oldRelations != null && oldRelations.size() > 0) {
                for (SalaryBreakdownRelation relation: oldRelations) {
                    if (this.match(relation, temp)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    relations.add(temp);
                }
            } else {
                relations.add(temp);
            }
        }

        if (total > 100) {
            throw new EntityNotFoundException("Total percentage exceeds 100%");
        }

        boolean flag;
        for (SalaryBreakdownRelation oldRelation: oldRelations) {
            flag = false;

            for (SalaryBreakdownRelation relation: allRelations) {
                if (this.match(oldRelation, relation)) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                this.removeRelation(oldRelation.getId());
            }
        }

        List<SalaryBreakdownRelation> toReturn = new ArrayList<>();
        for (SalaryBreakdownRelation relation: relations) {
            toReturn.add(this.createRelation(relation));
        }

        return toReturn;

    }

    @Override
    public SalaryBreakdownCheckListDTO readAllRelationsBySalary(Long salaryId) throws Exception {
        if (salaryService.read(salaryId) == null) {
            throw new EntityNotFoundException("No salaries with id: " + salaryId);
        }

        SalaryBreakdownCheckListDTO checkListDTO = new SalaryBreakdownCheckListDTO();

        List<SalaryBreakdownCheckDTO> checkDTOS = new ArrayList<>();
        List<SalaryBreakdown> breakdowns = this.readAll();

        if (breakdowns != null && breakdowns.size() > 0) {
            for (SalaryBreakdown breakdown: breakdowns) {
                SalaryBreakdownCheckDTO checkDTO = new SalaryBreakdownCheckDTO();
                checkDTO.setBreakdown(breakdown);
                checkDTO.setChecked(false);
                checkDTOS.add(checkDTO);
            }
        } else {
            return checkListDTO;
        }

        List<SalaryBreakdownRelation> relations = salaryBreakdownRelationRepository.findAllBySalaryIdAndIsDeletedFalse(salaryId);
        checkListDTO.setCheckDTOS(new ArrayList<>());
        if (relations != null && relations.size() > 0) {
            for (SalaryBreakdownCheckDTO checkDTO: checkDTOS) {
                boolean flag = false;
                SalaryBreakdownCheckDTO temp = checkDTO;
                for (SalaryBreakdownRelation relation: relations) {
                    // temp = checkDTO;
                    if (Objects.equals(checkDTO.getBreakdown().getId(), relation.getBreakdown().getId())) {
                        flag = true;
                        break;
                    }
                }
                if (flag && temp != null) {
                    temp.setChecked(true);
                }

                // System.out.println(temp);

                if (temp != null)
                    checkListDTO.getCheckDTOS().add(temp);
            }
        } else {
            checkListDTO.getCheckDTOS().addAll(checkDTOS);
        }

        checkListDTO.setRelations(relations);

        return checkListDTO;
    }

    @Override
    public SalaryBreakdownRelation createRelation(SalaryBreakdownRelation relation) throws Exception {
        return salaryBreakdownRelationRepository.save(relation);
    }
}
