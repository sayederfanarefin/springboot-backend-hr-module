package com.sweetitech.hrm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "userInfo")
public class UserInfo extends BaseEntity {

    private int machineNumber;

    private String enrollNumber;

    private String name;

    private int fingerIndex;

    @Column(columnDefinition = "TEXT")
    private String tmpData;

    private int privilege;

    private String password;

    private boolean enabled;

    private String iFlag;

    private String officeCode;

    public UserInfo() {
    }

    public int getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(int machineNumber) {
        this.machineNumber = machineNumber;
    }

    public String getEnrollNumber() {
        return enrollNumber;
    }

    public void setEnrollNumber(String enrollNumber) {
        this.enrollNumber = enrollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFingerIndex() {
        return fingerIndex;
    }

    public void setFingerIndex(int fingerIndex) {
        this.fingerIndex = fingerIndex;
    }

    public String getTmpData() {
        return tmpData;
    }

    public void setTmpData(String tmpData) {
        this.tmpData = tmpData;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getiFlag() {
        return iFlag;
    }

    public void setiFlag(String iFlag) {
        this.iFlag = iFlag;
    }

    public String getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "machineNumber=" + machineNumber +
                ", enrollNumber='" + enrollNumber + '\'' +
                ", name='" + name + '\'' +
                ", fingerIndex=" + fingerIndex +
                ", tmpData='" + tmpData + '\'' +
                ", privilege=" + privilege +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", iFlag='" + iFlag + '\'' +
                ", officeCode='" + officeCode + '\'' +
                '}';
    }
}
