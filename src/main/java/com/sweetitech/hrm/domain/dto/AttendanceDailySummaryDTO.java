package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttendanceDailySummaryDTO {

    List<UserSmallResponseDTO> absent;

    List<AttendanceDayTrackDTO> lateOrEarly;

    List<AttendanceDayTrackDTO> present;

    List<AttendanceDayTrackDTO> isLate;

    List<AttendanceDayTrackDTO> leftEarly;

    public List<UserSmallResponseDTO> getAbsent() {
        return absent;
    }

    public void setAbsent(List<UserSmallResponseDTO> absent) {
        this.absent = absent;
    }

    public List<AttendanceDayTrackDTO> getLateOrEarly() {
        return lateOrEarly;
    }

    public void setLateOrEarly(List<AttendanceDayTrackDTO> lateOrEarly) {
        this.lateOrEarly = lateOrEarly;
    }

    public List<AttendanceDayTrackDTO> getPresent() {
        return present;
    }

    public void setPresent(List<AttendanceDayTrackDTO> present) {
        this.present = present;
    }

    public List<AttendanceDayTrackDTO> getIsLate() {
        return isLate;
    }

    public void setIsLate(List<AttendanceDayTrackDTO> isLate) {
        this.isLate = isLate;
    }

    public List<AttendanceDayTrackDTO> getLeftEarly() {
        return leftEarly;
    }

    public void setLeftEarly(List<AttendanceDayTrackDTO> leftEarly) {
        this.leftEarly = leftEarly;
    }

    @Override
    public String toString() {
        return "AttendanceDailySummaryDTO{" +
                "absent=" + absent +
                ", lateOrEarly=" + lateOrEarly +
                ", present=" + present +
                ", isLate=" + isLate +
                ", leftEarly=" + leftEarly +
                '}';
    }
}
