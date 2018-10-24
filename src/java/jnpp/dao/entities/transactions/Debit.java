package jnpp.dao.entities.transactions;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = AccountTransaction.Type.BEBIT)
public class Debit extends MoneyTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.Debit[ id=" + getId() + " ]";
    }
    
}
