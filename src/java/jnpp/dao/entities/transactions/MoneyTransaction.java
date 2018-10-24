package jnpp.dao.entities.transactions;

import java.io.Serializable;

import javax.persistence.Entity;

import jnpp.dao.entities.accounts.Currency;

@Entity
public abstract class MoneyTransaction extends AccountTransaction 
        implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Double money;
    private Currency currency;

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
    
}
