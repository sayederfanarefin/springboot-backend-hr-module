package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationCountDTO {

    private Long countId;

    private UserSmallResponseDTO user;

    private Integer count;

    public Long getCountId() {
        return countId;
    }

    public void setCountId(Long countId) {
        this.countId = countId;
    }

    public UserSmallResponseDTO getUser() {
        return user;
    }

    public void setUser(UserSmallResponseDTO user) {
        this.user = user;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "NotificationCountDTO{" +
                "countId=" + countId +
                ", user=" + user +
                ", count=" + count +
                '}';
    }
}
