package com.sweetitech.hrm.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BonusListDTO {

    private List<BonusDTO> bonuses;

    private Integer number;

    public List<BonusDTO> getBonuses() {
        return bonuses;
    }

    public void setBonuses(List<BonusDTO> bonuses) {
        this.bonuses = bonuses;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "BonusListDTO{" +
                "bonuses=" + bonuses +
                ", number=" + number +
                '}';
    }
}
