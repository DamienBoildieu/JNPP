package jnpp.service.dto.movements;

import java.util.Date;
import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.movements.DebitEntity;

public class DebitDTO extends MoneyTradeDTO {

    public DebitDTO(Date date, String ribFrom, String ribTo, Double money, Currency currency) {
        super(date, ribFrom, ribTo, money, currency);
    }
        
    @Override
    public Type getType() {
        return MovementDTO.Type.DEBIT;
    }  
    
}
