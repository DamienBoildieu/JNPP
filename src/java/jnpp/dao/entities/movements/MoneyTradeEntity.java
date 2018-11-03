package jnpp.dao.entities.movements;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CurrencyEntity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class MoneyTradeEntity extends TradeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Double money;
    private CurrencyEntity currency;

    public MoneyTradeEntity() {}
    
    public MoneyTradeEntity(Date date, String ribFrom, String ribTo, 
            Double money, CurrencyEntity currency) {
        super(date, ribFrom, ribTo);
        this.money = money;
        this.currency = currency;
    }
    
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
