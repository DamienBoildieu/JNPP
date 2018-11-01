
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
    
    private Date date;
    private String ribFrom;

    public MovementDTO(Date date, String ribFrom) {
        this.date = date;
        this.ribFrom = ribFrom;
    }
    
    public abstract Type getType();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRibFrom() {
        return ribFrom;
    }

    public void setRibFrom(String ribFrom) {
        this.ribFrom = ribFrom;
    }
}
