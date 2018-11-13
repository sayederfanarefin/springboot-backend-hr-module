package com.sweetitech.hrm.service;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.exception.NotFoundException;
import com.sweetitech.hrm.common.exception.UserNotFoundException;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.UserSmallResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    User readByUsername(String username) throws UserNotFoundException;

    boolean isPasswordMatches(User user, String password) throws Exception;

    User create(User user);

    User read(long id);

    Page<User> readByCompanyId(Long id, Integer page);

    List<User> readByCompanyId(Long id) throws Exception;

    Page<User> readEmployeesByCompanyId(Long id, Integer page) throws Exception;

    Page<User> readNonAdminsByCompanyId(Long id, Integer page) throws Exception;

    User readById(long id);

    User update(User user);

    void updateProfilePicture(Long id, MultipartFile multipartFile) throws NotFoundException, IOException;

    User updateRole(Long userId, Long roleId) throws EntityNotFoundException;

    Page<User> readAllInactive(Integer page);

    void remove(Long id) throws Exception;

    User readInactiveById(Long id) throws Exception;

    User updateInactiveUser(Long id) throws Exception;

    Page<User> readAllInactiveByCompanyId(Long id, Integer page) throws Exception;

    Page<User> searchActiveByCompanyIdAndName(Long id, String firstName, String lastName, String middleName, Integer page) throws Exception;

    Page<User> searchActiveNonSuperAdminByCompanyIdAndName(Long id, String firstName, String lastName, Integer page) throws Exception;

    Page<User> searchInactiveByCompanyIdAndName(Long id, String firstName, String lastName, Integer page) throws Exception;

    User updateGrade(Long userId, Long gradeId) throws Exception;

    Page<User> readAllByGrade(Long id, Integer page) throws Exception;

    User updatePassword(User user, String currentPassword, String newPassword) throws Exception;

    User resetPassword(Long id, String newPassword) throws Exception;

    Page<User> readAllActiveByOfficeCodeId(Long id, Integer page) throws Exception;

    Page<User> readAllActiveByOfficeCodeCode(String code, Integer page) throws Exception;

    User updateUsername(Long id, String username) throws Exception;

    List<UserSmallResponseDTO> searchActiveByCompanyIdDepartmentIdAndName(Long id,
                                                                                 Long departmentId,
                                                                                 String firstName,
                                                                                 String lastName) throws Exception;

    List<UserSmallResponseDTO> searchActiveNonSuperAdminByCompanyIdDepartmentIdAndName(Long id,
                                                                                       Long departmentId,
                                                                                       String firstName,
                                                                                       String lastName) throws Exception;

    Page<User> readAllActiveNonSuperAdminByCompanyIdAndDepartmentId(Long id,
                                                                    Long departmentId,
                                                                    Integer page) throws Exception;

    Page<User> readAllActiveEmployeeByCompanyIdAndDepartmentId(Long id,
                                                               Long departmentId,
                                                               Integer page) throws Exception;

    List<UserSmallResponseDTO> readAllActiveUsersByCompanyAndDepartment(Long companyId,
                                                                        Long departmentId) throws Exception;

    UserSmallResponseDTO readSmallResponse(Long id) throws Exception;

    List<UserSmallResponseDTO> readAllByCompanyDepartmentAndOfficeCode(Long companyId,
                                                                       Long departmentId,
                                                                       Long officeCodeId) throws Exception;

    List<UserSmallResponseDTO> readAllByCompanyDepartmentAndOfficeCode(Long companyId,
                                                                       String department,
                                                                       Long officeCodeId) throws Exception;

    List<User> listActiveUsers(Long companyId, Long departmentId, Long officeCodeId) throws Exception;

    List<User> searchActiveUsers(Long companyId, Long departmentId, Long officeCodeId, String name) throws Exception;

    List<User> listInactiveUsers(Long companyId, Long departmentId, Long officeCodeId) throws Exception;

    List<User> readAllAdminsByCompany(Long companyId) throws Exception;

    List<User> readAllAdminsAndAccounts(Long companyId) throws Exception;

    List<User> readAllGeneralAdminsAndAccounts(Long companyId) throws Exception;

    Page<User> searchAllActiveByCompanyIdAndName(Long id, String firstName, String lastName, String middleName, Integer page) throws Exception;
}