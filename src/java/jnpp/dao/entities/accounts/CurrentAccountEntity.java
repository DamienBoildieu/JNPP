package jnpp.dao.entities.accounts;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import jnpp.dao.entities.clients.ClientEntity;

@Entity
@DiscriminatorValue(value = AccountEntity.Type.Values.CURRENT)
@NamedQueries({
    @NamedQuery(
        name = "has_current_account",
        query = "SELECT COUNT(a) FROM CurrentAccountEntity a "
                + "WHERE a.clients.id = :client_id")})
public class CurrentAccountEntity extends MoneyAccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;    

    private Double limit;
    
    public CurrentAccountEntity() {}
    
    public CurrentAccountEntity(String rib, ClientEntity client, Double money, Currency currency, Double limit) {
        super(rib, client, money, currency);
        this.limit = limit;
    }
    
    @Override
    public Type getType() {
        return AccountEntity.Type.CURRENT;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.CurrentAccountEntity[ id=" + getRib() + " ]";
    }
    
}
