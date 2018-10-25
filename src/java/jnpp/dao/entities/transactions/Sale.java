package jnpp.dao.entities.transactions;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = AccountTransaction.Type.Values.SALE)
public class Sale extends ShareTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    public Sale() {}
    
    @Override
    public Type getType() {
        return AccountTransaction.Type.SALE;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.Sale[ id=" + getId() + " ]";
    }
    
}
