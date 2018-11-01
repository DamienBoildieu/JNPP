package jnpp.service.dto.accounts;

import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.accounts.MoneyAccountEntity;

public abstract class MoneyAccountDTO extends AccountDTO {
    
    private final Double money;
    private final Currency currency;

    public MoneyAccountDTO(MoneyAccountEntity account) {
        super(account);
        money = account.getMoney();
        currency = account.getCurrency();
    }

    public Double getMoney() {
        return money;
    }

    public Currency getCurrency() {
        return currency;
    }
    
}
