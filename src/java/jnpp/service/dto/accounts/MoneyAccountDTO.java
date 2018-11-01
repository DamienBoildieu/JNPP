package jnpp.service.dto.accounts;

import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.accounts.MoneyAccountEntity;

public abstract class MoneyAccountDTO extends AccountDTO {
    
    private Double money;
    private Currency currency;

    public MoneyAccountDTO(String rib, Double money, Currency currency) {
        super(rib);
        this.money = money;
        this.currency = currency;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
}
