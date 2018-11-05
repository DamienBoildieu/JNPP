package jnpp.dao.entities.movements;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jnpp.dao.entities.accounts.CurrencyEntity;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class MoneyMovementEntity extends MovementEntity implements Serializable {

   private static final long serialVersionUID = 1L;
    
    private Double money;
    private CurrencyEntity currency;
    
    public MoneyMovementEntity() {}
    
    public MoneyMovementEntity(Date date, String ribFrom, Double money, 
            CurrencyEntity currency) {
        super(date, ribFrom);
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
