package jnpp.dao.entities.accounts;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.dto.accounts.SavingAccountDTO;

@Entity
@DiscriminatorValue(value = AccountEntity.Type.Values.SAVING)
@NamedQueries({
    @NamedQuery(
        name = "has_saving_account",
        query = "SELECT COUNT(a) FROM SavingAccountEntity a "
                + "WHERE a.clients.id = :login "
                + "  AND a.savingBook.id = :savingbook_id")})
public class SavingAccountEntity extends MoneyAccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double money;
    private Currency currency;
    
    @ManyToOne
    @JoinColumn(name="savingbook_fk")
    private SavingBookEntity savingBook;   
    
    public SavingAccountEntity() {}
    
    public SavingAccountEntity(String rib, ClientEntity client, Double money, Currency currency, SavingBookEntity savingBook) {
        super(rib, client, money, currency);
        this.savingBook = savingBook;
    }
    
    @Override
    public Type getType() {
        return AccountEntity.Type.SAVING;
    }

    public SavingBookEntity getSavingBook() {
        return savingBook;
    }

    public void setSavingBook(SavingBookEntity savingBook) {
        this.savingBook = savingBook;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.SavingAccountEntity[ id=" + getRib() + " ]";
    }
    
    @Override
    public SavingAccountDTO toDTO() {
        return new SavingAccountDTO(getRib(), getMoney(), getCurrency(), savingBook.toDTO());
    }
    
}
