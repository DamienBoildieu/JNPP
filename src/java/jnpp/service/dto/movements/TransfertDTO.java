package jnpp.service.dto.movements;

import java.util.Date;
import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.movements.TransfertEntity;

public class TransfertDTO extends MoneyTradeDTO {

    public TransfertDTO(Date date, String ribFrom, String ribTo, Double money, Currency currency) {
        super(date, ribFrom, ribTo, money, currency);
    }
    
    @Override
    public Type getType() {
        return MovementDTO.Type.TRANSFERT;
    }   
    
}
