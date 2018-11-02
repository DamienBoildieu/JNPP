package jnpp.dao.entities.movements;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import jnpp.service.dto.movements.SaleDTO;

@Entity
@DiscriminatorValue(value = MovementEntity.Type.Values.SALE)
public class SaleEntity extends ShareTradeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public SaleEntity() {}
    
    @Override
    public Type getType() {
        return MovementEntity.Type.SALE;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.SaleEntity[ id=" + getId() + " ]";
    }
    
    @Override
    public SaleDTO toDTO() {
        return new SaleDTO(getDate(), getAccount().getRib(), getRibTo(), getAmount(), getShare().toDTO());
    }
    
}
