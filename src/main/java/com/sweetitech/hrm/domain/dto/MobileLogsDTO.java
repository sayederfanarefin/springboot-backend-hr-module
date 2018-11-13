package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MobileLogsDTO {

    private Long logId;

    private Double lat;

    private Double lng;

    private String id;

    private String name;

    private String place_id;

    private String reference;

    private String vicinity;

    private boolean manual_entry;

    private Long timestamp;

    private Long userId;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public boolean isManual_entry() {
        return manual_entry;
    }

    public void setManual_entry(boolean manual_entry) {
        this.manual_entry = manual_entry;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MobileLogsDTO{" +
                "logId=" + logId +
                ", lat=" + lat +
                ", lng=" + lng +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", place_id='" + place_id + '\'' +
                ", reference='" + reference + '\'' +
                ", vicinity='" + vicinity + '\'' +
                ", manual_entry=" + manual_entry +
                ", timestamp=" + timestamp +
                ", userId=" + userId +
                '}';
    }
}
