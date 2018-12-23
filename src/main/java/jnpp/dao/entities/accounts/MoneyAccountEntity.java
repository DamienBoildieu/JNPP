package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import jnpp.dao.entities.clients.ClientEntity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class MoneyAccountEntity extends AccountEntity
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double money;
    private CurrencyEntity currency;

    public MoneyAccountEntity() {
    }

    public MoneyAccountEntity(String rib, ClientEntity client, Double money,
            CurrencyEntity currency) {
        super(rib, client);
        this.money = money;
        this.currency = currency;
    }

    public MoneyAccountEntity(String rib, List<ClientEntity> clients,
            Double money, CurrencyEntity currency) {
        super(rib, clients);
        this.money = money;
        this.currency = currency;
    }

    public abstract boolean canOverdraft();

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

}
