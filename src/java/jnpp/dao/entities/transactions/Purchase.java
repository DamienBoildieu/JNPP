package jnpp.dao.entities.transactions;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = AccountTransaction.Type.PURCHASE)
public class Purchase extends ShareTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.Purchase[ id=" + getId() + " ]";
    }
    
}
