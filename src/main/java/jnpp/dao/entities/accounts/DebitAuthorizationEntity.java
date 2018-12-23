package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import jnpp.service.dto.accounts.DebitAuthorizationDTO;

@Entity
@NamedQueries({
        @NamedQuery(name = "find_debit_authorization_by_rib_from_rib_to", query = "SELECT d "
                + "FROM DebitAuthorizationEntity d "
                + "WHERE d.from.rib = :rib_from " + "  AND d.ribTo = :rib_to"),
        @NamedQuery(name = "find_all_debit_authorization_by_login", query = "SELECT d "
                + "FROM DebitAuthorizationEntity d "
                + "INNER JOIN d.from.clients d_from_clients "
                + "WHERE d_from_clients.login = :login"),
        @NamedQuery(name = "find_all_debit_authorization_by_login_rib_from", query = "SELECT d "
                + "FROM DebitAuthorizationEntity d "
                + "INNER JOIN d.from.clients d_from_clients "
                + "WHERE d.from.rib = :rib "
                + "  AND d_from_clients.login = :login"),
        @NamedQuery(name = "can_debit", query = "SELECT COUNT(d) "
                + "FROM DebitAuthorizationEntity d "
                + "WHERE d.from.rib = :rib_to "
                + "  AND d.ribTo = :rib_from") })
public class DebitAuthorizationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_account_fk")
    private AccountEntity from;
    private String ribTo;

    public DebitAuthorizationEntity() {
    }

    public DebitAuthorizationEntity(AccountEntity from, String ribTo) {
        this.from = from;
        this.ribTo = ribTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountEntity getFrom() {
        return from;
    }

    public void setFrom(AccountEntity from) {
        this.from = from;
    }

    public String getTo() {
        return ribTo;
    }

    public void setTo(String ribTo) {
        this.ribTo = ribTo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DebitAuthorizationEntity)) {
            return false;
        }
        DebitAuthorizationEntity other = (DebitAuthorizationEntity) object;
        return !((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.accounts.DebitAuthorizationEntity[ id=" + id
                + " ]";
    }

    public DebitAuthorizationDTO toDTO() {
        return new DebitAuthorizationDTO(from.getRib(), ribTo);
    }

    public static List<DebitAuthorizationDTO> toDTO(
            List<DebitAuthorizationEntity> entities) {
        List<DebitAuthorizationDTO> dtos = new ArrayList<DebitAuthorizationDTO>(
                entities.size());
        Iterator<DebitAuthorizationEntity> it = entities.iterator();
        while (it.hasNext()) {
            dtos.add(it.next().toDTO());
        }
        return dtos;
    }

}
