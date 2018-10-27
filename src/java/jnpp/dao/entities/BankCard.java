package jnpp.dao.entities;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;

import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = PaymentMean.Type.Values.BANKCARD)
public class BankCard extends PaymentMean implements Serializable {

    private static final long serialVersionUID = 1L;

    public BankCard() {}
    
    @Override
    public Type getType() {
        return PaymentMean.Type.BANKCARD;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.BankCard[ id=" + getId() + " ]";
    }
    
}
