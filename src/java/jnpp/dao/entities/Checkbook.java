package jnpp.dao.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = PaymentObject.Type.Values.CHECKBOOK)
public class Checkbook extends PaymentObject implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Checkbook() {}            
    
    @Override
    public Type getType() {
        return PaymentObject.Type.CHECKBOOK;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.Checkbook[ id=" + getId() + " ]";
    }
    
}
