package jnpp.service.dto.accounts;

import jnpp.dao.entities.accounts.SavingBookEntity;

public class SavingBookDTO {
    
    private final String name;
    private final Double moneyRate;
    private final Double timeRate;
    
    public SavingBookDTO(SavingBookEntity savingBook) {
        name = savingBook.getName();
        moneyRate = savingBook.getMoneyRate();
        timeRate = savingBook.getMoneyRate();
    }
    
    public String getName() {
        return name;
    }

    public Double getMoneyRate() {
        return moneyRate;
    }

    public Double getTimeRate() {
        return timeRate;
    }
    
}
