package jnpp.dao.entities.clients;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@DiscriminatorValue(value = ClientEntity.Type.Values.PRIVATE)
@NamedQueries({
    @NamedQuery(
        name = "find_private_by_identity",
        query = "SELECT p FROM PrivateEntity p " 
                + "WHERE p.identity.gender = :gender "
                + "  AND p.identity.firstname = :firstname "
                + "  AND p.identity.lastname = :lastname")})
public class PrivateEntity extends ClientEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Embedded
    private Identity identity;
    
    @Temporal(TemporalType.DATE)
    private Date birthday;
    
    public PrivateEntity(String login, String password, Gender gender, String firstname, String lastname,
            Date birthday, String email, Integer number, String street, 
            String city, String state, String phone, Boolean notify) {
        super(login, password, email, number, street, city, state, phone, notify);
        identity = new Identity(gender, firstname, lastname);
        this.birthday = birthday;
    }
    
    public PrivateEntity() {}

    @Override
    public Type getType() {
        return ClientEntity.Type.PRIVATE;
    }
    
    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }
   
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.PrivateEntity[ id=" + getLogin() + " ]";
    }
    
}
