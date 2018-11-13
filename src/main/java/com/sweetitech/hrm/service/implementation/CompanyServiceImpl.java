package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Company;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.repository.CompanyRepository;
import com.sweetitech.hrm.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> readAllActive() throws Exception {
        return companyRepository.findAllByIsDeletedFalseOrderByNameAsc();
    }

    @Override
    public Company read(long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company create(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Page<Company> readAll(Integer page) {
        return companyRepository.findAllByOrderByNameAsc(new PageRequest(page, PageAttr.PAGE_SIZE));
    }

    @Override
    public List<Company> readAll() {
        return companyRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Page<Company> readAllActive(Long userId, Integer page) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (user.getRole().getName().equals(Constants.Roles.ROLE_ADMIN_2)) {
            return companyRepository.findAllByIdAndIsDeletedFalseOrderByNameAsc(user.getCompany().getId(),
                    new PageRequest(page, PageAttr.PAGE_SIZE));
        } else {
            return companyRepository.findAllByIsDeletedFalseOrderByNameAsc(new PageRequest(page, PageAttr.PAGE_SIZE));
        }
    }

    @Override
    public List<Company> readAllActive(Long userId) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (user.getRole().getName().equals(Constants.Roles.ROLE_ADMIN_2)) {
            return companyRepository.findAllByIdAndIsDeletedFalseOrderByNameAsc(user.getCompany().getId());
        } else {
            return companyRepository.findAllByIsDeletedFalseOrderByNameAsc();
        }
    }

    @Override
    public List<Company> readAllActiveByRole(Long userId) throws Exception {
        User user = userService.read(userId);
        if (user == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }

        if (user.getRole().getName().equals(Constants.Roles.ROLE_ADMIN_2)
                || user.getRole().getName().equals(Constants.Roles.ROLE_EMPLOYEE)) {
            return companyRepository.findAllByIdAndIsDeletedFalseOrderByNameAsc(user.getCompany().getId());
        } else {
            return companyRepository.findAllByIsDeletedFalseOrderByNameAsc();
        }
    }

    @Override
    public Company update(long id, Company company) throws EntityNotFoundException {
        Company companyOld = this.read(id);
        if (companyOld == null) {
            throw new EntityNotFoundException("No company with id: " + id);
        }

        company.setId(companyOld.getId());
        company.setDeleted(companyOld.isDeleted());
        return companyRepository.save(company);
    }

    @Override
    public void removeActiveState(long id) throws EntityNotFoundException {
        Company company = this.read(id);
        if (company == null) {
            throw new EntityNotFoundException("No company with id: " + id);
        }

        company.setDeleted(true);
        companyRepository.save(company);
    }
}
