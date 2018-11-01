package jnpp.service.dto.movements;

import jnpp.dao.entities.movements.TransfertEntity;

public class TransfertDTO extends MoneyTradeDTO {

    public TransfertDTO(TransfertEntity transfert) {
        super(transfert);
    }
    
    @Override
    public Type getType() {
        return MovementDTO.Type.TRANSFERT;
    }   
    
}
