package jnpp.dao.entities.paymentmeans;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;

import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = PaymentMeanEntity.Type.Values.BANKCARD)
public class BankCardEntity extends PaymentMeanEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public BankCardEntity() {}
    
    @Override
    public Type getType() {
        return PaymentMeanEntity.Type.BANKCARD;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.BankCard[ id=" + getId() + " ]";
    }
    
}
