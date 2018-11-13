package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sweetitech.hrm.service.mapping.UserSmallResponseMapper;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttendanceResponseDTO {

    private UserDeviceResponseDTO userDeviceDTO;

    private String dateTimeRecord;

    private Integer lateCount;

    private Integer earlyCount;

    public UserDeviceResponseDTO getUserDeviceDTO() {
        return userDeviceDTO;
    }

    public void setUserDeviceDTO(UserDeviceResponseDTO userDeviceDTO) {
        this.userDeviceDTO = userDeviceDTO;
    }

    public String getDateTimeRecord() {
        return dateTimeRecord;
    }

    public void setDateTimeRecord(String dateTimeRecord) {
        this.dateTimeRecord = dateTimeRecord;
    }

    public Integer getLateCount() {
        return lateCount;
    }

    public void setLateCount(Integer lateCount) {
        this.lateCount = lateCount;
    }

    public Integer getEarlyCount() {
        return earlyCount;
    }

    public void setEarlyCount(Integer earlyCount) {
        this.earlyCount = earlyCount;
    }

    @Override
    public String toString() {
        return "AttendanceResponseDTO{" +
                "userDeviceDTO=" + userDeviceDTO +
                ", dateTimeRecord='" + dateTimeRecord + '\'' +
                ", lateCount=" + lateCount +
                ", earlyCount=" + earlyCount +
                '}';
    }
}
