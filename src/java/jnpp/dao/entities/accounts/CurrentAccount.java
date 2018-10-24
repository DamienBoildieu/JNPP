package jnpp.dao.entities.accounts;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = Account.Type.CURRENT)
public class CurrentAccount extends Account implements Serializable {

    private static final long serialVersionUID = 1L;    

    private Double money;
    private Currency currency;
    private Double limit;
    
    public CurrentAccount() {}
    
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
