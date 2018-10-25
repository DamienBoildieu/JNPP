package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import jnpp.dao.entities.clients.Client;

@Entity
@DiscriminatorValue(value = Account.Type.Values.JOINT)
public class JointAccount extends CurrentAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JoinTable(
            name = "JointAccount_Client",
            joinColumns = @JoinColumn(name = "rib_jointaccount"),
            inverseJoinColumns = @JoinColumn(name = "login_client")
    )
    @ManyToMany
    private List<Client> clients = new ArrayList<Client>();
    
    public JointAccount() {}

    @Override
    public Type getType() {
        return Account.Type.JOINT;
    }
    
    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.accounts.JointAccount[ id=" + getRib() + " ]";
    }
    
}
