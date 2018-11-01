package jnpp.service.dto.movements;

import java.util.Date;
import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.movements.MoneyTradeEntity;

public abstract class MoneyTradeDTO extends TradeDTO {
    
    private Double money;
    private Currency currency;

    public MoneyTradeDTO(Date date, String ribFrom, String ribTo, Double money, Currency currency) {
        super(date, ribFrom, ribTo);
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
