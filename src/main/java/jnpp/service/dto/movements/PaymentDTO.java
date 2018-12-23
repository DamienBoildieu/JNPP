package jnpp.service.dto.movements;

import java.util.Date;

import jnpp.service.dto.accounts.CurrencyDTO;

public class PaymentDTO extends MoneyMovementDTO {

    private String target;

    public PaymentDTO(Date date, String ribFrom, Double money,
            CurrencyDTO currency, String target, String label) {
        super(date, ribFrom, money, currency, label);
        this.target = target;
    }

    @Override
    public Type getType() {
        return MovementDTO.Type.PAYMENT;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

}
