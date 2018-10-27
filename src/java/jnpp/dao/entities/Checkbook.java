package jnpp.dao.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = PaymentMean.Type.Values.CHECKBOOK)
public class Checkbook extends PaymentMean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Checkbook() {}            
    
    @Override
    public Type getType() {
        return PaymentMean.Type.CHECKBOOK;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.Checkbook[ id=" + getId() + " ]";
    }
    
}
