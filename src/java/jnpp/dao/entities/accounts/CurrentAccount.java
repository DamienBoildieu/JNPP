package jnpp.dao.entities.accounts;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import jnpp.dao.entities.clients.Client;

@Entity
@DiscriminatorValue(value = Account.Type.Values.CURRENT)
@NamedQueries({
    @NamedQuery(
        name = "has_current_account",
        query = "SELECT COUNT(a) FROM CurrentAccount a "
                + "WHERE a.client.id = :client_id")})
public class CurrentAccount extends Account implements Serializable {

    private static final long serialVersionUID = 1L;    

    private Double money;
    private Currency currency;
    private Double limit;
    
    public CurrentAccount() {}
    
    public CurrentAccount(String rib, Client client, Double money, Currency currency, Double limit) {
        super(rib, client);
        this.money = money;
        this.currency = currency;
        this.limit = limit;
    }
    
    @Override
    public Type getType() {
        return Account.Type.CURRENT;
    }
    
    public Double getMoney() {
        return money;
    }
    
    public void setMoney(Double money) {
        this.money = money;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.CurrentAccount[ id=" + getRib() + " ]";
    }
    
}
