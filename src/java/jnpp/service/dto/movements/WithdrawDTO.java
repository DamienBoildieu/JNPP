package jnpp.service.dto.movements;

import java.util.Date;
import jnpp.service.dto.accounts.CurrencyDTO;

public class WithdrawDTO extends MovementDTO {
    
    private Double money;
    private CurrencyDTO currency;

    public WithdrawDTO(Date date, String ribFrom, Double money, CurrencyDTO currency) {
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

    public CurrencyDTO getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDTO currency) {
        this.currency = currency;
    }
    
}
