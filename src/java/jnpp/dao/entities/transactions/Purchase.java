package jnpp.dao.entities.transactions;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = AccountTransaction.Type.Values.PURCHASE)
public class Purchase extends ShareTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    public Purchase() {}
    
    public Type getType() {
        return AccountTransaction.Type.PURCHASE;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.Purchase[ id=" + getId() + " ]";
    }
    
}
