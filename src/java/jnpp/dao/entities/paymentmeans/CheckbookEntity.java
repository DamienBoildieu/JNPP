package jnpp.dao.entities.paymentmeans;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = PaymentMeanEntity.Type.Values.CHECKBOOK)
public class CheckbookEntity extends PaymentMeanEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public CheckbookEntity() {}            
    
    @Override
    public Type getType() {
        return PaymentMeanEntity.Type.CHECKBOOK;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.Checkbook[ id=" + getId() + " ]";
    }
    
}
