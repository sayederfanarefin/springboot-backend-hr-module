package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.domain.Salary;
import com.sweetitech.hrm.repository.SalaryRepository;
import com.sweetitech.hrm.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalaryServiceImpl implements SalaryService {

    private SalaryRepository salaryRepository;

    @Autowired
    public SalaryServiceImpl(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @Override
    public Salary create(Salary salary) {
        return salaryRepository.save(salary);
    }

    @Override
    public Salary update(Long id, Salary salary) throws Exception {
        Salary salaryOld = salaryRepository.getFirstById(id);
        if (salaryOld == null) {
            throw new EntityNotFoundException("No salary with id: " + id);
        }

        salary.setId(id);
        return salaryRepository.save(salary);
    }

    @Override
    public Salary read(Long id) throws Exception {
        Salary salary = salaryRepository.getFirstById(id);
        if (salary == null) {
            throw new EntityNotFoundException("No salary with id: " + id);
        }

        return salary;
    }
}
