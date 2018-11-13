package com.sweetitech.hrm.service;

import com.sweetitech.hrm.domain.Bonus;
import com.sweetitech.hrm.domain.dto.BonusListDTO;
import com.sweetitech.hrm.domain.dto.BonusResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface BonusService {

    BonusResponseDTO create(Bonus bonus) throws Exception;

    BonusResponseDTO update(Long id, Bonus bonus) throws Exception;

    BonusResponseDTO read(Long id) throws Exception;

    BonusResponseDTO updatePaymentDate(Long id, Date paymentDate) throws Exception;

    void remove(Long id) throws Exception;

    Page<Bonus> readAllByPaidToAndYear(Long paidToUserId, Integer year, Integer page) throws Exception;

    Page<Bonus> readAllByApprovedByMonthAndYear(Long approvedById, Integer month, Integer year, Integer page) throws Exception;

    Page<Bonus> readAllByCompanyMonthAndYear(Long companyId, Integer month, Integer year, Integer page) throws Exception;

    Page<Bonus> readAllByCompanyAndYear(Long companyId, Integer year, Integer page) throws Exception;

    List<BonusResponseDTO> readAllByCompanyDepartmentOfficeCodeTypeAndYear(Long companyId,
                                                                           Long departmentId,
                                                                           Long officeCodeId,
                                                                           Long typeId,
                                                                           Integer year) throws Exception;

    List<BonusResponseDTO> createOrUpdateBonusesByYear(BonusListDTO dto) throws Exception;

    List<BonusResponseDTO> generateReportForYearByCompanyDepartmentOfficeCodeTypeAndCategory(Long companyId,
                                                                                             Long departmentId,
                                                                                             Long officeCodeId,
                                                                                             Long typeId,
                                                                                             Integer year) throws Exception;

}
