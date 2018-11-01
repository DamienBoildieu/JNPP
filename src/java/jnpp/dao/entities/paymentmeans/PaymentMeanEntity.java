package jnpp.dao.entities.paymentmeans;

import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.dto.advisor.MessageDTO;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class PaymentMeanEntity implements Serializable {

    public static enum Type {
        
        BANKCARD,
        CHECKBOOK;
        
        public static class Values {
        
            public static final String BANKCARD = "BANKCARD";
            public static final String CHECKBOOK = "CHECKBOOK";
        
            private Values() {}
            
        }
        
    }
    
    public static enum Status {
    
        ORDERED,
        ARRIVED,
        DELIVERED;

        public PaymentMeanDTO.Status toDTO() {
            switch (ordinal()) {
                case 0:
                    return PaymentMeanDTO.Status.ORDERED;
                case 1:
                    return PaymentMeanDTO.Status.ARRIVED;
                case 2:
                    return PaymentMeanDTO.Status.DELIVERED;
            }
            return null;
        }
        
    }
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="client_fk")
    private ClientEntity client;
    @ManyToOne
    @JoinColumn(name="account_fk")
    private AccountEntity account;
    
    @Enumerated(EnumType.STRING)
    private Status status;
    
    public PaymentMeanEntity() {}
    
    public PaymentMeanEntity(ClientEntity client, AccountEntity account, Status status) {
        this.client = client;
        this.account = account;
        this.status = status;
    }
    
    public abstract Type getType(); 
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "jnpp.dao.entities.PaymentObject[ id=" + id + " ]";
    }
    
    public abstract PaymentMeanDTO toDTO();
    
}
