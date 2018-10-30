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
@DiscriminatorValue(value = Client.Type.Values.PRIVATE)
@NamedQueries({
    @NamedQuery(
        name = "find_by_identity",
        query = "SELECT COUNT(p) FROM Private p " 
                + "WHERE p.identity.gender = :gender "
                + "  AND p.identity.firstname = :firstname "
                + "  AND p.identity.lastname = :lastname"),
    @NamedQuery(
        name = "is_private_fake",
        query = "SELECT COUNT(p) FROM Private p "
                + "WHERE p.id = :id "
                + "  AND p.identity.gender = :gender "
                + "  AND p.identity.firstname = :firstname "
                + "  AND p.identity.lastname = :lastname")})
public class Private extends Client implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Embedded
    private Identity identity;
    
    @Temporal(TemporalType.DATE)
    private Date birthday;
    
    public Private(Gender gender, String firstname, String lastname,
            Date birthday, String email, Integer number, String street, 
            String city, String state, String phone, Boolean notify) {
        super(email, number, street, city, state, phone, notify);
        identity = new Identity(gender, firstname, lastname);
        this.birthday = birthday;
    }
    
    public Private() {}

    @Override
    public Type getType() {
        return Client.Type.PRIVATE;
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
        return "jnpp.dao.entities.EPrivate[ id=" + getId() + " ]";
    }
    
}
