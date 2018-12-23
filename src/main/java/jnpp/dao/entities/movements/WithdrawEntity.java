package jnpp.dao.entities.movements;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import jnpp.dao.entities.accounts.CurrencyEntity;
import jnpp.service.dto.movements.WithdrawDTO;

@Entity
@DiscriminatorValue(value = MovementEntity.Type.Values.WITHDRAW)
public class WithdrawEntity extends MoneyMovementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public WithdrawEntity() {
    }

    public WithdrawEntity(Date date, String ribFrom, Double money,
            CurrencyEntity currency, String label) {
        super(date, ribFrom, money, currency, label);
    }

    @Override
    public Type getType() {
        return MovementEntity.Type.WITHDRAW;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.WithdrawEntity[ id=" + getId() + " ]";
    }

    @Override
    public WithdrawDTO toDTO() {
        return new WithdrawDTO(getDate(), getRibFrom(), getMoney(), getCurrency().toDTO(), getLabel());
    }

}
