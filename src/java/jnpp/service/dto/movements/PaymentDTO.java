package jnpp.service.dto.movements;

import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.movements.PaymentEntity;

public class PaymentDTO extends MovementDTO {
    
    private final Double money;
    private final Currency currency;
    
    public PaymentDTO(PaymentEntity movement) {
        super(movement);
        money = movement.getMoney();
        currency = movement.getCurrency();
    }
    
    @Override
    public Type getType() {
        return MovementDTO.Type.PAYMENT;
    }
    
    public Double getMoney() {
        return money;
    }

    public Currency getCurrency() {
        return currency;
    }
    
}
