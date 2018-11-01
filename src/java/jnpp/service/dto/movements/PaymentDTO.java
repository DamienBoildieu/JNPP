package jnpp.service.dto.movements;

import java.util.Date;
import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.movements.PaymentEntity;

public class PaymentDTO extends MovementDTO {
    
    private Double money;
    private Currency currency;
    private String target;

    public PaymentDTO(Date date, String ribFrom, Double money, Currency currency, String target) {
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
