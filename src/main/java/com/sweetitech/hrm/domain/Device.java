package com.sweetitech.hrm.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @GeneratedValue
    private Long Id;

    private String deviceName;

    private String deviceId;

    private String IP;

    private String port;

    private String mac;

    private Date updatedAt;

    private boolean status;

    private String officeCode;

    public Device() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    @Override
    public String toString() {
        return "Device{" +
                "Id=" + Id +
                ", deviceName='" + deviceName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", IP='" + IP + '\'' +
                ", port='" + port + '\'' +
                ", mac='" + mac + '\'' +
                ", updatedAt=" + updatedAt +
                ", status=" + status +
                ", officeCode='" + officeCode + '\'' +
                '}';
    }
}
