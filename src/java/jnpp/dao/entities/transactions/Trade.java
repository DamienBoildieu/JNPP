package jnpp.dao.entities.transactions;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Trade extends Movement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String ribTo;

    public String getRibTo() {
        return ribTo;
    }

    public void setRibTo(String ribTo) {
        this.ribTo = ribTo;
    }
    
}
