package jnpp.dao.entities.movements;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jnpp.dao.entities.accounts.CurrencyEntity;
import jnpp.service.dto.movements.DepositDTO;

@Entity
@DiscriminatorValue(value = MovementEntity.Type.Values.DEPOSIT)
public class DepositEntity extends MoneyMovementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public DepositEntity() {
    }

    public DepositEntity(Date date, String ribFrom, Double money,
            CurrencyEntity currency, String label) {
        super(date, ribFrom, money, currency, label);
    }

    @Override
    public Type getType() {
        return MovementEntity.Type.DEPOSIT;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.movements.DepositEntity[ id=" + getId()
                + " ]";
    }

    @Override
    public DepositDTO toDTO() {
        return new DepositDTO(getDate(), getRibFrom(), getMoney(),
                getCurrency().toDTO(), getLabel());
    }

}
