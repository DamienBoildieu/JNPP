package jnpp.dao.entities.movements;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import jnpp.dao.entities.accounts.Currency;

@Entity
@DiscriminatorValue(value = Movement.Type.Values.WITHDRAW)
public class Withdraw extends Movement implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double money;
    private Currency currency;
    
    public Withdraw() {}
    
    @Override
    public Type getType() {
        return Movement.Type.WITHDRAW;
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
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.Withdraw[ id=" + getId() + " ]";
    }
    
}
