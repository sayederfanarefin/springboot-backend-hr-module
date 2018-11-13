package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Payroll;
import com.sweetitech.hrm.domain.PayrollBreakdown;
import com.sweetitech.hrm.domain.SalaryBreakdown;
import com.sweetitech.hrm.domain.dto.PayrollDTO;
import com.sweetitech.hrm.domain.dto.UserPayrollDTO;
import com.sweetitech.hrm.domain.dto.UserPayrollListDTO;

import java.util.Date;
import java.util.List;

public interface PayrollService {

    Payroll read(Long userId, Integer month, Integer year) throws Exception;

    Payroll read(Long id) throws Exception;

    UserPayrollDTO readPayrollForUser(Long userId, Integer month, Integer year) throws Exception;

    List<Payroll> readAllByUserAndYear(Integer year, Long userId) throws Exception;

    List<UserPayrollListDTO> readAllUserPayrollByMonth(Long companyId,
                                                       Long departmentId,
                                                       Long officeCodeId,
                                                       Integer month,
                                                       Integer year) throws Exception;
    Payroll create(Payroll payroll);

    List<PayrollBreakdown> createPayrollBreakdowns(Payroll payroll, List<SalaryBreakdown> breakdowns) throws Exception;

    Payroll update(Long payrollId, Payroll payroll) throws Exception;

    UserPayrollDTO approve(PayrollDTO dto, List<PayrollBreakdown> breakdowns) throws Exception;

    UserPayrollDTO changeStatusToPaid(Long payrollId, Date paidOn) throws Exception;

    List<PayrollBreakdown> readAllBreakdownsByPayroll(Long payrollId) throws Exception;

    void removeBreakdowns(Long payrollId) throws Exception;

    List<SalaryBreakdown> convert(List<PayrollBreakdown> breakdowns) throws Exception;
}
