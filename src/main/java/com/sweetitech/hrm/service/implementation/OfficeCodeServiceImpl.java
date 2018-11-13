package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.OfficeCode;
import com.sweetitech.hrm.repository.OfficeCodeRepository;
import com.sweetitech.hrm.service.OfficeCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeCodeServiceImpl implements OfficeCodeService {

    private OfficeCodeRepository officeCodeRepository;

    @Autowired
    private CompanyServiceImpl companyService;

    @Autowired
    public OfficeCodeServiceImpl(OfficeCodeRepository officeCodeRepository) {
        this.officeCodeRepository = officeCodeRepository;
    }

    @Override
    public OfficeCode create(OfficeCode officeCode) throws Exception {
        OfficeCode officeCodeOld = officeCodeRepository.getFirstByCodeAndCompanyId(officeCode.getCode(), officeCode.getCompany().getId());
        if (officeCodeOld != null) {
            throw new EntityNotFoundException("A code already exists with: " + officeCode.getCode());
        }

        return officeCodeRepository.save(officeCode);
    }

    @Override
    public OfficeCode update(Long id, OfficeCode officeCode) throws Exception {
        OfficeCode officeCodeOld = officeCodeRepository.getFirstById(id);
        if (officeCodeOld == null) {
            throw new EntityNotFoundException("No office codes with id: " + id);
        }

        officeCode.setId(id);
        return officeCodeRepository.save(officeCode);
    }

    @Override
    public void remove(Long id) throws Exception {
        OfficeCode officeCode = officeCodeRepository.getFirstById(id);
        if (officeCode == null) {
            throw new EntityNotFoundException("No office codes with id: " + id);
        }

        officeCode.setDeleted(true);
        officeCodeRepository.save(officeCode);
    }

    @Override
    public OfficeCode read(Long id) throws Exception {
        return officeCodeRepository.getFirstById(id);
    }

    @Override
    public OfficeCode readById(Long id) throws Exception {
        OfficeCode officeCode = officeCodeRepository.getFirstById(id);
        if (officeCode == null) {
            throw new EntityNotFoundException("No office codes with id: " + id);
        }

        return officeCode;
    }

    @Override
    public OfficeCode readByCode(String code) throws Exception {
        OfficeCode officeCode = officeCodeRepository.readByCodeLike(code);
        if (officeCode == null) {
            throw new EntityNotFoundException("No office codes with code: " + code);
        }

        return officeCode;
    }

    @Override
    public boolean exists(Long companyId, String code) throws Exception {
        OfficeCode officeCode = officeCodeRepository.getFirstByCodeAndCompanyId(code, companyId);
        if (officeCode == null) {
            return false;
        }

        return true;
    }

    @Override
    public List<OfficeCode> readAll() {
        return officeCodeRepository.getAllByIsDeletedFalseOrderByCodeAsc();
    }

    @Override
    public List<OfficeCode> readByCompanyId(Long companyId) throws Exception {
        if (companyService.read(companyId) == null) {
            throw new EntityNotFoundException("No company with id: " + companyId);
        }

        return officeCodeRepository.findAllByCompanyIdAndIsDeletedFalseOrderByCodeAsc(companyId);
    }
}
