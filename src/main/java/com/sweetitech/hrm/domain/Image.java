package com.sweetitech.hrm.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "images")
public class Image extends BaseEntity {

    @Column(columnDefinition="LONGTEXT")
    private String url;

    public Image() {
    }

    public Image(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "url='" + url + '\'' +
                '}';
    }
}
