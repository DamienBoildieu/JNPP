package jnpp.dao.entities.movements;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class TradeEntity extends MovementEntity
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ribTo;

    public TradeEntity() {
    }

    public TradeEntity(Date date, String ribFrom, String ribTo, String label) {
        super(date, ribFrom, label);
        this.ribTo = ribTo;
    }

    public String getRibTo() {
        return ribTo;
    }

    public void setRibTo(String ribTo) {
        this.ribTo = ribTo;
    }

}
