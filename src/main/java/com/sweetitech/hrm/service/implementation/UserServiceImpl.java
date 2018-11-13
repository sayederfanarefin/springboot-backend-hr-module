package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.exception.NotFoundException;
import com.sweetitech.hrm.common.exception.NullPasswordException;
import com.sweetitech.hrm.common.exception.UserNotFoundException;
import com.sweetitech.hrm.common.util.ImageValidator;
import com.sweetitech.hrm.common.util.PasswordUtil;
import com.sweetitech.hrm.domain.*;
import com.sweetitech.hrm.domain.authentication.OAuthAccessToken;
import com.sweetitech.hrm.domain.dto.UserSmallResponseDTO;
import com.sweetitech.hrm.repository.OAuthAccessTokenRepository;
import com.sweetitech.hrm.repository.UserRepository;
import com.sweetitech.hrm.service.GradeService;
import com.sweetitech.hrm.service.UserService;
import com.sweetitech.hrm.service.mapping.UserSmallResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sweetitech.hrm.common.util.PasswordUtil.EncType.BCRYPT_ENCODER;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private OAuthAccessTokenRepository oAuthAccessTokenRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private GradeServiceImpl gradeService;

    @Autowired
    private OfficeCodeServiceImpl officeCodeService;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OAuthAccessTokenRepository oAuthAccessTokenRepository) {
        this.userRepository = userRepository;
        this.oAuthAccessTokenRepository = oAuthAccessTokenRepository;
    }

    @Override
    public User readByUsername(String username) throws UserNotFoundException {
        System.out.println("From username in impl:" + username);
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isPasswordMatches(User user, String password) throws Exception {
        /**
         * Check if password matches with shaencoder, if matches encode it with bicrypt and save to the database.
         */
        if (PasswordUtil.encryptPassword(password).equals(user.getPassword())) {
            user.setPassword(PasswordUtil.encryptPassword(password));
            user = userRepository.save(user);
        }

        return PasswordUtil.getPasswordEncoder().matches(password, user.getPassword());
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User read(long id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<User> readByCompanyId(Long id, Integer page) {
        return userRepository.findAllByCompanyIdAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(id, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public List<User> readByCompanyId(Long id) throws Exception {
        if (companyService.read(id) ==  null) {
            throw new EntityNotFoundException("No companies with id: " + id);
        }

        return userRepository.findAllByCompanyIdAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(id);
    }

    @Override
    public Page<User> readEmployeesByCompanyId(Long id, Integer page) throws Exception {
        return userRepository.findAllByCompanyIdAndRoleNameAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(id,
                Constants.Roles.ROLE_EMPLOYEE, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<User> readNonAdminsByCompanyId(Long id, Integer page) throws Exception {
        Company company = companyService.read(id);
        if (company == null) {
            throw new EntityNotFoundException("No company found with id: " + id);
        }

        return userRepository.findAllActiveByCompanyId(id,
                new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public User readById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public User update(User user) {
        System.out.println("New User: " + user.toString());

        User userToUpdate = userRepository.getFirstById(user.getId());

        System.out.println("Old User: "  + user.toString());

        userToUpdate.setMrCode(user.getMrCode());
        userToUpdate.setRole(user.getRole());
        userToUpdate.setCompany(user.getCompany());
        userToUpdate.setBasicInfo(user.getBasicInfo());
        userToUpdate.setContactInfo(user.getContactInfo());
        userToUpdate.setEmergencyContact(user.getEmergencyContact());
        userToUpdate.setProfessionalInfo(user.getProfessionalInfo());
        userToUpdate.setDeleted(user.isDeleted());

        return userRepository.save(user);
    }

    @Override
    public void updateProfilePicture(Long id, MultipartFile multipartFile) throws NotFoundException, IOException {
        if (id == null) throw new NotFoundException("User id can not be null");
        User user = userRepository.getFirstById(id);
        if (user == null) throw new NotFoundException("User not found with id " + id);
        if (ImageValidator.isImageValid(multipartFile)) {
//            user.getBasicInfo().setProfilePicture(multipartFile.getBytes());
        }
        userRepository.save(user);
    }

    @Override
    public User updateRole(Long userId, Long roleId) throws EntityNotFoundException {
        User user = this.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("User does not exist!");
        }

        Role role = roleService.findRoleById(roleId);
        if (role == null) {
            throw  new EntityNotFoundException("No roles with id: " + roleId);
        }

        user.setRole(role);
        user = userRepository.save(user);
        if (user == null) {
            throw new EntityNotFoundException("Failed to update role!");
        }

        OAuthAccessToken oAuthAccessToken = oAuthAccessTokenRepository.getFirstByUserName(user.getUsername());

        if (oAuthAccessToken != null)
            oAuthAccessTokenRepository.delete(oAuthAccessToken);

        return user;
    }

    @Override
    public Page<User> readAllInactive(Integer page) {
        return userRepository.findAllByIsDeletedTrueOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public void remove(Long id) throws Exception {
        User user = this.read(id);
        if (user == null) {
            throw new EntityNotFoundException("No user found with id: " + id);
        }

        user.setDeleted(true);
        user = userRepository.save(user);
        if (user == null) {
            throw new EntityNotFoundException("Failed to remove user!");
        }

        OAuthAccessToken oAuthAccessToken = oAuthAccessTokenRepository.getFirstByUserName(user.getUsername());

        if (oAuthAccessToken != null)
            oAuthAccessTokenRepository.delete(oAuthAccessToken);
    }

    @Override
    public User readInactiveById(Long id) throws Exception {
        User user = userRepository.findByIdAndIsDeletedTrueOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(id);
        if (user == null) {
            throw new EntityNotFoundException("No inactive user with id: " + id);
        }

        return user;
    }

    @Override
    public User updateInactiveUser(Long id) throws Exception {
        User user = userRepository.findByIdAndIsDeletedTrueOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(id);
        if (user == null) {
            throw new EntityNotFoundException("No inactive user with id: " + id);
        }

        user.setDeleted(false);
        user = userRepository.save(user);
        if (user == null) {
            throw new EntityNotFoundException("Failed to activate user!");
        }

        OAuthAccessToken oAuthAccessToken = oAuthAccessTokenRepository.getFirstByUserName(user.getUsername());

        if (oAuthAccessToken != null)
            oAuthAccessTokenRepository.delete(oAuthAccessToken);

        return user;
    }

    @Override
    public Page<User> readAllInactiveByCompanyId(Long id, Integer page) throws Exception {
        Company company = companyService.read(id);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + id);
        }

        return userRepository.findAllByCompanyIdAndIsDeletedTrueOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(id, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<User> searchActiveByCompanyIdAndName(Long id, String firstName, String lastName, String middleName, Integer page) throws Exception {
        Company company = companyService.read(id);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + id);
        }

        Role role = roleService.findRoleByName(Constants.Roles.ROLE_EMPLOYEE);

        System.out.println("/***************************************/");
        System.out.println(firstName + " " + middleName + " " + lastName);
        System.out.println("/***************************************/");

        return userRepository.searchActiveNonSuperAdminUserByNameAndCompanyId(id,
                Constants.RoleIds.ROLE_ADMIN_1,
                Constants.RoleIds.ROLE_ADMIN_2,
                role.getId(),
                firstName, lastName, middleName, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public List<UserSmallResponseDTO> searchActiveByCompanyIdDepartmentIdAndName(Long id,
                                                                                 Long departmentId,
                                                                                 String firstName,
                                                                                 String lastName) throws Exception {
        Company company = companyService.read(id);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + id);
        }

        Department department = departmentService.read(departmentId);
        if (department == null) {
            throw new EntityNotFoundException("No department with id: " + departmentId);
        }

        Role role = roleService.findRoleByName(Constants.Roles.ROLE_EMPLOYEE);

        List<UserSmallResponseDTO> dtos = new ArrayList<>();

        List<User> users = userRepository.searchActiveNonSuperAdminUserByNameCompanyAndDepartment(id,
                Constants.RoleIds.ROLE_ADMIN_1,
                Constants.RoleIds.ROLE_ADMIN_2,
                role.getId(),
                departmentId,
                firstName, lastName);

        if (users != null && users.size() > 0) {
            for (User user: users) {
                dtos.add(userSmallResponseMapper.map(user));
            }
        }

        return dtos;
    }

    @Override
    public Page<User> searchActiveNonSuperAdminByCompanyIdAndName(Long id, String firstName, String lastName, Integer page) throws Exception {
        Company company = companyService.read(id);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + id);
        }

        return userRepository.searchActiveUserByNameAndCompanyId(id,
                firstName, lastName, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public List<UserSmallResponseDTO> searchActiveNonSuperAdminByCompanyIdDepartmentIdAndName(Long id,
                                                                              Long departmentId,
                                                                              String firstName,
                                                                              String lastName) throws Exception {
        Company company = companyService.read(id);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + id);
        }

        Department department = departmentService.read(departmentId);
        if (department == null) {
            throw new EntityNotFoundException("No department with id: " + departmentId);
        }

        List<UserSmallResponseDTO> dtos = new ArrayList<>();

        List<User> users = userRepository.searchActiveUserByNameCompanyAndDepartment(id,
                departmentId,
                firstName, lastName);

        if (users != null && users.size() > 0) {
            for (User user: users) {
                dtos.add(userSmallResponseMapper.map(user));
            }
        }

        return dtos;
    }

    @Override
    public Page<User> readAllActiveNonSuperAdminByCompanyIdAndDepartmentId(Long id,
                                                                           Long departmentId,
                                                                           Integer page) throws Exception {
        Company company = companyService.read(id);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + id);
        }

        Department department = departmentService.read(departmentId);
        if (department == null) {
            throw new EntityNotFoundException("No department with id: " + departmentId);
        }

        return userRepository.findAllActiveUserByCompanyAndDepartment(id,
                departmentId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public List<UserSmallResponseDTO> readAllActiveUsersByCompanyAndDepartment(Long companyId,
                                                                               Long departmentId) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        Department department = departmentService.read(departmentId);
        if (department == null) {
            throw new EntityNotFoundException("No department with id: " + departmentId);
        }

        List<UserSmallResponseDTO> dtos = new ArrayList<>();

        List<User> users = userRepository.findAllActiveUserByCompanyAndDepartment(companyId, departmentId);
        if (users != null && users.size() > 0) {
            for (User user: users) {
                dtos.add(userSmallResponseMapper.map(user));
            }
        }

        return dtos;
    }

    @Override
    public Page<User> readAllActiveEmployeeByCompanyIdAndDepartmentId(Long id,
                                                                      Long departmentId,
                                                                      Integer page) throws Exception {
        Company company = companyService.read(id);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + id);
        }

        Department department = departmentService.read(departmentId);
        if (department == null) {
            throw new EntityNotFoundException("No department with id: " + departmentId);
        }

        return userRepository.findAllActiveNonSuperAdminUserByCompanyAndDepartment(id,
                Constants.RoleIds.ROLE_ADMIN_1,
                Constants.RoleIds.ROLE_ADMIN_2,
                Constants.RoleIds.ROLE_EMPLOYEE,
                departmentId, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<User> searchInactiveByCompanyIdAndName(Long id, String firstName, String lastName, Integer page) throws Exception {
        Company company = companyService.read(id);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + id);
        }

        return userRepository.searchInactiveUserByNameAndCompanyId(id,
                firstName, lastName, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public User updateGrade(Long userId, Long gradeId) throws Exception {
        User user = userRepository.getFirstById(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        Grade grade = gradeService.read(gradeId);
        if (grade == null) {
            throw new EntityNotFoundException("No grade with id: " + gradeId);
        }

        user.setGrade(grade);
        return userRepository.save(user);
    }

    @Override
    public Page<User> readAllByGrade(Long id, Integer page) throws Exception {
        Grade grade = gradeService.read(id);
        if (grade == null) {
            throw new EntityNotFoundException("No grade with id: " + id);
        }

        return userRepository.findAllByGradeIdAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(id, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public User updatePassword(User user, String currentPassword, String newPassword) throws Exception {
        User userToUpdate = userRepository.getFirstById(user.getId());

        if (userToUpdate == null) {
            throw new EntityNotFoundException("No User with such Id!");
        }

        boolean isPasswordMatches = this.isPasswordMatches(user, currentPassword);

        if (isPasswordMatches) {
            userToUpdate.setPassword(PasswordUtil.encryptPassword(newPassword));
        } else {
            throw new EntityNotFoundException("Password does not match!");
        }

        return userRepository.save(userToUpdate);
    }

    @Override
    public User resetPassword(Long id, String newPassword) throws Exception {
        User userToUpdate = userRepository.getFirstById(id);
        if (userToUpdate == null) {
            throw new EntityNotFoundException("No User with such Id!");
        }

        if (newPassword.length() < 6) {
            throw new NullPasswordException("Password must be at least 6 characters long!");
        }

        userToUpdate.setPassword(PasswordUtil.encryptPassword(newPassword));
        return userRepository.save(userToUpdate);
    }

    @Override
    public Page<User> readAllActiveByOfficeCodeId(Long id, Integer page) throws Exception {
        if (officeCodeService.readById(id) == null) {
            throw new EntityNotFoundException("No office codes with id: " + id);
        }

        return userRepository.findAllByOfficeCodeIdAndOfficeCodeIsDeletedFalseAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(id, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public Page<User> readAllActiveByOfficeCodeCode(String code, Integer page) throws Exception {
        if (officeCodeService.readByCode(code) == null) {
            throw new EntityNotFoundException("No office codes with code: " + code);
        }

        return userRepository.findAllByOfficeCodeCodeAndOfficeCodeIsDeletedFalseAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(code, new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public User updateUsername(Long id, String username) throws Exception {
        User user = userRepository.getFirstById(id);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + id);
        }

        user.setUsername(username);
        return userRepository.save(user);
    }

    @Override
    public UserSmallResponseDTO readSmallResponse(Long id) throws Exception {
        User user = userRepository.getFirstById(id);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + id);
        }

        return userSmallResponseMapper.map(user);
    }

    @Override
    public List<UserSmallResponseDTO> readAllByCompanyDepartmentAndOfficeCode(Long companyId,
                                                                              Long departmentId,
                                                                              Long officeCodeId) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        Department department = departmentService.read(departmentId);
        if (department == null) {
            throw new EntityNotFoundException("No department with id: " + departmentId);
        }

        OfficeCode officeCode = officeCodeService.read(officeCodeId);
        if (officeCode == null) {
            throw new EntityNotFoundException("No office codes with id: " + officeCodeId);
        }

        List<UserSmallResponseDTO> dtos = new ArrayList<>();

        List<User> users = userRepository.findAllActiveUserByCompanyAndDepartmentAndOfficeCode(companyId,
                departmentId, officeCodeId);
        if (users != null && users.size() > 0) {
            for (User user: users) {
                dtos.add(userSmallResponseMapper.map(user));
            }
        }

        return dtos;

    }

    @Override
    public List<UserSmallResponseDTO> readAllByCompanyDepartmentAndOfficeCode(Long companyId,
                                                                              String department,
                                                                              Long officeCodeId) throws Exception {
        Company company = companyService.read(companyId);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        OfficeCode officeCode = officeCodeService.read(officeCodeId);
        if (officeCode == null) {
            throw new EntityNotFoundException("No office codes with id: " + officeCodeId);
        }

        List<UserSmallResponseDTO> dtos = new ArrayList<>();

        List<User> users = new ArrayList<>();

        if (!department.equals(Constants.CustomDepartments.OTHER))
            users = userRepository.findAllActiveUserByCompanyAndDepartmentAndOfficeCode(companyId,
                    department, officeCodeId);
        else {
            users = userRepository.findAllActiveUserByCompanyAndDepartmentAndOfficeCode(companyId,
                    Constants.CustomDepartments.SALES, Constants.CustomDepartments.APPLICATION, officeCodeId);
        }

        if (users != null && users.size() > 0) {
            for (User user: users) {
                dtos.add(userSmallResponseMapper.map(user));
            }
        }

        return dtos;

    }

    @Override
    public List<User> listActiveUsers(Long companyId, Long departmentId, Long officeCodeId) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id:" + companyId);
        }

        if (departmentId != -1 && officeCodeId != -1) {
            if (departmentService.read(departmentId) == null || officeCodeService.read(officeCodeId) == null) {
                throw new EntityNotFoundException("Invalid department or office code id!");
            }

            return userRepository.findAllActiveUserByCompanyAndDepartmentAndOfficeCode(companyId, departmentId, officeCodeId);
        }

        if (departmentId != -1 && officeCodeId == -1) {
            if (departmentService.read(departmentId) == null) {
                throw new EntityNotFoundException("No departments with id: " + departmentId);
            }

            return userRepository.findAllActiveUserByCompanyAndDepartment(companyId, departmentId);
        }

        if (departmentId == -1 && officeCodeId != -1) {
            if (officeCodeService.read(officeCodeId) == null) {
                throw new EntityNotFoundException("No office codes with id: " + officeCodeId);
            }

            return userRepository.findAllActiveUserByCompanyAndOfficeCode(companyId, officeCodeId);
        }

        if (departmentId == -1 && officeCodeId == -1) {
            return userRepository.findAllByCompanyIdAndIsDeletedFalseOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(companyId);
        }

        return null;
    }

    @Override
    public List<User> searchActiveUsers(Long companyId, Long departmentId, Long officeCodeId, String name) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id:" + companyId);
        }

        if (departmentId != -1 && officeCodeId != -1) {
            if (departmentService.read(departmentId) == null || officeCodeService.read(officeCodeId) == null) {
                throw new EntityNotFoundException("Invalid department or office code id!");
            }

            return userRepository.findAllActiveUserByCompanyAndDepartmentAndOfficeCode(companyId, departmentId, officeCodeId, name);
        }

        if (departmentId != -1 && officeCodeId == -1) {
            if (departmentService.read(departmentId) == null) {
                throw new EntityNotFoundException("No departments with id: " + departmentId);
            }

            return userRepository.findAllActiveUserByCompanyAndDepartment(companyId, departmentId, name);
        }

        if (departmentId == -1 && officeCodeId != -1) {
            if (officeCodeService.read(officeCodeId) == null) {
                throw new EntityNotFoundException("No office codes with id: " + officeCodeId);
            }

            return userRepository.findAllActiveUserByCompanyAndOfficeCode(companyId, officeCodeId, name);
        }

        if (departmentId == -1 && officeCodeId == -1) {
            return userRepository.findAllActiveUserByCompany(companyId, name);
        }

        return null;
    }

    @Override
    public List<User> listInactiveUsers(Long companyId, Long departmentId, Long officeCodeId) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id:" + companyId);
        }

        if (departmentId != -1 && officeCodeId != -1) {
            if (departmentService.read(departmentId) == null || officeCodeService.read(officeCodeId) == null) {
                throw new EntityNotFoundException("Invalid department or office code id!");
            }

            return userRepository.findAllInactiveUserByCompanyAndDepartmentAndOfficeCode(companyId, departmentId, officeCodeId);
        }

        if (departmentId != -1 && officeCodeId == -1) {
            if (departmentService.read(departmentId) == null) {
                throw new EntityNotFoundException("No departments with id: " + departmentId);
            }

            return userRepository.findAllInactiveUserByCompanyAndDepartment(companyId, departmentId);
        }

        if (departmentId == -1 && officeCodeId != -1) {
            if (officeCodeService.read(officeCodeId) == null) {
                throw new EntityNotFoundException("No office codes with id: " + officeCodeId);
            }

            return userRepository.findAllInactiveUserByCompanyAndOfficeCode(companyId, officeCodeId);
        }

        if (departmentId == -1 && officeCodeId == -1) {
            return userRepository.findAllByCompanyIdAndIsDeletedTrueOrderByGradeGradeNumberAscGradeTitleAscBasicInfoFirstNameAsc(companyId);
        }

        return null;
    }

    @Override
    public List<User> readAllAdminsByCompany(Long companyId) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        return userRepository.findAllAdminByCompanyId(companyId,
                Constants.Roles.ROLE_SUPER_ADMIN,
                Constants.Roles.ROLE_ADMIN_1,
                Constants.Roles.ROLE_ADMIN_2);
    }

    @Override
    public List<User> readAllAdminsAndAccounts(Long companyId) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        return userRepository.findAllAdminByCompanyId(companyId,
                Constants.Roles.ROLE_SUPER_ADMIN,
                Constants.Roles.ROLE_ADMIN_1,
                Constants.Roles.ROLE_ADMIN_2,
                departmentService.getIdOfAccounts(companyId));
    }

    @Override
    public List<User> readAllGeneralAdminsAndAccounts(Long companyId) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No companies with id: " + companyId);
        }

        return userRepository.findAllAdminByCompanyId(companyId,
                Constants.Roles.ROLE_SUPER_ADMIN,
                Constants.Roles.ROLE_ADMIN_1,
                Constants.Roles.ROLE_ADMIN_1,
                departmentService.getIdOfAccounts(companyId));
    }

    @Override
    public Page<User> searchAllActiveByCompanyIdAndName(Long id, String firstName, String lastName, String middleName, Integer page) throws Exception {
        Company company = companyService.read(id);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + id);
        }

        return userRepository.searchActiveNonSuperAdminUserByNameAndCompanyId(id,
                firstName, lastName, middleName, new PageRequest(page, PageAttr.PAGE_SIZE));
    }
}