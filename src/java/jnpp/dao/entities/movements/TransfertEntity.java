package jnpp.dao.entities.movements;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = MovementEntity.Type.Values.TRANSFERT)
public class TransfertEntity extends MoneyTradeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public TransfertEntity() {}

    @Override
    public Type getType() {
        return MovementEntity.Type.TRANSFERT;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.TransfertEntity[ id=" + getId() + " ]";
    }
    
}
