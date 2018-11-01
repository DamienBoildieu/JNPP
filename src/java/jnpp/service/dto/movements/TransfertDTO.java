package jnpp.service.dto.movements;

import jnpp.dao.entities.movements.TransfertEntity;

public class TransfertDTO extends MoneyTradeDTO {

    public TransfertDTO(TransfertEntity movement) {
        super(movement);
    }
    
    @Override
    public Type getType() {
        return MovementDTO.Type.TRANSFERT;
    }   
    
}
