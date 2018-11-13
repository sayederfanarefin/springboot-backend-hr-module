package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Commission;
import com.sweetitech.hrm.domain.dto.CommissionListDTO;
import com.sweetitech.hrm.domain.dto.CommissionResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface CommissionService {

    CommissionResponseDTO create(Commission commission) throws Exception;

    CommissionResponseDTO update(Long id, Commission commission) throws Exception;

    CommissionResponseDTO read(Long id) throws Exception;

    CommissionResponseDTO updatePaymentDate(Long id, Date paymentDate) throws Exception;

    void remove(Long id) throws Exception;

    Page<Commission> readAllByPaidToAndYear(Long paidToUserId, Integer year, Integer page) throws Exception;

    Page<Commission> readAllByApprovedByMonthAndYear(Long approvedById, Integer month, Integer year, Integer page) throws Exception;

    Page<Commission> readAllByCompanyMonthAndYear(Long companyId, Integer month, Integer year, Integer page) throws Exception;

    Page<Commission> readAllByCompanyAndYear(Long companyId, Integer year, Integer page) throws Exception;

    List<CommissionResponseDTO> readAllByCompanyDepartmentOfficeCodeTypeAndYear(Long companyId,
                                                                                String department,
                                                                                Long officeCodeId,
                                                                                Long typeId,
                                                                                Integer year) throws Exception;

    List<CommissionResponseDTO> createOrUpdateCommissionsByYear(CommissionListDTO dto) throws Exception;

    List<CommissionResponseDTO> generateReportForYearByCompanyDepartmentOfficeCodeTypeAndCategory(Long companyId,
                                                                                                  String department,
                                                                                                  Long officeCodeId,
                                                                                                  Long typeId,
                                                                                                  Integer year,
                                                                                                  String category) throws Exception;

}
