package jnpp.service.dto.accounts;

import jnpp.service.dto.AbstractDTO;

public class SavingBookDTO extends AbstractDTO {

    private String name;
    private Double moneyRate;
    private Double timeRate;

    public SavingBookDTO(String name, Double moneyRate, Double timeRate) {
        this.name = name;
        this.moneyRate = moneyRate;
        this.timeRate = timeRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMoneyRate() {
        return moneyRate;
    }

    public void setMoneyRate(Double moneyRate) {
        this.moneyRate = moneyRate;
    }

    public Double getTimeRate() {
        return timeRate;
    }

    public void setTimeRate(Double timeRate) {
        this.timeRate = timeRate;
    }

}
