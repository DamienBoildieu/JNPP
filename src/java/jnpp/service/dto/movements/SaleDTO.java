package jnpp.service.dto.movements;

import jnpp.dao.entities.movements.SaleEntity;

public class SaleDTO extends ShareTradeDTO {
    
    public SaleDTO(SaleEntity sale) {
        super(sale);
    }
    
    @Override
    public MovementDTO.Type getType() {
        return MovementDTO.Type.SALE;
    } 
        
}
