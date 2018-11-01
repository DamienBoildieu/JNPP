package jnpp.dao.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import jnpp.dao.entities.clients.ClientEntity;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "find_all_advisor",
        query = "SELECT a FROM AdvisorEntity a")})
public class AdvisorEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Embedded
    private Identity identity;
    
    private String email;
    private String phone;
    @Embedded
    private Address officeAdress;
    
    @OneToMany(mappedBy = "advisor")
    private List<ClientEntity> clients = new ArrayList<ClientEntity>();
    
    public AdvisorEntity() {}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public List<ClientEntity> getClients() {
        return clients;
    }
    
    public void setClients(List<ClientEntity> clients) {
        this.clients = clients;
    }
    
    public Identity getIdentity() {
        return identity;
    }
    
    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getOfficeAdress() {
        return officeAdress;
    }

    public void setOfficeAdress(Address officeAdress) {
        this.officeAdress = officeAdress;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AdvisorEntity)) return false;
        AdvisorEntity other = (AdvisorEntity) object;
        return !((this.id == null && other.id != null) 
                || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.AdvisorEntity[ id=" + id + " ]";
    }
    
}
