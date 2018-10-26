package jnpp.dao.entities.transactions;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = Movement.Type.Values.PURCHASE)
public class Purchase extends ShareTrade implements Serializable {

    private static final long serialVersionUID = 1L;

    public Purchase() {}
    
    @Override
    public Type getType() {
        return Movement.Type.PURCHASE;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.Purchase[ id=" + getId() + " ]";
    }
    
}
