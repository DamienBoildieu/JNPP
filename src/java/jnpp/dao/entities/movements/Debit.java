package jnpp.dao.entities.movements;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = Movement.Type.Values.BEBIT)
public class Debit extends MoneyTrade implements Serializable {

    private static final long serialVersionUID = 1L;

    public Debit() {}
    
    @Override
    public Type getType() {
        return Movement.Type.BEBIT;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.Debit[ id=" + getId() + " ]";
    }
    
}
