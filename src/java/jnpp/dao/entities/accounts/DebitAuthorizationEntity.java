package jnpp.dao.entities.accounts;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class DebitAuthorizationEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="from_account_fk")
    private AccountEntity from;
    private String ribTo;
    
    public DebitAuthorizationEntity() {}
    
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
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.accounts.DebitAuthorizationEntity[ id=" + id + " ]";
    }
    
}
