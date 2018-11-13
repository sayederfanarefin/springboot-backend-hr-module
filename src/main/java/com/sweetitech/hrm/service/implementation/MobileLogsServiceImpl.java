package com.sweetitech.hrm.service.implementation;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.PageAttr;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateTimeUtils;
import com.sweetitech.hrm.domain.Allowance;
import com.sweetitech.hrm.domain.MobileLogs;
import com.sweetitech.hrm.domain.dto.AllowanceDTO;
import com.sweetitech.hrm.domain.dto.MobileLogsDTO;
import com.sweetitech.hrm.domain.dto.MobileLogsResponseDTO;
import com.sweetitech.hrm.domain.dto.page.MobileLogsPage;
import com.sweetitech.hrm.repository.MobileLogsRepository;
import com.sweetitech.hrm.service.MobileLogsService;
import com.sweetitech.hrm.service.mapping.AllowanceCreateMapper;
import com.sweetitech.hrm.service.mapping.AllowanceEditMapper;
import com.sweetitech.hrm.service.mapping.MobileLogsMapper;
import com.sweetitech.hrm.service.mapping.MobileLogsResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MobileLogsServiceImpl implements MobileLogsService {

    private MobileLogsRepository mobileLogsRepository;

    @Autowired
    private MobileLogsMapper mobileLogsMapper;

    @Autowired
    private MobileLogsResponseMapper mobileLogsResponseMapper;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AllowanceServiceImpl allowanceService;

    @Autowired
    private AllowanceCreateMapper allowanceCreateMapper;

    @Autowired
    private AllowanceEditMapper allowanceEditMapper;

    @Autowired
    public MobileLogsServiceImpl(MobileLogsRepository mobileLogsRepository) {
        this.mobileLogsRepository = mobileLogsRepository;
    }

    MobileLogsPage preparePage(Page<MobileLogs> logs) throws Exception {
        List<MobileLogsResponseDTO> dtos = new ArrayList<>();

        if (logs.getContent() != null && logs.getContent().size() > 0) {
            for (MobileLogs mobileLogs: logs) {
                dtos.add(mobileLogsResponseMapper.map(mobileLogs));
            }
        }

        return new MobileLogsPage(dtos, logs);
    }

    private void followAllowance(MobileLogs mobileLogs) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mobileLogs.getTimestamp());

        Allowance allowance = allowanceService.checkIfExistsForUserAndDate(mobileLogs.getUser().getId(),
                calendar.get(Calendar.DAY_OF_MONTH), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.YEAR));

        System.out.println(mobileLogs.getUser().getId() + " " +
                calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.YEAR));
        System.out.println(allowance);

        AllowanceDTO dto = new AllowanceDTO();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.Dates.DATE_FORMAT);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.Dates.ACCEPTED_FORMAT);

        Date temp = calendar.getTime();
        Date date = sdf.parse(sdf1.format(temp));

        System.out.println(date);

        dto.setCreatedOn(date);
        dto.setRequestedBy(mobileLogs.getUser().getId());
        dto.setTransportWithTicket("");

        if (allowance == null) {
            dto.setDestination(mobileLogs.getName());

            allowanceService.create(allowanceCreateMapper.map(dto));
        } else {
            dto.setAllowanceId(allowance.getId());

            String[] destination = allowance.getDestination().split(",");
            if (destination != null && destination.length > 0) {
                if (destination[destination.length - 1].equals(mobileLogs.getName())) {
                    dto.setDestination(allowance.getDestination());
                } else {
                    dto.setDestination(allowance.getDestination() + ", " + mobileLogs.getName());
                }
            }

            allowanceService.updateDestination(allowance.getId(), dto.getDestination());
        }
    }

    @Override
    public MobileLogsResponseDTO create(MobileLogs mobileLogs) throws Exception {
        MobileLogs mobileLog = mobileLogsRepository.save(mobileLogs);

        followAllowance(mobileLog);

        return mobileLogsResponseMapper.map(mobileLog);
    }

    @Override
    public MobileLogsResponseDTO readDTO(Long id) throws Exception {
        MobileLogs mobileLogs = this.read(id);
        if (mobileLogs == null) {
            throw new EntityNotFoundException("No logs with id: " + id);
        }

        return mobileLogsResponseMapper.map(mobileLogs);
    }

    @Override
    public MobileLogs read(Long id) throws Exception {
        return mobileLogsRepository.getFirstByLogId(id);
    }

    @Override
    public MobileLogsPage readAllByUser(Long userId, Integer page) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No users with id: " + userId);
        }

        return preparePage(mobileLogsRepository.findAllByUserIdOrderByTimestampDesc(userId,
                new PageRequest(page, PageAttr.PAGE_SIZE)));
    }

    @Override
    public List<MobileLogsResponseDTO> bulkAdd(List<MobileLogsDTO> mobileLogs, Long userId) throws Exception {
        List<MobileLogsResponseDTO> result = new ArrayList<>();
        if (mobileLogs != null && mobileLogs.size() > 0) {
            for (MobileLogsDTO mobileLog: mobileLogs) {
                MobileLogs temp = mobileLogsMapper.map(mobileLog, userId);
                temp.setName("Tracking");
                result.add(this.create(temp));
            }
        }

        return result;
    }

    @Override
    public List<MobileLogsResponseDTO> readAllByUserAndDate(Long userId, Date date) throws Exception {
        if (userService.read(userId) == null) {
            throw new EntityNotFoundException("No user with id: " + userId);
        }
        if (!DateTimeUtils.isValidDate(date.toString(), "E MMM dd HH:mm:ss z yyyy")) {
            throw new EntityNotFoundException("Invalid date found!");
        }

        List<MobileLogs> mobileLogs = mobileLogsRepository.findAllByUserIdAndTimestampBetweenOrderByTimestampAsc(userId,
                DateTimeUtils.getStartOfDayTimestamp(date), DateTimeUtils.getEndOfDayTimestamp(date));

        List<MobileLogsResponseDTO> result = new ArrayList<>();
        if (mobileLogs != null && mobileLogs.size() > 0) {
            for (MobileLogs mobileLog: mobileLogs) {
                result.add(mobileLogsResponseMapper.map(mobileLog));
            }
        }

        return result;
    }
}
