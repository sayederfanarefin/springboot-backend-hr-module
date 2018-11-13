package com.sweetitech.hrm.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mobile_logs")
public class MobileLogs {

    @Id
    @GeneratedValue
    private Long logId;

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    private String id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String placeId;

    @Column(columnDefinition = "TEXT")
    private String reference;

    private String vicinity;

    private boolean manualEntry;

    @NotNull
    private Long timestamp;

    @NotNull
    @OneToOne
    private User user;

    public MobileLogs() {
    }

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

    public String getEntityId() {
        return id;
    }

    public void setEntityId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
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

    public boolean isManualEntry() {
        return manualEntry;
    }

    public void setManualEntry(boolean manualEntry) {
        this.manualEntry = manualEntry;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "MobileLogs{" +
                "logId=" + logId +
                ", lat=" + lat +
                ", lng=" + lng +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", placeId='" + placeId + '\'' +
                ", reference='" + reference + '\'' +
                ", vicinity='" + vicinity + '\'' +
                ", manualEntry=" + manualEntry +
                ", timestamp=" + timestamp +
                ", user=" + user +
                '}';
    }
}
