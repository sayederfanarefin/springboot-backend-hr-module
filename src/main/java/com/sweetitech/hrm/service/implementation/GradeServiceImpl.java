package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Grade;
import com.sweetitech.hrm.repository.GradeRepository;
import com.sweetitech.hrm.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {

    private GradeRepository gradeRepository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    @Override
    public Grade read(Long id) {
        return gradeRepository.getFirstById(id);
    }

    @Override
    public List<Grade> readAll() {
        return gradeRepository.findAllByIsDeletedFalseOrderByGradeNumberAscTitleAsc();
    }

    @Override
    public Grade create(Grade grade) {
        Grade gradeCheck = gradeRepository.getFirstByTitle(grade.getTitle());
        if (gradeCheck != null) {
            throw new EntityNotFoundException("Title already exists!");
        }

        return gradeRepository.save(grade);
    }

    @Override
    public Grade update(Long id, Grade grade) throws Exception {
        Grade gradeOld = gradeRepository.getFirstById(id);
        if (gradeOld == null) {
            throw new EntityNotFoundException("No grade with id: " + id);
        }

        grade.setId(id);
        return gradeRepository.save(grade);
    }

    @Override
    public void remove(Long id) throws Exception {
        Grade grade = gradeRepository.getFirstById(id);
        if (grade == null) {
            throw new EntityNotFoundException("No grade with id: " + id);
        }

        grade.setDeleted(true);
        gradeRepository.save(grade);
    }
}
