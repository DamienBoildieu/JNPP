package jnpp.dao.entities.clients;

import jnpp.dao.entities.Identity;
import jnpp.dao.entities.Gender;
import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import jnpp.dao.entities.advisor.AdvisorEntity;

@Entity
@DiscriminatorValue(value = ClientEntity.Type.Values.PROFESSIONAL)
@NamedQueries({
    @NamedQuery(
        name = "find_professional_by_name_identity",
        query = "SELECT p FROM ProfessionalEntity p "
                + "WHERE p.name = :name "
                + "  AND p.owner.gender = :gender "
                + "  AND p.owner.firstname = :firstname "
                + "  AND p.owner.lastname = :lastname")})
public class ProfessionalEntity extends ClientEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
   
    @Embedded
    private Identity owner;
    
    public ProfessionalEntity(String login, String password, String name, Gender ownerGender, 
            String ownerFirstname, String ownerLastname, String email, 
            Integer number, String street, String city, String state, 
            String phone, Boolean notify, AdvisorEntity advisor) {
        super(login, password, email, number, street, city, state, phone, notify, advisor);
        this.name = name;
        owner = new Identity(ownerGender, ownerFirstname, ownerLastname);
    }
    
    public ProfessionalEntity() {}
    
    @Override
    public Type getType() {
        return ClientEntity.Type.PROFESIONAL;
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
        return "jnpp.dao.entities.ProfessionalEntity[ id=" + getLogin() + " ]";
    }
    
}
