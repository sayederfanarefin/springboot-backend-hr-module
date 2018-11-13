package com.sweetitech.hrm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

//     private String location = "/opt/tomcat/webapps/abc"; //for droplet
    private String location = ""; //for mac

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}