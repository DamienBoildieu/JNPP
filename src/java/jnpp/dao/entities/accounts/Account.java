package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import jnpp.dao.entities.clients.Client;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Account implements Serializable {

    public static class Type {
        
        public static final String CURRENT = "CURRENT";
        public static final String SAVING = "SAVING";
        public static final String SHARE = "SHARE";
        
        private Type() {}
        
    }
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private String rib;

    @ManyToMany(mappedBy = "accounts")
    private List<Client> clients = new ArrayList<Client>();
    
    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
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
