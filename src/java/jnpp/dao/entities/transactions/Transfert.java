package jnpp.dao.entities.transactions;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(value = AccountTransaction.Type.TRANSFERT)
public class Transfert extends MoneyTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.TRANSFERT[ id=" + getId() + " ]";
    }
    
}
