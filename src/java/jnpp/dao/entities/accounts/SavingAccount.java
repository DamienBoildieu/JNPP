package jnpp.dao.entities.accounts;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = Account.Type.SAVING)
public class SavingAccount extends Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double money;
    private Currency currency;
    
    @ManyToOne
    @JoinColumn(name="savingbook_fk")
    private SavingBook savingBook;   
    
    public SavingAccount() {}
    
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

    public SavingBook getSavingBook() {
        return savingBook;
    }

    public void setSavingBook(SavingBook savingBook) {
        this.savingBook = savingBook;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.SaveAccount[ id=" + getRib() + " ]";
    }
    
}
