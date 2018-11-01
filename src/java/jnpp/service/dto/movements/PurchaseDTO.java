package jnpp.service.dto.movements;

import java.util.Date;
import jnpp.dao.entities.movements.PurchaseEntity;
import jnpp.service.dto.accounts.ShareDTO;

public class PurchaseDTO extends ShareTradeDTO {

    public PurchaseDTO(Date date, String ribFrom, String ribTo, Integer amount, ShareDTO share) {
        super(date, ribFrom, ribTo, amount, share);
    }
    
    @Override
    public MovementDTO.Type getType() {
        return MovementDTO.Type.PURCHASE;
    }  
    
}
