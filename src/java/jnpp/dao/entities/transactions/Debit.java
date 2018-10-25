package jnpp.dao.entities.transactions;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = AccountTransaction.Type.Values.BEBIT)
public class Debit extends MoneyTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    public Debit() {}
    
    @Override
    public Type getType() {
        return AccountTransaction.Type.BEBIT;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.Debit[ id=" + getId() + " ]";
    }
    
}
