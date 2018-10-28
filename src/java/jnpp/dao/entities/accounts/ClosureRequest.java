package jnpp.dao.entities.accounts;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jnpp.dao.entities.clients.Private;

@Entity
public class ClosureRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="client_fk")
    private Private client;
    @ManyToOne
    @JoinColumn(name="account_fk")
    private JointAccount account;
    
    public ClosureRequest() {}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Private getClient() {
        return client;
    }

    public void setClient(Private client) {
        this.client = client;
    }

    public JointAccount getAccount() {
        return account;
    }

    public void setAccount(JointAccount account) {
        this.account = account;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ClosureRequest)) {
            return false;
        }
        ClosureRequest other = (ClosureRequest) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.accounts.ClotureRequest[ id=" + id + " ]";
    }
    
}
