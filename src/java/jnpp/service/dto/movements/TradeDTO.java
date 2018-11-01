package jnpp.service.dto.movements;

import jnpp.dao.entities.movements.TradeEntity;

public abstract class TradeDTO extends MovementDTO {
    
    private final String ribTo;

    public TradeDTO(TradeEntity movement) {
        super(movement);
        ribTo = movement.getRibTo();
    }
    
    public String getRibTo() {
        return ribTo;
    }
    
}
