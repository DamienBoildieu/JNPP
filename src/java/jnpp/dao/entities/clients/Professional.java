package jnpp.dao.entities.clients;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue(value = Client.Type.Values.PROFESSIONAL)
@NamedQuery(name = "find_professional_by_name",
        query = "SELECT COUNT(p) FROM Professional p WHERE p.name = :name")
public class Professional extends Client implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
   
    @Embedded
    private Identity owner;
    
    public Professional(String name, Gender ownerGender, 
            String ownerFirstname, String ownerLastname, String email, 
            Integer number, String street, String city, String state, 
            String phone) {
        super(email, number, street, city, state, phone, true);
        this.name = name;
        owner = new Identity(ownerGender, ownerFirstname, ownerLastname);
    }
    
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
        return "jnpp.dao.entities.EProfessional[ id=" + getId() + " ]";
    }
    
}
