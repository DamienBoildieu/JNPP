package jnpp.dao.entities.movements;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.service.dto.movements.MovementDTO;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
    @NamedQuery(
        name = "find_all_movement_by_rib",
        query = "SELECT m FROM MovementEntity m "
                + "WHERE m.account.rib = :rib "
                + "ORDER BY m.date DESC"),
    @NamedQuery(
        name = "find_recent_movement_by_rib",
        query = "SELECT m FROM MovementEntity m "
                + "WHERE m.account.rib = :rib "
                + "  AND m.date >= :date "
                + "ORDER BY m.date DESC")})
public abstract class MovementEntity implements Serializable {

    public static enum Type {
    
        TRANSFERT,
        DEBIT,
        PURCHASE,
        SALE,
        WITHDRAW,
        PAYMENT;
        
        public static class Values {

            public static final String TRANSFERT = "TRANSFERT";
            public static final String DEBIT = "DEBIT";
            public static final String PURCHASE = "PURCHASE";
            public static final String SALE = "SALE";
            public static final String WITHDRAW = "WITHDRAW";
            public static final String PAYMENT = "PAYMENT";
            
            private Values() {}

        }
    
    }
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @ManyToOne
    @JoinColumn(name = "account_fk")
    private AccountEntity account;
    
    public abstract Type getType();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
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
        if (!(object instanceof MovementEntity)) {
            return false;
        }
        MovementEntity other = (MovementEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
    
    public abstract MovementDTO toDTO();
    
    public static List<MovementDTO> toDTO(List<MovementEntity> entities) {
        List<MovementDTO> dtos = new ArrayList<MovementDTO>(entities.size());
        Iterator<MovementEntity> it = entities.iterator();
        while (it.hasNext()) dtos.add(it.next().toDTO());
        return dtos;
    }
    
}
