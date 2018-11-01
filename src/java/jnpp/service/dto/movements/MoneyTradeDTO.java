package jnpp.service.dto.movements;

import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.movements.MoneyTradeEntity;

public abstract class MoneyTradeDTO extends TradeDTO {
    
    private final Double money;
    private final Currency currency;

    public MoneyTradeDTO(MoneyTradeEntity movement) {
        super(movement);
        money = movement.getMoney();
        currency = movement.getCurrency();
    }
    
    public Double getMoney() {
        return money;
    }

    public Currency getCurrency() {
        return currency;
    }
    
}
