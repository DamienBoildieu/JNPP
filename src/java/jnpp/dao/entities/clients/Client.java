package jnpp.dao.entities.clients;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import jnpp.dao.entities.accounts.Account;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Client implements Serializable {

    public static class Type {
        
        public static final String PRIVATE = "PRIVATE";
        public static final String PROFESSIONAL = "PROFESSIONAL";
        
    }
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private String login;
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;
    private String address;
    private String phone;
    
    private Boolean notify;    
    
    @ManyToOne
    @JoinColumn(name="advisor_fk")
    private Advisor advisor;    
    
    @JoinTable(
        name = "Client_Account",
        joinColumns = @JoinColumn(name = "login_client"),
        inverseJoinColumns = @JoinColumn(name = "rib_account"))
    @ManyToMany
    private List<Account> accounts = new ArrayList<Account>();
    
    public Client() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getNotify() {
        return notify;
    }

    public void setNotify(Boolean notify) {
        this.notify = notify;
    }

    public Advisor getAdvisor() {
        return advisor;
    }
    
    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Client))
            return false;
        Client other = (Client) object;
        return !((this.login == null && other.login != null) 
                || (this.login != null && !this.login.equals(other.login)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.EClient[ id=" + login + " ]";
    }
    
}
