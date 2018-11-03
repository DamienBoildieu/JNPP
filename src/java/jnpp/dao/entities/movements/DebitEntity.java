package jnpp.dao.entities.movements;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CurrencyEntity;
import jnpp.service.dto.movements.DebitDTO;

@Entity
@DiscriminatorValue(value = MovementEntity.Type.Values.DEBIT)
public class DebitEntity extends MoneyTradeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public DebitEntity() {}

    public DebitEntity(Date date, String ribFrom, String ribTo, 
            Double money, CurrencyEntity currency) {
        super(date, ribFrom, ribTo, money, currency);
    }
    
    @Override
    public Type getType() {
        return MovementEntity.Type.DEBIT;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.DebitEntity[ id=" + getId() + " ]";
    }
    
    @Override
    public DebitDTO toDTO() {
        return new DebitDTO(getDate(), getRibFrom(), getRibTo(), getMoney(), getCurrency().toDTO());
    }
    
}
