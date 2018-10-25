package jnpp.dao.entities.accounts;

import java.io.Serializable;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jnpp.dao.entities.clients.Client;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Account implements Serializable {

    public static enum Type {
    
        CURRENT,
        JOINT,
        SAVING,
        SHARE;
        
        public static class Values {

            public static final String CURRENT = "CURRENT";
            public static final String JOINT = "JOINT";
            public static final String SAVING = "SAVING";
            public static final String SHARE = "SHARE";

            private Values() {}

        }
    
    }
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private String rib;

    @ManyToOne
    @JoinColumn(name="client_fk")
    private Client client;
    
    public abstract Type getType();    
    
    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rib != null ? rib.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        return !((this.rib == null && other.rib != null) || (this.rib != null && !this.rib.equals(other.rib)));
    }
    
}
