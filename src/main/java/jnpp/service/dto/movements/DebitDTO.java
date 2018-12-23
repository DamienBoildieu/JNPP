package jnpp.service.dto.movements;

import java.util.Date;

import jnpp.service.dto.accounts.CurrencyDTO;

public class DebitDTO extends MoneyTradeDTO {

    public DebitDTO(Date date, String ribFrom, String ribTo, Double money,
            CurrencyDTO currency, String label) {
        super(date, ribFrom, ribTo, money, currency, label);
    }

    @Override
    public Type getType() {
        return MovementDTO.Type.DEBIT;
    }

}
