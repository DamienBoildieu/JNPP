
package jnpp.service.dto.movements;

import java.util.Date;
import jnpp.dao.entities.movements.DebitEntity;
import jnpp.dao.entities.movements.MovementEntity;
import jnpp.dao.entities.movements.PaymentEntity;
import jnpp.dao.entities.movements.PurchaseEntity;
import jnpp.dao.entities.movements.SaleEntity;
import jnpp.dao.entities.movements.TradeEntity;
import jnpp.dao.entities.movements.TransfertEntity;
import jnpp.dao.entities.movements.WithdrawEntity;

public abstract class MovementDTO {
    
    public static enum Type {
    
        TRANSFERT,
        DEBIT,
        PURCHASE,
        SALE,
        WITHDRAW,
        PAYMENT;
        
    }
    
    private final Date date;
    private final String ribFrom;
    
    public MovementDTO(MovementEntity movement) {
        date = movement.getDate();
        ribFrom = movement.getRibFrom();
    }
    
    public abstract Type getType();

    public Date getDate() {
        return date;
    }

    public String getRibFrom() {
        return ribFrom;
    }
    
    public static MovementDTO newMovementDTO(MovementEntity movement) {
        switch(movement.getType()) {
            case TRANSFERT:
                return new TransfertDTO((TransfertEntity) movement);
            case DEBIT:
                return new DebitDTO((DebitEntity) movement);
            case PURCHASE:
                return new PurchaseDTO((PurchaseEntity) movement);
            case SALE:
                return new SaleDTO((SaleEntity) movement);
            case WITHDRAW:
                return new WithdrawDTO((WithdrawEntity) movement);
            case PAYMENT:
                return new PaymentDTO((PaymentEntity) movement);
        }
        return null;
    }
    
}
