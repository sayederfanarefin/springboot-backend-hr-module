package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Grade;

import java.util.List;

public interface GradeService {

    Grade read(Long id);

    List<Grade> readAll();

    Grade create(Grade grade);

    Grade update(Long id, Grade grade) throws Exception;

    void remove(Long id) throws Exception;

}
