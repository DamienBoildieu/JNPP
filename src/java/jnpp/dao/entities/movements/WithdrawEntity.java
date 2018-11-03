package jnpp.dao.entities.movements;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import jnpp.dao.entities.accounts.CurrencyEntity;
import jnpp.service.dto.movements.WithdrawDTO;

@Entity
@DiscriminatorValue(value = MovementEntity.Type.Values.WITHDRAW)
public class WithdrawEntity extends MovementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double money;
    private CurrencyEntity currency;
    
    public WithdrawEntity() {}
    
    @Override
    public Type getType() {
        return MovementEntity.Type.WITHDRAW;
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
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.WithdrawEntity[ id=" + getId() + " ]";
    }
    
    @Override
    public WithdrawDTO toDTO() {
        return new WithdrawDTO(getDate(), getRibFrom(), money, currency.toDTO());
    }
    
}
