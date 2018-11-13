package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.ConstantsUtil;
import com.sweetitech.hrm.domain.Tender;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.TaskResponseDTO;
import com.sweetitech.hrm.domain.dto.TenderResponseDTO;
import com.sweetitech.hrm.repository.TenderRepository;
import com.sweetitech.hrm.service.TenderService;
import com.sweetitech.hrm.service.mapping.TaskMapper;
import com.sweetitech.hrm.service.mapping.TaskResponseMapper;
import com.sweetitech.hrm.service.mapping.TenderResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TenderServiceImpl implements TenderService {

    private TenderRepository tenderRepository;

    @Autowired
    private TenderResponseMapper tenderResponseMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskResponseMapper taskResponseMapper;

    @Autowired
    public TenderServiceImpl(TenderRepository tenderRepository) {
        this.tenderRepository = tenderRepository;
    }

    @Override
    public TenderResponseDTO create(Tender tender) throws Exception {
        return tenderResponseMapper.map(tenderRepository.save(tender));
    }

    @Override
    public Tender read(Long id) throws Exception {
        return tenderRepository.getFirstById(id);
    }

    @Override
    public TenderResponseDTO readDTO(Long id) throws Exception {
        if (this.read(id) == null) {
            throw new EntityNotFoundException("No tenders with id: " + id);
        }

        return tenderResponseMapper.map(this.read(id));
    }

    @Override
    public TenderResponseDTO update(Long id, Tender tender) throws Exception {
        if (this.read(id) == null) {
            throw new EntityNotFoundException("No tenders with id: " + id);
        }

        tender.setId(id);
        return tenderResponseMapper.map(tenderRepository.save(tender));
    }

    @Override
    public TenderResponseDTO updateStatus(Long id, String status) throws Exception {
        Tender tender = this.read(id);
        if (tender == null) {
            throw new EntityNotFoundException("No tenders with id: " + id);
        }

        if (!ConstantsUtil.isValidTenderStatus(status)) {
            throw new EntityNotFoundException("Invalid status provided!");
        }

        tender.setStatus(status);
        return tenderResponseMapper.map(tenderRepository.save(tender));
    }

    @Override
    public TenderResponseDTO updateTenderSecurityStatus(Long id, String status) throws Exception {
        Tender tender = this.read(id);
        if (tender == null) {
            throw new EntityNotFoundException("No tenders with id: " + id);
        }

        if (!ConstantsUtil.isValidAmountOfTenderSecurityStatus(status)) {
            throw new EntityNotFoundException("Invalid status provided!");
        }

        tender.setTenderSecurityStatus(status);
        return tenderResponseMapper.map(tenderRepository.save(tender));
    }

    @Override
    public TenderResponseDTO updateTenderStatus(Long id, String status) throws Exception {
        Tender tender = this.read(id);
        if (tender == null) {
            throw new EntityNotFoundException("No tenders with id: " + id);
        }

        if (!ConstantsUtil.isValidEntityTenderStatus(status)) {
            throw new EntityNotFoundException("Invalid status provided!");
        }

        tender.setTenderStatus(status);
        return tenderResponseMapper.map(tenderRepository.save(tender));
    }

    @Override
    public void remove(Long id) throws Exception {
        if (this.read(id) == null) {
            throw new EntityNotFoundException("No tenders with id: " + id);
        }

        tenderRepository.delete(this.read(id));
    }

    @Override
    public List<TenderResponseDTO> readAll() throws Exception {
        List<Tender> tenders = tenderRepository.findAllByOrderByLastUpdatedAsc();

        List<TenderResponseDTO> dtos = new ArrayList<>();
        if (tenders != null && tenders.size() > 0) {
            for (Tender tender: tenders) {
                dtos.add(tenderResponseMapper.map(tender));
            }
        }

        return dtos;
    }

    @Override
    public List<TenderResponseDTO> readAllByUser(Long userId) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        List<Tender> tenders = tenderRepository.findAllByCreatedByIdOrderByLastUpdatedDesc(userId);

        List<TenderResponseDTO> dtos = new ArrayList<>();
        if (tenders != null && tenders.size() > 0) {
            for (Tender tender: tenders) {
                dtos.add(tenderResponseMapper.map(tender));
            }
        }

        return dtos;
    }

    @Override
    public List<TenderResponseDTO> search(String institutionName, String eTenderId, String mrCode) throws Exception {
        List<Tender> tenders = new ArrayList<>();

        if (!institutionName.isEmpty() && !eTenderId.isEmpty() && !mrCode.isEmpty()) {
            tenders = tenderRepository.findAllByInstitutionNameContainingAndETenderIdContainingAndMrCodeContainingOrderByLastUpdatedAsc(institutionName, eTenderId, mrCode);
        }
        if (!institutionName.isEmpty() && !eTenderId.isEmpty() && mrCode.isEmpty()) {
            tenders = tenderRepository.findAllByInstitutionNameContainingAndETenderIdContainingOrderByLastUpdatedAsc(institutionName, eTenderId);
        }
        if (!institutionName.isEmpty() && eTenderId.isEmpty() && !mrCode.isEmpty()) {
            tenders = tenderRepository.findAllByInstitutionNameContainingAndMrCodeContainingOrderByLastUpdatedAsc(institutionName, mrCode);
        }
        if (institutionName.isEmpty() && !eTenderId.isEmpty() && !mrCode.isEmpty()) {
            tenders = tenderRepository.findAllByETenderIdContainingAndMrCodeContainingOrderByLastUpdatedAsc(eTenderId, mrCode);
        }
        if (!institutionName.isEmpty() && eTenderId.isEmpty() && mrCode.isEmpty()) {
            tenders = tenderRepository.findAllByInstitutionNameContainingOrderByLastUpdatedAsc(institutionName);
        }
        if (institutionName.isEmpty() && !eTenderId.isEmpty() && mrCode.isEmpty()) {
            tenders = tenderRepository.findAllByETenderIdContainingOrderByLastUpdatedAsc(eTenderId);
        }
        if (institutionName.isEmpty() && eTenderId.isEmpty() && !mrCode.isEmpty()) {
            tenders = tenderRepository.findAllByMrCodeContainingOrderByLastUpdatedAsc(mrCode);
        }
        if (institutionName.isEmpty() && eTenderId.isEmpty() && mrCode.isEmpty()) {
            return this.readAll();
        }

        List<TenderResponseDTO> dtos = new ArrayList<>();
        if (tenders != null && tenders.size() > 0) {
            for (Tender tender: tenders) {
                dtos.add(tenderResponseMapper.map(tender));
            }
        }

        return dtos;
    }

    @Override
    public List<TenderResponseDTO> readAllNearingExpiry(Date date) throws Exception {
        List<TenderResponseDTO> tenders = this.readAll();

        List<TenderResponseDTO> resultSet = new ArrayList<>();
        Calendar source = Calendar.getInstance();
        Calendar current = Calendar.getInstance();
        current.setTime(date);
        if (tenders != null && tenders.size() > 0) {
            for (TenderResponseDTO tender: tenders) {
                if (tender.getTenderSecurityReleaseDate() != null) {
                    source.setTime(tender.getTenderSecurityReleaseDate());
                    source.add(Calendar.DATE, -7);

                    if (source.before(current)) {
                        resultSet.add(tender);
                    }
                }
                else if (tender.getPerformanceSecurityReleaseDate() != null) {
                    source.setTime(tender.getPerformanceSecurityReleaseDate());
                    source.add(Calendar.DATE, -7);

                    if (source.before(current)) {
                        resultSet.add(tender);
                    }
                }
            }
        }

        return resultSet;
    }

    @Override
    public TaskResponseDTO createTaskForTender(Long tenderId,
                                               Long assignedToUserId,
                                               Long assignedByUserId) throws  Exception {
        Tender tender = tenderRepository.getFirstById(tenderId);
        if (tender == null) {
            throw new EntityNotFoundException("No tenders with id: " + tenderId);
        }

        User assignedToUser = userService.read(assignedToUserId);
        if (assignedToUser == null) {
            throw new EntityNotFoundException("No users with id: " + assignedToUserId);
        }
        List<Long> users = new ArrayList<>();
        users.add(assignedToUserId);

        User assignedByUser = userService.read(assignedByUserId);
        if (assignedByUser == null) {
            throw new EntityNotFoundException("No users with id: " + assignedByUserId);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);
        Date date = new Date();
        date = sdf.parse(sdf.format(date));

        return taskResponseMapper.map(taskService.create(taskMapper.map(tender, date, assignedToUserId), users));
    }
}
