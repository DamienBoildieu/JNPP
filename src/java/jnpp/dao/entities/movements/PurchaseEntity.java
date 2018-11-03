package jnpp.dao.entities.movements;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import jnpp.service.dto.movements.PurchaseDTO;

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
    
    @Override
    public PurchaseDTO toDTO() {
        return new PurchaseDTO(getDate(), getRibFrom(), getRibTo(), getAmount(), getShare().toDTO());
    }
    
}
