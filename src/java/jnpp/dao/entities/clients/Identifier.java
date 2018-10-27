package jnpp.dao.entities.clients;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Identifier implements Serializable {

    private static final long serialVersionUID = 1L;
        
    @Id
    private String login;
    @Column(nullable = false)
    private String password;
    
    @OneToOne
    @JoinColumn(name = "client_fk")
    private Client client;
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Identifier)) return false;
        Identifier other = (Identifier) object;
        return !((this.login == null && other.login != null) 
                || (this.login != null && !this.login.equals(other.login)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.clients.Identifier[ id=" + login + " ]";
    }
    
}
