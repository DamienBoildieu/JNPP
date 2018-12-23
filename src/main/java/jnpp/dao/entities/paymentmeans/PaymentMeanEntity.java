package jnpp.dao.entities.paymentmeans;

import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
    @NamedQuery(
            name = "find_all_paymentmeans",
            query = "SELECT p FROM PaymentMeanEntity p")
    ,
    @NamedQuery(
            name = "find_all_paymentmean_ids",
            query = "SELECT p.id FROM PaymentMeanEntity p"
    )})
public abstract class PaymentMeanEntity implements Serializable {

    public static enum Type {

        BANKCARD,
        CHECKBOOK;

        public static class Values {

            public static final String BANKCARD = "BANKCARD";
            public static final String CHECKBOOK = "CHECKBOOK";

            private Values() {
            }

        }

    }

    public static enum Status {

        ORDERED,
        ARRIVED,
        DELIVERED;

        public PaymentMeanDTO.Status toDTO() {
            switch (this) {
                case ORDERED:
                    return PaymentMeanDTO.Status.ORDERED;
                case ARRIVED:
                    return PaymentMeanDTO.Status.ARRIVED;
                case DELIVERED:
                    return PaymentMeanDTO.Status.DELIVERED;
            }
            return null;
        }

        public Status next() {
            Status[] status = values();
            return status[(ordinal() + 1) % status.length];
        }

    }

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "client_fk")
    private ClientEntity client;
    @ManyToOne
    @JoinColumn(name = "account_fk")
    private AccountEntity account;

    @Enumerated(EnumType.STRING)
    private Status status;

    public PaymentMeanEntity() {
    }

    public PaymentMeanEntity(String id, ClientEntity client, AccountEntity account, Status status) {
        this.id = id;
        this.client = client;
        this.account = account;
        this.status = status;
    }

    public abstract Type getType();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PaymentMeanEntity)) {
            return false;
        }
        PaymentMeanEntity other = (PaymentMeanEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.PaymentMeanEntity[ id=" + id + " ]";
    }

    public abstract PaymentMeanDTO toDTO();

}
