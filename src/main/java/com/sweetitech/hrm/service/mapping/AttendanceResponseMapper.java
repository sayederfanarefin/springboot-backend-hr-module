package com.sweetitech.hrm.service.mapping;

import com.sweetitech.hrm.common.Constants;
import com.sweetitech.hrm.domain.DeviceLocation;
import com.sweetitech.hrm.domain.Log;
import com.sweetitech.hrm.domain.User;
import com.sweetitech.hrm.domain.dto.AttendanceResponseDTO;
import com.sweetitech.hrm.domain.dto.UserDeviceResponseDTO;
import com.sweetitech.hrm.domain.relation.UserDeviceRelation;
import com.sweetitech.hrm.service.implementation.DeviceServiceImpl;
import com.sweetitech.hrm.service.implementation.MobileLogsServiceImpl;
import com.sweetitech.hrm.service.implementation.UserDeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceResponseMapper {

    @Autowired
    private UserDeviceServiceImpl userDeviceService;

    @Autowired
    private UserDeviceResponseMapper userDeviceResponseMapper;

    @Autowired
    private UserSmallResponseMapper userSmallResponseMapper;

    @Autowired
    private MobileLogsServiceImpl mobileLogsService;

    @Autowired
    private DeviceServiceImpl deviceService;

    /**
     *
     * @param entity
     * @return dto
     */
    public AttendanceResponseDTO map(Log entity) throws Exception {
        UserDeviceRelation mapping = userDeviceService.getByMachineAndEnrolNumber(String.valueOf(entity.getMachineNumber()),
                String.valueOf(entity.getIndRegId()));

        AttendanceResponseDTO dto = new AttendanceResponseDTO();

        if (mapping == null && (entity.getMachineNumber() == Constants.MOBILE_IDENTIFIER)) {
            UserDeviceResponseDTO userDeviceResponseDTO = new UserDeviceResponseDTO();

            User user = mobileLogsService.read(entity.getIndRegId()).getUser();
            userDeviceResponseDTO.setUserSmallResponseDTO(userSmallResponseMapper.map(user));

            DeviceLocation location = new DeviceLocation();
            location.setCompany(user.getCompany());
            location.setDevice(deviceService.readAll().get(0));
            location.setLocation(mobileLogsService.read(entity.getIndRegId()).getName());

            userDeviceResponseDTO.setLocation(location);

            dto.setUserDeviceDTO(userDeviceResponseDTO);
        }
        else dto.setUserDeviceDTO(userDeviceResponseMapper.map(mapping));

        dto.setDateTimeRecord(entity.getDateTimeRecord());

        //TODO count late

        //TODO count early

        return dto;
    }

}
