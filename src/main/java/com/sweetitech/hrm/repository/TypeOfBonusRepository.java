package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.TypeOfBonus;
import com.sweetitech.hrm.domain.TypeOfCommission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypeOfBonusRepository extends CrudRepository<TypeOfBonus, Long> {

    TypeOfBonus getFirstById(Long id);

    List<TypeOfBonus> findAllByIsDeletedFalseOrderByNameAsc();

    TypeOfBonus getFirstByName(String name);

    @Query(value = "SELECT * FROM type_of_bonus b WHERE b.name LIKE CONCAT('%', :name, '%') AND b.is_deleted = false", nativeQuery = true)
    List<TypeOfBonus> searchByName(@Param("name") String name);

}
