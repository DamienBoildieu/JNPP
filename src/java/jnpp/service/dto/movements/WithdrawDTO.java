package jnpp.service.dto.movements;

import java.util.Date;
import jnpp.service.dto.accounts.CurrencyDTO;

public class WithdrawDTO extends MoneyMovementDTO {

    public WithdrawDTO(Date date, String ribFrom, Double money, CurrencyDTO currency) {
        super(date, ribFrom, money, currency);
    }

    @Override
    public Type getType() {
        return MovementDTO.Type.WITHDRAW;
    }

}
