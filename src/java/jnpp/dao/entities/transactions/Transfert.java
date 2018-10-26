package jnpp.dao.entities.transactions;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = Movement.Type.Values.TRANSFERT)
public class Transfert extends MoneyTrade implements Serializable {

    private static final long serialVersionUID = 1L;

    public Transfert() {}

    @Override
    public Type getType() {
        return Movement.Type.TRANSFERT;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.TRANSFERT[ id=" + getId() + " ]";
    }
    
}
