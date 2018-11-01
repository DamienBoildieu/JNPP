package jnpp.dao.entities.movements;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = MovementEntity.Type.Values.DEBIT)
public class DebitEntity extends MoneyTradeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public DebitEntity() {}
    
    @Override
    public Type getType() {
        return MovementEntity.Type.DEBIT;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.DebitEntity[ id=" + getId() + " ]";
    }
    
}
