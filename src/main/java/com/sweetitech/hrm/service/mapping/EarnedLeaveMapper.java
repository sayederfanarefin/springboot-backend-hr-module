package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.common.exception.EntityNotFoundException;
import com.sweetitech.hrm.common.util.DateValidator;
import com.sweetitech.hrm.domain.AllocatedLeaves;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.EarnedLeaveDTO;
import com.sweetitech.hrm.domain.dto.LeaveSummaryDTO;
import com.sweetitech.hrm.domain.dto.LeaveUserSummaryDTO;
import com.sweetitech.hrm.domain.dto.UserSmallResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EarnedLeaveMapper {

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    /**
     *
     * @param leaves
     * @return dto
     */
    public EarnedLeaveDTO map(User user, List<LeaveSummaryDTO> leaves, AllocatedLeaves allocatedLeaves, Integer year) throws Exception {
        EarnedLeaveDTO dto = new EarnedLeaveDTO();

        if (!DateValidator.isValidYear(year)) {
            throw new EntityNotFoundException("Invalid year foundd!");
        }

        UserSmallResponseDTO userSmallResponseDTO = userSmallResponseMapper.map(user);
        dto.setUser(userSmallResponseDTO);

        dto.setYear(year);

        int consumed = 0;
        if (leaves != null && leaves.size() > 0) {
            for (LeaveSummaryDTO leave: leaves) {
                if (leave.getLeaveType().equals(Constants.LeaveTypes.EARN_LEAVE)) {
                    consumed++;
                }
            }
        }
        dto.setConsumed(consumed);

        if (allocatedLeaves != null)
            dto.setAllocated(allocatedLeaves.getEarnLeave());
        else dto.setAllocated(0);

        return dto;

    }

    /**
     *
     * @param summary
     * @return dto
     */
    public EarnedLeaveDTO map(LeaveUserSummaryDTO summary) throws Exception {
        EarnedLeaveDTO dto = new EarnedLeaveDTO();

        dto.setUser(summary.getUser());

        if (summary.getAllocated() != null)
            dto.setAllocated(summary.getAllocated().getEarnLeave());
        else dto.setAllocated(0);

        dto.setYear(summary.getYear());
        dto.setConsumed(summary.getEarnLeave());

        return dto;
    }

}
