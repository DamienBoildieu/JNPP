package jnpp.service.dto.movements;

import java.util.Date;

import jnpp.service.dto.accounts.CurrencyDTO;

public class DepositDTO extends MoneyMovementDTO {

    public DepositDTO(Date date, String ribFrom, Double money,
            CurrencyDTO currency, String label) {
        super(date, ribFrom, money, currency, label);
    }

    @Override
    public Type getType() {
        return MovementDTO.Type.DEPOSIT;
    }

}
