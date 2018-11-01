package jnpp.service.dto.movements;

import jnpp.dao.entities.movements.PurchaseEntity;

public class PurchaseDTO extends ShareTradeDTO {
    
    public PurchaseDTO(PurchaseEntity purchase) {
        super(purchase);
    }
    
    @Override
    public MovementDTO.Type getType() {
        return MovementDTO.Type.PURCHASE;
    }  
    
}
