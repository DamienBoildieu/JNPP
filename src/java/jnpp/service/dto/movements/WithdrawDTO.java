package jnpp.service.dto.movements;

import java.util.Date;
import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.movements.WithdrawEntity;

public class WithdrawDTO extends MovementDTO {
    
    private Double money;
    private Currency currency;

    public WithdrawDTO(Date date, String ribFrom, Double money, Currency currency) {
        super(date, ribFrom);
        this.money = money;
        this.currency = currency;
    }
       
    @Override
    public Type getType() {
        return MovementDTO.Type.WITHDRAW;
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
