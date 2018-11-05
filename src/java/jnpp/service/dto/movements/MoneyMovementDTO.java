package jnpp.service.dto.movements;

import java.util.Date;
import jnpp.service.dto.accounts.CurrencyDTO;

public abstract class MoneyMovementDTO extends MovementDTO {
    
    private Double money;
    private CurrencyDTO currency;

    public MoneyMovementDTO(Date date, String ribFrom, Double money, CurrencyDTO currency) {
        super(date, ribFrom);
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
