package jnpp.dao.entities.clients;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = Client.Type.Values.PROFESSIONAL)
public class Professional extends Client implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
   
    @Embedded
    private Identity owner;
    
    public Professional() {}
    
    @Override
    public Type getType() {
        return Client.Type.PROFESIONAL;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Identity getOwner() {
        return owner;
    }
    
    public void setOwner(Identity owner) {
        this.owner = owner;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.EProfessional[ id=" + getLogin() + " ]";
    }
    
}
