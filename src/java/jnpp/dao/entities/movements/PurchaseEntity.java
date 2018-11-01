package jnpp.dao.entities.movements;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = MovementEntity.Type.Values.PURCHASE)
public class PurchaseEntity extends ShareTradeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public PurchaseEntity() {}
    
    @Override
    public Type getType() {
        return MovementEntity.Type.PURCHASE;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.PurchaseEntity[ id=" + getId() + " ]";
    }
    
}
