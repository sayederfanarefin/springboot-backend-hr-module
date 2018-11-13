package com.sweetitech.hrm.repository;

import com.sweetitech.hrm.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findById(long id);

    Page<User> findAllByCompanyIdAndIsDeletedFalseOrderByCreatedDesc(Long companyId, Pageable pageable);

    Page<User> findAllByCompanyIdAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(Long companyId, Pageable pageable);

    List<User> findAllByCompanyIdAndIsDeletedFalseOrderByCreatedDesc(Long companyId);

    List<User> findAllByCompanyIdAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(Long companyId);

    Page<User> findAllByCompanyIdAndRoleNameAndIsDeletedFalseOrderByCreatedDesc(Long companyId, String name, Pageable pageable);

    Page<User> findAllByCompanyIdAndRoleNameAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(Long companyId, String name, Pageable pageable);

    Page<User> findAllByCompanyIdAndRoleNameOrRoleNameOrRoleNameAndIsDeletedFalseOrderByCreatedDesc(Long companyId, String name1, String name2, String name3, Pageable pageable);

    // Admin
    @Query("SELECT u FROM User u WHERE u.company.id = :companyId AND (u.role.name = :roleName1 OR u.role.name = :roleName2 OR u.role.name = :roleName3) AND u.isDeleted = false ORDER BY u.grade.gradeNumber ASC, u.grade.title ASC, u.basicInfo.firstName ASC")
    Page<User> findAllNonSuperAdminByCompanyId(
            @Param("companyId") Long companyId,
            @Param("roleName1") String roleName1,
            @Param("roleName2") String roleName2,
            @Param("roleName3") String roleName3,
            Pageable pageable
    );

    // Super Admin
    @Query("SELECT u FROM User u WHERE u.company.id = :companyId AND u.isDeleted = false ORDER BY u.grade.gradeNumber ASC, u.grade.title ASC, u.basicInfo.firstName ASC")
    Page<User> findAllActiveByCompanyId(
            @Param("companyId") Long companyId,
            Pageable pageable
    );

    User findByIdAndIsDeletedFalse(long id);

    User getFirstById(long id);

    Page<User> findAllByIsDeletedTrueOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(Pageable pageable);

    User findByIdAndIsDeletedTrueOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(long id);

    Page<User> findAllByCompanyIdAndIsDeletedTrueOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(Long companyId, Pageable pageable);

    Page<User> findAllByCompanyIdAndBasicInfoFirstNameLikeOrBasicInfoLastNameLikeAndIsDeletedFalseOrderByLastUpdatedDesc(Long companyId, String firstName, String lastName, Pageable pageable);

    Page<User> findAllByCompanyIdAndBasicInfoFirstNameLikeOrBasicInfoLastNameLikeAndIsDeletedTrueOrderByLastUpdatedDesc(Long companyId, String firstName, String lastName, Pageable pageable);

    // Employee
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.role_id = :roleId AND u.is_deleted = false AND ((SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :firstName, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    Page<User> searchActiveUserByNameAndCompanyId(
            @Param("companyId") Long companyId,
            @Param("roleId") Long roleId,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            Pageable pageable
    );

    // Employee
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.role_id = :roleId AND u.is_deleted = false AND (SELECT department_id FROM professional_info WHERE id = u.professional_info_id) = :departmentId AND ((SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :firstName, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> searchActiveUserByNameCompanyAndDepartment(@Param("companyId") Long companyId,
                                                          @Param("roleId") Long roleId,
                                                          @Param("departmentId") Long departmentId,
                                                          @Param("firstName") String firstName,
                                                          @Param("lastName") String lastName) throws Exception;

    // Admin
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND (u.role_id = :roleId1 OR u.role_id = :roleId2 OR u.role_id = :roleId3) AND u.is_deleted = false AND ((SELECT middle_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :middleName, '%') OR (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :firstName, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%') OR (SELECT designation FROM professional_info WHERE id = u.professional_info_id) LIKE CONCAT('%', :lastName, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    Page<User> searchActiveNonSuperAdminUserByNameAndCompanyId(
            @Param("companyId") Long companyId,
            @Param("roleId1") Long roleId1,
            @Param("roleId2") Long roleId2,
            @Param("roleId3") Long roleId3,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("middleName") String middleName,
            Pageable pageable
    );

    // Admin
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND ((SELECT middle_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :middleName, '%') OR (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :firstName, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%') OR (SELECT designation FROM professional_info WHERE id = u.professional_info_id) LIKE CONCAT('%', :lastName, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    Page<User> searchActiveNonSuperAdminUserByNameAndCompanyId(
            @Param("companyId") Long companyId,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("middleName") String middleName,
            Pageable pageable
    );

    // Super Admin
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND ((SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :firstName, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%') OR (SELECT middle_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%') OR (SELECT designation FROM professional_info WHERE id = u.professional_info_id) LIKE CONCAT('%', :lastName, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    Page<User> searchActiveUserByNameAndCompanyId(
            @Param("companyId") Long companyId,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            Pageable pageable
    );

    // Admin
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND (u.role_id = :roleId1 OR u.role_id = :roleId2 OR u.role_id = :roleId3) AND u.is_deleted = false AND (SELECT department_id FROM professional_info WHERE id = u.professional_info_id) = :departmentId AND ((SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :firstName, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%') OR (SELECT middle_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%') OR (SELECT designation FROM professional_info WHERE id = u.professional_info_id) LIKE CONCAT('%', :lastName, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> searchActiveNonSuperAdminUserByNameCompanyAndDepartment(@Param("companyId") Long companyId,
                                                                       @Param("roleId1") Long roleId1,
                                                                       @Param("roleId2") Long roleId2,
                                                                       @Param("roleId3") Long roleId3,
                                                                       @Param("departmentId") Long departmentId,
                                                                       @Param("firstName") String firstName,
                                                                       @Param("lastName") String lastName) throws Exception;

    // Super Admin
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND (SELECT department_id FROM professional_info WHERE id = u.professional_info_id) = :departmentId AND ((SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :firstName, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%') OR (SELECT middle_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%') OR (SELECT designation FROM professional_info WHERE id = u.professional_info_id) LIKE CONCAT('%', :lastName, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> searchActiveUserByNameCompanyAndDepartment(@Param("companyId") Long companyId,
                                                          @Param("departmentId") Long departmentId,
                                                          @Param("firstName") String firstName,
                                                          @Param("lastName") String lastName) throws Exception;

    // Admin
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND (u.role_id = :roleId1 OR u.role_id = :roleId2 OR u.role_id = :roleId3) AND u.is_deleted = false AND (SELECT department_id FROM professional_info WHERE id = u.professional_info_id) = :departmentId ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    Page<User> findAllActiveNonSuperAdminUserByCompanyAndDepartment(@Param("companyId") Long companyId,
                                                                    @Param("roleId1") Long roleId1,
                                                                    @Param("roleId2") Long roleId2,
                                                                    @Param("roleId3") Long roleId3,
                                                                    @Param("departmentId") Long departmentId,
                                                                    Pageable pageable) throws Exception;

    // Super Admin
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND (SELECT department_id FROM professional_info WHERE id = u.professional_info_id) = :departmentId ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    Page<User> findAllActiveUserByCompanyAndDepartment(@Param("companyId") Long companyId,
                                                       @Param("departmentId") Long departmentId,
                                                       Pageable pageable) throws Exception;

    // Employee
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.role_id = :roleId AND u.is_deleted = false AND (SELECT department_id FROM professional_info WHERE id = u.professional_info_id) = :departmentId ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    Page<User> findAllActiveUserByCompanyAndDepartment(@Param("companyId") Long companyId,
                                                       @Param("roleId") Long roleId,
                                                       @Param("departmentId") Long departmentId,
                                                       Pageable pageable) throws Exception;

    // Admin
    // @Query("SELECT u FROM User u WHERE u.company.id = :companyId AND u.isDeleted = true AND (u.basicInfo.firstName LIKE CONCAT('%', :firstName, '%') OR u.basicInfo.lastName LIKE CONCAT('%', :lastName, '%')) AND (u.role.name = :roleName1 OR u.role.name = :roleName2 OR u.role.name = :roleName3)")
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND (u.role_id = :roleId1 OR u.role_id = :roleId2 OR u.role_id = :roleId3) AND u.is_deleted = true AND ((SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :firstName, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    Page<User> searchInactiveUserByNameAndCompanyId(
            @Param("companyId") Long companyId,
            @Param("roleId1") Long roleId1,
            @Param("roleId2") Long roleId2,
            @Param("roleId3") Long roleId3,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            Pageable pageable
    );

    // Super Admin
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = true AND ((SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :firstName, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%') OR (SELECT middle_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :lastName, '%') OR (SELECT designation FROM professional_info WHERE id = u.professional_info_id) LIKE CONCAT('%', :lastName, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    Page<User> searchInactiveUserByNameAndCompanyId(
            @Param("companyId") Long companyId,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            Pageable pageable
    );

    Page<User> findAllByGradeIdAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(Long id, Pageable pageable);

    Page<User> findAllByOfficeCodeIdAndOfficeCodeIsDeletedFalseAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(Long id, Pageable pageable);

    Page<User> findAllByOfficeCodeCodeAndOfficeCodeIsDeletedFalseAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(String code, Pageable pageable);

    // Super Admin and/or Admin
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND (SELECT department_id FROM professional_info WHERE id = u.professional_info_id) = :departmentId ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> findAllActiveUserByCompanyAndDepartment(@Param("companyId") Long companyId,
                                                       @Param("departmentId") Long departmentId) throws Exception;

    // Fetch all users by company, department, office code, active status, ordered by grade
    // Super Admin and/or Admin
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND (SELECT department_id FROM professional_info WHERE id = u.professional_info_id) = :departmentId AND u.office_code_id = :officeCodeId ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> findAllActiveUserByCompanyAndDepartmentAndOfficeCode(@Param("companyId") Long companyId,
                                                                    @Param("departmentId") Long departmentId,
                                                                    @Param("officeCodeId") Long officeCodeId) throws Exception;

    // Only applicable for custom department list
    // Fetch all users by company, office code, active status and custom department list, ordered by grade
    // Super Admin and/or Admin
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND ((SELECT name FROM departments WHERE id = (SELECT department_id FROM professional_info WHERE id = u.professional_info_id)) LIKE CONCAT('%', :department, '%')) AND u.office_code_id = :officeCodeId ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> findAllActiveUserByCompanyAndDepartmentAndOfficeCode(@Param("companyId") Long companyId,
                                                                    @Param("department") String department,
                                                                    @Param("officeCodeId") Long officeCodeId) throws Exception;

    // Only applicable for custom department list and not common in limited departments
    // Fetch all users by company, office code, active status and custom department list, ordered by grade
    // Super Admin and/or Admin
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND ((SELECT name FROM departments WHERE id = (SELECT department_id FROM professional_info WHERE id = u.professional_info_id)) NOT LIKE CONCAT('%', :department1, '%')) AND ((SELECT name FROM departments WHERE id = (SELECT department_id FROM professional_info WHERE id = u.professional_info_id)) NOT LIKE CONCAT('%', :department2, '%')) AND u.office_code_id = :officeCodeId ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> findAllActiveUserByCompanyAndDepartmentAndOfficeCode(@Param("companyId") Long companyId,
                                                                    @Param("department1") String department1,
                                                                    @Param("department2") String department2,
                                                                    @Param("officeCodeId") Long officeCodeId) throws Exception;

    /**
     * Customised search including parameters for company, department and office code
     */

    //-------------------
    // Active and List
    //-------------------

    // Part 1 (List)
    // Active users
    // Search by company, department and office code in list format
    // Already have that with the name: findAllActiveUserByCompanyAndDepartmentAndOfficeCode(Long companyId, Long departmentId, Long officeCodeId)

    // Part 2 (List)
    // Active users
    // Search by company and department in list format
    // Already have that with the name: findAllActiveUserByCompanyAndDepartment(Long companyId, Long departmentId)

    // Part 3 (List)
    // Active users
    // Search by company and office code in list format
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND u.office_code_id = :officeCodeId ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> findAllActiveUserByCompanyAndOfficeCode(@Param("companyId") Long companyId,
                                                       @Param("officeCodeId") Long officeCodeId) throws Exception;

    // Part 4 (List)
    // Active users
    // Search only by company in list format
    // Already have that with the name: findAllByCompanyIdAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(Long companyId)


    //--------------------------
    // Search, Active and List
    //--------------------------

    // Part 1 (Search)
    // Active users
    // Search by company, department and office code in list format
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND (SELECT department_id FROM professional_info WHERE id = u.professional_info_id) = :departmentId AND u.office_code_id = :officeCodeId AND ((SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :nameWord, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :nameWord, '%') OR (SELECT middle_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :nameWord, '%') OR (SELECT designation FROM professional_info WHERE id = u.professional_info_id) LIKE CONCAT('%', :nameWord, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> findAllActiveUserByCompanyAndDepartmentAndOfficeCode(@Param("companyId") Long companyId,
                                                                    @Param("departmentId") Long departmentId,
                                                                    @Param("officeCodeId") Long officeCodeId,
                                                                    @Param("nameWord") String nameWord) throws Exception;

    // Part 2 (Search)
    // Active users
    // Search by company and department in list format
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND (SELECT department_id FROM professional_info WHERE id = u.professional_info_id) = :departmentId AND ((SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :nameWord, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :nameWord, '%') OR (SELECT middle_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :nameWord, '%') OR (SELECT designation FROM professional_info WHERE id = u.professional_info_id) LIKE CONCAT('%', :nameWord, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> findAllActiveUserByCompanyAndDepartment(@Param("companyId") Long companyId,
                                                       @Param("departmentId") Long departmentId,
                                                       @Param("nameWord") String nameWord) throws Exception;

    // Part 3 (Search)
    // Active users
    // Search by company and office code in list format
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND u.office_code_id = :officeCodeId AND ((SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :nameWord, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :nameWord, '%') OR (SELECT middle_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :nameWord, '%') OR (SELECT designation FROM professional_info WHERE id = u.professional_info_id) LIKE CONCAT('%', :nameWord, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> findAllActiveUserByCompanyAndOfficeCode(@Param("companyId") Long companyId,
                                                       @Param("officeCodeId") Long officeCodeId,
                                                       @Param("nameWord") String nameWord) throws Exception;

    // Part 4 (Search)
    // Active users
    // Search only by company in list format
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = false AND ((SELECT first_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :nameWord, '%') OR (SELECT last_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :nameWord, '%') OR (SELECT middle_name FROM basic_info WHERE id = u.basic_info_id) LIKE CONCAT('%', :nameWord, '%') OR (SELECT designation FROM professional_info WHERE id = u.professional_info_id) LIKE CONCAT('%', :nameWord, '%')) ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> findAllActiveUserByCompany(@Param("companyId") Long companyId,
                                          @Param("nameWord") String nameWord) throws Exception;


    //--------------------
    // Inactive and List
    //--------------------

    // Part 1 (Inactive)
    // Search by company, department and office code in list format
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = true AND (SELECT department_id FROM professional_info WHERE id = u.professional_info_id) = :departmentId AND u.office_code_id = :officeCodeId ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> findAllInactiveUserByCompanyAndDepartmentAndOfficeCode(@Param("companyId") Long companyId,
                                                                      @Param("departmentId") Long departmentId,
                                                                      @Param("officeCodeId") Long officeCodeId) throws Exception;

    // Part 2 (Inactive)
    // Search by company and department in list format
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = true AND (SELECT department_id FROM professional_info WHERE id = u.professional_info_id) = :departmentId ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> findAllInactiveUserByCompanyAndDepartment(@Param("companyId") Long companyId,
                                                         @Param("departmentId") Long departmentId) throws Exception;

    // Part 3 (Inactive)
    // Search by company and office code in list format
    @Query(value = "SELECT * FROM users u WHERE u.company_id = :companyId AND u.is_deleted = true AND u.office_code_id = :officeCodeId ORDER BY (SELECT grade_number FROM grades WHERE id = u.grade_id) ASC, (SELECT title FROM grades WHERE id = u.grade_id) ASC, (SELECT first_name FROM basic_info WHERE id = u.basic_info_id) ASC", nativeQuery = true)
    List<User> findAllInactiveUserByCompanyAndOfficeCode(@Param("companyId") Long companyId,
                                                         @Param("officeCodeId") Long officeCodeId) throws Exception;

    // Part 4 (Inactive)
    // Search by company in list format
    List<User> findAllByCompanyIdAndIsDeletedTrueOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(Long companyId);


    /**********************************************************************************/

    /**
     *
     * @param companyId
     * @param roleName1
     * @param roleName2
     * @param roleName3
     * @return users
     */
    //------------------------
    // Lists all admins for a company
    //------------------------
    @Query("SELECT u FROM User u WHERE u.company.id = :companyId AND (u.role.name = :roleName1 OR u.role.name = :roleName2 OR u.role.name = :roleName3) AND u.isDeleted = false ORDER BY u.grade.gradeNumber ASC, u.grade.title ASC, u.basicInfo.firstName ASC")
    List<User> findAllAdminByCompanyId(@Param("companyId") Long companyId,
                                       @Param("roleName1") String roleName1,
                                       @Param("roleName2") String roleName2,
                                       @Param("roleName3") String roleName3);

    //------------------------
    // Lists all admins and accounts people for a company
    //------------------------
    @Query("SELECT u FROM User u WHERE u.company.id = :companyId AND u.isDeleted = false AND ((u.role.name = :roleName1 OR u.role.name = :roleName2 OR u.role.name = :roleName3) OR u.professionalInfo.department.id = :departmentId) ORDER BY u.grade.gradeNumber ASC, u.grade.title ASC, u.basicInfo.firstName ASC")
    List<User> findAllAdminByCompanyId(@Param("companyId") Long companyId,
                                       @Param("roleName1") String roleName1,
                                       @Param("roleName2") String roleName2,
                                       @Param("roleName3") String roleName3,
                                       @Param("departmentId") Long departmentId);

}