package jnpp.dao.entities.advisor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import jnpp.dao.entities.AddressEntity;
import jnpp.dao.entities.IdentityEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.dto.advisor.AdvisorDTO;

@Entity
@NamedQueries({
    @NamedQuery(
            name = "find_all_advisor",
            query = "SELECT a FROM AdvisorEntity a")
    ,
    @NamedQuery(
            name = "find_advisor_by_identity",
            query = "SELECT a FROM AdvisorEntity a "
            + "WHERE a.identity.firstname = :firstname "
            + "  AND a.identity.lastname = :lastname")
    ,
    @NamedQuery(
            name = "find_advisor_clients_by_id",
            query = "SELECT a_clients FROM AdvisorEntity a INNER JOIN a.clients a_clients WHERE a.id = :id")})
public class AdvisorEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Embedded
    private IdentityEntity identity;

    private String email;
    private String phone;
    @Embedded
    private AddressEntity officeAdress;

    @OneToMany(mappedBy = "advisor")
    private List<ClientEntity> clients = new ArrayList<ClientEntity>();

    public AdvisorEntity() {
    }

    public AdvisorEntity(IdentityEntity.Gender gender, String firstname,
            String lastname, String email, String phone, Integer number,
            String street, String city, String state) {
        identity = new IdentityEntity(gender, firstname, lastname);
        this.email = email;
        this.phone = phone;
        officeAdress = new AddressEntity(number, street, city, state);
    }

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

    public IdentityEntity getIdentity() {
        return identity;
    }

    public void setIdentity(IdentityEntity identity) {
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

    public AddressEntity getOfficeAdress() {
        return officeAdress;
    }

    public void setOfficeAdress(AddressEntity officeAdress) {
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
        if (!(object instanceof AdvisorEntity)) {
            return false;
        }
        AdvisorEntity other = (AdvisorEntity) object;
        return !((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.AdvisorEntity[ id=" + id + " ]";
    }

    public AdvisorDTO toDTO() {
        return new AdvisorDTO(identity.toDTO(), email, phone, officeAdress.toDTO());
    }

    public static List<AdvisorDTO> toDTO(List<AdvisorEntity> entities) {
        List<AdvisorDTO> dtos = new ArrayList<AdvisorDTO>(entities.size());
        Iterator<AdvisorEntity> it = entities.iterator();
        while (it.hasNext()) {
            dtos.add(it.next().toDTO());
        }
        return dtos;
    }

}
