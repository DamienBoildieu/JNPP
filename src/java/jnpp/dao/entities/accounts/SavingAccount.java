package jnpp.dao.entities.accounts;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import jnpp.dao.entities.clients.Client;

@Entity
@DiscriminatorValue(value = Account.Type.Values.SAVING)
@NamedQueries({
    @NamedQuery(
        name = "has_saving_account",
        query = "SELECT COUNT(a) FROM SavingAccount a "
                + "WHERE a.savingBook.id = :book_id"
                + "  AND a.client.id = :client_id")})
public class SavingAccount extends Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double money;
    private Currency currency;
    
    @ManyToOne
    @JoinColumn(name="savingbook_fk")
    private SavingBook savingBook;   
    
    public SavingAccount() {}
    
    public SavingAccount(String rib, Client client, Double money, Currency currency) {
        super(rib, client);
        this.money = money;
        this.currency = currency;
    }
    
    @Override
    public Type getType() {
        return Account.Type.SAVING;
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
