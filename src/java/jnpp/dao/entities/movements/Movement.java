package jnpp.dao.entities.movements;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Movement implements Serializable {

    public static enum Type {
    
        TRANSFERT,
        BEBIT,
        PURCHASE,
        SALE,
        WITHDRAW;
        
        public static class Values {

            public static final String TRANSFERT = "TRANSFERT";
            public static final String BEBIT = "BEBIT";
            public static final String PURCHASE = "PURCHASE";
            public static final String SALE = "SALE";
            public static final String WITHDRAW = "WITHDRAW";
            
            private Values() {}

        }
    
    }
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    private String ribFrom;
    
    public abstract Type getType();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRibFrom() {
        return ribFrom;
    }

    public void setRibFrom(String ribFrom) {
        this.ribFrom = ribFrom;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Movement)) {
            return false;
        }
        Movement other = (Movement) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
    
}
