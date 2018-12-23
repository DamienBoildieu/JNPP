package jnpp.dao.entities.clients;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import jnpp.dao.entities.IdentityEntity;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.advisor.AdvisorEntity;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.dto.clients.LoginDTO;
import jnpp.service.dto.clients.ProfessionalDTO;

@Entity
@DiscriminatorValue(value = ClientEntity.Type.Values.PROFESSIONAL)
@NamedQueries({
        @NamedQuery(name = "find_professional_by_name_identity", query = "SELECT p FROM ProfessionalEntity p "
                + "WHERE p.name = :name " + "  AND p.owner.gender = :gender "
                + "  AND p.owner.firstname = :firstname "
                + "  AND p.owner.lastname = :lastname") })
public class ProfessionalEntity extends ClientEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    @Embedded
    private IdentityEntity owner;

    public ProfessionalEntity(String login, String password, String name,
            IdentityEntity.Gender ownerGender, String ownerFirstname,
            String ownerLastname, String email, Integer number, String street,
            String city, String state, String phone, Boolean notify,
            AdvisorEntity advisor) {
        super(login, password, email, number, street, city, state, phone,
                notify, advisor);
        this.name = name;
        owner = new IdentityEntity(ownerGender, ownerFirstname, ownerLastname);
    }

    public ProfessionalEntity() {
    }

    @Override
    public Type getType() {
        return ClientEntity.Type.PROFESIONAL;
    }

    @Override
    public boolean canOpen(AccountEntity.Type type) {
        switch (type) {
        case CURRENT:
        case SHARE:
            return true;
        case JOINT:
        case SAVING:
            return false;
        default:
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IdentityEntity getOwner() {
        return owner;
    }

    public void setOwner(IdentityEntity owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.ProfessionalEntity[ id=" + getLogin() + " ]";
    }

    @Override
    public ProfessionalDTO toDTO() {
        return new ProfessionalDTO(getLogin(), name, owner.toDTO(), getEmail(),
                getAddress().toDTO(), getPhone());
    }

    @Override
    public LoginDTO toLoginDTO() {
        return new LoginDTO(getLogin(), getPassword(),
                ClientDTO.Type.PROFESIONAL, name, getEmail());
    }

}
