package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.Grade;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GradeRepository extends CrudRepository<Grade, Long> {

    Grade findById(long id);

    Grade getFirstById(Long id);

    List<Grade> findAllByIsDeletedFalseOrderByGradeNumberAscTitleAsc();

    Grade getFirstByTitle(String title);

}
