package jnpp.service.dto.movements;

import java.util.Date;
import jnpp.service.dto.accounts.CurrencyDTO;

public class DepositDTO extends MoneyMovementDTO {

    public DepositDTO(Date date, String ribFrom, Double money, CurrencyDTO currency) {
        super(date, ribFrom, money, currency);
    }

    @Override
    public Type getType() {
        return MovementDTO.Type.DEPOSIT;
    }

}
