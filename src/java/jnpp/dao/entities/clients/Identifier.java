package jnpp.dao.entities.clients;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "find_client_by_login_password",
        query = "SELECT i.client FROM Identifier i "
                + "WHERE i.login = :login AND i.password = :password "),
    @NamedQuery(
        name = "find_all_login",
        query = "SELECT i.login FROM Identifier i"),
    @NamedQuery(
        name = "find_login_by_client_fk",
        query = "SELECT i.login FROM Identifier i "
                + "WHERE i.client.id = :client_id"),
    @NamedQuery(
        name = "find_by_client_fk",
        query = "SELECT i FROM Identifier i WHERE i.client.id = :client_id"),
    @NamedQuery(
        name = "find_by_login_identity_email",
        query = "SELECT i "
                + "FROM Identifier i "
                + "WHERE i.login = :login "
                + "  AND TREAT(i.client AS Private).identity.firstname = :firstname "
                + "  AND TREAT(i.client AS Private).identity.lastname = :lastname "
                + "  AND i.client.email = :email"),
    @NamedQuery(
        name = "find_by_login_name_owner_email",
        query = "SELECT i "
                + "FROM Identifier i "
                + "WHERE i.login = :login "
                + "  AND TREAT(i.client AS Professional).name = :name "
                + "  AND TREAT(i.client AS Professional).owner.firstname = :firstname "
                + "  AND TREAT(i.client AS Professional).owner.lastname = :lastname "
                + "  AND i.client.email = :email")})
public class Identifier implements Serializable {

    private static final long serialVersionUID = 1L;
        
    @Id
    private String login;
    @Column(nullable = false)
    private String password;
    
    @OneToOne
    @JoinColumn(name = "client_fk")
    private Client client;
    
    public Identifier(String login, String password) {
        this.login = login;
        this.password = password;
    }
    
    public Identifier() {}
    
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
