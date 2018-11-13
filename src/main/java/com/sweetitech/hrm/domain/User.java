package com.sweetitech.hrm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    public static final String SPLIT_CHAR = "::";

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn = new Date();

    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 100, message = "Username character must be between 2 to 100!")
    private String username;

    @Size(min = 6)
    @NotNull
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String salt;

    private String mrCode;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Role role;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private BasicInfo basicInfo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ContactInfo contactInfo;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private EmergencyContact emergencyContact;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfessionalInfo professionalInfo;

    @OneToOne(cascade = CascadeType.ALL)
    private Grade grade;

    @OneToOne
    private OfficeCode officeCode;

    @JsonIgnore
    private boolean isDeleted = false;

    // Required for OAuth2
    @JsonIgnore
    private boolean userEnabled = true;
    @JsonIgnore
    private boolean accountNonExpired = true;
    @JsonIgnore
    private boolean accountNoLocked = true;
    @JsonIgnore
    private boolean credentialNonExpired = true;

    public User() {
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUsername() {
        return username;
    }

    public boolean isUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(boolean userEnabled) {
        this.userEnabled = userEnabled;
    }

    public boolean isAccountNoLocked() {
        return accountNoLocked;
    }

    public boolean isCredentialNonExpired() {
        return credentialNonExpired;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNoLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNonExpired;
    }


    @Override
    public boolean isEnabled() {
        return userEnabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
        System.out.println("Authority for " + getUsername() + " -> " + authority.toString());
        authorities.add(authority);
        return authorities;
    }

    @JsonIgnore
    public String getPassword() {
        if (salt == null)
            return password;
        else
            return salt + SPLIT_CHAR + password;

    }

    public void setPassword(String password) {
        this.password = password;
        this.salt = null;
    }

    public String getMrCode() {
        return mrCode;
    }

    public void setMrCode(String mrCode) {
        this.mrCode = mrCode;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public ProfessionalInfo getProfessionalInfo() {
        return professionalInfo;
    }

    public void setProfessionalInfo(ProfessionalInfo professionalInfo) {
        this.professionalInfo = professionalInfo;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public OfficeCode getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(OfficeCode officeCode) {
        this.officeCode = officeCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "createdOn=" + createdOn +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", mrCode='" + mrCode + '\'' +
                ", company=" + company +
                ", role=" + role +
                ", basicInfo=" + basicInfo +
                ", contactInfo=" + contactInfo +
                ", emergencyContact=" + emergencyContact +
                ", professionalInfo=" + professionalInfo +
                ", grade=" + grade +
                ", officeCode=" + officeCode +
                ", isDeleted=" + isDeleted +
                ", userEnabled=" + userEnabled +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNoLocked=" + accountNoLocked +
                ", credentialNonExpired=" + credentialNonExpired +
                '}';
    }
}