
package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.List;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import jnpp.dao.entities.clients.ClientEntity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class MoneyAccountEntity extends AccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double money;
    private Currency currency;
    
    public MoneyAccountEntity() {}
    
    public MoneyAccountEntity(String rib, ClientEntity client, Double money, Currency currency) {
        super(rib, client);
        this.money = money;
        this.currency = currency;
    }
    
    public MoneyAccountEntity(String rib, List<ClientEntity> clients, Double money, Currency currency) {
        super(rib, clients);
        this.money = money;
        this.currency = currency;
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
    
}
