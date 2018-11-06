package jnpp.dao.entities.accounts;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import jnpp.dao.entities.clients.PrivateEntity;

@Entity
@NamedQueries({
    @NamedQuery(
            name = "find_closerequest_by_rib",
            query = "SELECT c FROM CloseRequestEntity c WHERE c.account.rib = :rib")})
public class CloseRequestEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_fk")
    private PrivateEntity client;
    @ManyToOne
    @JoinColumn(name = "account_fk")
    private JointAccountEntity account;

    public CloseRequestEntity() {
    }

    public CloseRequestEntity(PrivateEntity client, JointAccountEntity account) {
        this.client = client;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PrivateEntity getClient() {
        return client;
    }

    public void setClient(PrivateEntity client) {
        this.client = client;
    }

    public JointAccountEntity getAccount() {
        return account;
    }

    public void setAccount(JointAccountEntity account) {
        this.account = account;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CloseRequestEntity)) {
            return false;
        }
        CloseRequestEntity other = (CloseRequestEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.accounts.ClotureRequest[ id=" + id + " ]";
    }

}
