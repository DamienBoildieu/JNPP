package jnpp.service.dto.movements;

import jnpp.dao.entities.movements.PurchaseEntity;

public class PurchaseDTO extends ShareTradeDTO {
    
    public PurchaseDTO(PurchaseEntity movement) {
        super(movement);
    }
    
    @Override
    public MovementDTO.Type getType() {
        return MovementDTO.Type.PURCHASE;
    }  
    
}
