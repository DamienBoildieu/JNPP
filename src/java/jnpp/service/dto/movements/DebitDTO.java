package jnpp.service.dto.movements;

import jnpp.dao.entities.movements.DebitEntity;

public class DebitDTO extends MoneyTradeDTO {
    
    
    public DebitDTO(DebitEntity movement) {
        super(movement);
    }
    
    @Override
    public Type getType() {
        return MovementDTO.Type.DEBIT;
    }  
    
}
