package jnpp.dao.entities.clients;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@DiscriminatorValue(value = Client.Type.Values.PRIVATE)
public class Private extends Client implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Embedded
    private Identity identity;
    
    @Temporal(TemporalType.DATE)
    private Date birthday;
    
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
        return "jnpp.dao.entities.EPrivate[ id=" + getLogin() + " ]";
    }
    
}
