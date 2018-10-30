package jnpp.dao.entities.clients;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue(value = Client.Type.Values.PROFESSIONAL)
@NamedQueries({
    @NamedQuery(
        name = "find_by_name",
        query = "SELECT COUNT(p) FROM Professional p WHERE p.name = :name"),
    @NamedQuery(
        name = "is_professional_fake",
        query = "SELECT COUNT(p) FROM Professional p "
                + "WHERE p.id = :id "
                + "  AND p.name = :name "
                + "  AND p.owner.gender = :gender "
                + "  AND p.owner.firstname = :firstname "
                + "  AND p.owner.lastname = :lastname")})
public class Professional extends Client implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
   
    @Embedded
    private Identity owner;
    
    public Professional(String name, Gender ownerGender, 
            String ownerFirstname, String ownerLastname, String email, 
            Integer number, String street, String city, String state, 
            String phone, Boolean notify) {
        super(email, number, street, city, state, phone, notify);
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
