package jnpp.service.dto.movements;

import java.util.Date;
import jnpp.service.dto.accounts.CurrencyDTO;

public class PaymentDTO extends MovementDTO {
    
    private Double money;
    private CurrencyDTO currency;
    private String target;

    public PaymentDTO(Date date, String ribFrom, Double money, CurrencyDTO currency, String target) {
        super(date, ribFrom);
        this.money = money;
        this.currency = currency;
        this.target = target;
    }
    
    @Override
    public Type getType() {
        return MovementDTO.Type.PAYMENT;
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

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
