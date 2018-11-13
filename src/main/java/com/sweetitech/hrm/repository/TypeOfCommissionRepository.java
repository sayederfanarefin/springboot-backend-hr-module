package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.TypeOfCommission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TypeOfCommissionRepository extends CrudRepository<TypeOfCommission, Long> {

    TypeOfCommission getFirstById(Long id);

    List<TypeOfCommission> findAllByIsDeletedFalseOrderByNameAsc();

    TypeOfCommission getFirstByName(String name);

    @Query(value = "SELECT * FROM type_of_commission c WHERE c.name LIKE CONCAT('%', :name, '%') AND c.is_deleted = false", nativeQuery = true)
    List<TypeOfCommission> searchByName(@Param("name") String name);

}
