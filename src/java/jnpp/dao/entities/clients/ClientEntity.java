package jnpp.dao.entities.clients;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class ClientEntity implements Serializable {

    public static enum Type {
    
        PRIVATE,
        PROFESIONAL;
        
        public static class Values {

            public static final String PRIVATE = "PRIVATE";
            public static final String PROFESSIONAL = "PROFESSIONAL";

            private Values() {}

        }
    
    }
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private String login;
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;
    @Embedded
    @Column(nullable = false)
    private Address address;
    @Column(nullable = false)
    private String phone;
    
    @Column(nullable = false)
    private Boolean notify;
    
    public ClientEntity(String login, String password, String email, Integer number, String street, String city, 
            String state, String phone, Boolean notify) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.address = new Address(number, street, city, state);
        this.phone = phone;
        this.notify = notify;
    }
    
    public ClientEntity() {}
    
    public abstract Type getType();
    
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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
    
    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ClientEntity))
            return false;
        ClientEntity other = (ClientEntity) object;
        return !((this.login == null && other.login != null) 
                || (this.login != null && !this.login.equals(other.login)));
    }
    
}
