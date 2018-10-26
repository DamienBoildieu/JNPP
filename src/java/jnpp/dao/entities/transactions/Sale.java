package jnpp.dao.entities.transactions;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = Movement.Type.Values.SALE)
public class Sale extends ShareTrade implements Serializable {

    private static final long serialVersionUID = 1L;

    public Sale() {}
    
    @Override
    public Type getType() {
        return Movement.Type.SALE;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.Sale[ id=" + getId() + " ]";
    }
    
}
