package jnpp.service.dto.movements;

import java.util.Date;

import jnpp.service.dto.accounts.CurrencyDTO;

public abstract class MoneyTradeDTO extends TradeDTO {

    private Double money;
    private CurrencyDTO currency;

    public MoneyTradeDTO(Date date, String ribFrom, String ribTo, Double money,
            CurrencyDTO currency, String label) {
        super(date, ribFrom, ribTo, label);
        this.money = money;
        this.currency = currency;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }

}
