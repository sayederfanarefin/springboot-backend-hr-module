package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.PasswordUtil;
import com.sweetitech.hrm.domain.*;
import com.sweetitech.hrm.domain.dto.UserDTO;
import com.sweetitech.hrm.service.CompanyService;
import com.sweetitech.hrm.service.RoleService;
import com.sweetitech.hrm.service.UserService;
import com.sweetitech.hrm.service.implementation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.sweetitech.hrm.common.util.PasswordUtil.EncType.BCRYPT_ENCODER;

@Service
public class UserMapper {

    @Autowired
    private RoleService roleService;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private GradeServiceImpl gradeService;

    @Autowired
    private ImageServiceImpl imageService;

    @Autowired
    private OfficeCodeServiceImpl officeCodeService;

    /**
     * Maps DTO to Entity
     *
     * @param dto
     * @return entity
     */
    public User map(UserDTO dto) throws Exception {

        Role role = roleService.findRoleById(dto.getRoleId());
        if (role == null) {
            throw new EntityNotFoundException("Role does not exist!");
        }

        Company company = companyService.read(dto.getCompanyId());
        if (company == null) {
            throw new EntityNotFoundException("Company does not exist!");
        }

        Department department = departmentService.read(dto.getDepartmentId());
        if (department == null) {
            throw new EntityNotFoundException("Department does not exist!");
        }

        Grade grade = gradeService.read(dto.getGradeId());

        Image image = imageService.findById(dto.getProfilePictureId());

//        User user = userService.read(dto.getCreatedByUserId());
//        if (user == null) {
//            throw new EntityNotFoundException("User does not exist!");
//        }

        OfficeCode officeCode = officeCodeService.read(dto.getOfficeCodeId());

        User entity = new User();
//        entity.setDesignation(dto.getDesignation());
        entity.setUsername(dto.getUsername());
        entity.setPassword(PasswordUtil.encryptPassword(dto.getPassword(), BCRYPT_ENCODER, null));
        entity.setMrCode(dto.getMrCode());
        entity.setRole(role);
        entity.setCompany(company);

        // Basic Info
        entity.setBasicInfo(new BasicInfo());
        entity.getBasicInfo().setFirstName(dto.getFirstName());
        entity.getBasicInfo().setMiddleName(dto.getMiddleName());
        entity.getBasicInfo().setLastName(dto.getLastName());
        entity.getBasicInfo().setDob(dto.getDob());
        entity.getBasicInfo().setNationality(dto.getNationality());
        entity.getBasicInfo().setNationalId(dto.getNationalId());
        entity.getBasicInfo().setPassportNumber(dto.getPassportNumber());
        entity.getBasicInfo().setMaritalStatus(dto.getMaritalStatus());
        entity.getBasicInfo().setGender(dto.getGender());
        entity.getBasicInfo().setBloodGroup(dto.getBloodGroup());
        entity.getBasicInfo().setJoiningDate(dto.getJoiningDate());
        entity.getBasicInfo().setReligion(dto.getReligion());
        entity.getBasicInfo().setProfilePicture(image);
//        entity.getBasicInfo().setCreatedBy(user);

        // Contact Info
        entity.setContactInfo(new ContactInfo());
        entity.getContactInfo().setMobile(dto.getMobile());
        entity.getContactInfo().setEmail(dto.getEmail());
        entity.getContactInfo().setPresentAddress(dto.getPresentAddress());
        entity.getContactInfo().setPermanentAddress(dto.getPermanentAddress());

        // Emergency Contact
        entity.setEmergencyContact(new EmergencyContact());
        entity.getEmergencyContact().setName(dto.getEmergencyContactName());
        entity.getEmergencyContact().setRelation(dto.getRelation());
        entity.getEmergencyContact().setMobile(dto.getEmergencyContactMobile());
        entity.getEmergencyContact().setAddress(dto.getEmergencyContactAddress());

        // Professional Info
        entity.setProfessionalInfo(new ProfessionalInfo());
        entity.getProfessionalInfo().setDesignation(dto.getDesignation());
        entity.getProfessionalInfo().setDepartment(department);

        // Grade
        entity.setGrade(grade);

        // Office Code
        entity.setOfficeCode(officeCode);

        return entity;
    }

    /**
     * Maps Entity to DTO
     *
     * @param entity
     * @return dto
     */
    public UserDTO map(User entity) throws Exception {

        UserDTO dto = new UserDTO();

        dto.setUsername(entity.getUsername());
        dto.setMrCode(entity.getMrCode());
        dto.setRoleId(entity.getRole().getId());
        dto.setCompanyId(entity.getCompany().getId());
        dto.setFirstName(entity.getBasicInfo().getFirstName());
        dto.setMiddleName(entity.getBasicInfo().getMiddleName());
        dto.setLastName(entity.getBasicInfo().getLastName());
        dto.setDob(entity.getBasicInfo().getDob());
        dto.setNationality(entity.getBasicInfo().getNationality());
        dto.setNationalId(entity.getBasicInfo().getNationalId());
        dto.setPassportNumber(entity.getBasicInfo().getPassportNumber());
        dto.setMaritalStatus(entity.getBasicInfo().getMaritalStatus());
        dto.setGender(entity.getBasicInfo().getGender());
        dto.setBloodGroup(entity.getBasicInfo().getBloodGroup());
        dto.setJoiningDate(entity.getBasicInfo().getJoiningDate());
        dto.setReligion(entity.getBasicInfo().getReligion());
        if (entity.getBasicInfo().getProfilePicture() != null)
            dto.setProfilePictureId(entity.getBasicInfo().getProfilePicture().getId());
        else dto.setProfilePictureId((long) -1);
//        dto.setCreatedByUserId(entity.getBasicInfo().getCreatedBy().getId());
        dto.setMobile(entity.getContactInfo().getMobile());
        dto.setEmail(entity.getContactInfo().getEmail());
        dto.setPresentAddress(entity.getContactInfo().getPresentAddress());
        dto.setPermanentAddress(entity.getContactInfo().getPermanentAddress());
        dto.setEmergencyContactName(entity.getEmergencyContact().getName());
        dto.setRelation(entity.getEmergencyContact().getRelation());
        dto.setEmergencyContactMobile(entity.getEmergencyContact().getMobile());
        dto.setEmergencyContactAddress(entity.getEmergencyContact().getAddress());
        dto.setDesignation(entity.getProfessionalInfo().getDesignation());
        dto.setDepartmentId(entity.getProfessionalInfo().getDepartment().getId());
        if (entity.getGrade() != null) {
            dto.setGradeId(entity.getGrade().getId());
        } else {
            dto.setGradeId((long) -1);
        }
        if (entity.getOfficeCode() != null) {
            dto.setOfficeCodeId(entity.getOfficeCode().getId());
        } else {
            dto.setOfficeCodeId((long) -1);
        }

        return dto;
    }

}