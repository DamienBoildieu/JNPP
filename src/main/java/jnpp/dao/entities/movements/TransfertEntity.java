package jnpp.dao.entities.movements;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jnpp.dao.entities.accounts.CurrencyEntity;
import jnpp.service.dto.movements.TransfertDTO;

@Entity
@DiscriminatorValue(value = MovementEntity.Type.Values.TRANSFERT)
public class TransfertEntity extends MoneyTradeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public TransfertEntity() {
    }

    public TransfertEntity(Date date, String ribFrom, String ribTo,
            Double money, CurrencyEntity currency, String label) {
        super(date, ribFrom, ribTo, money, currency, label);
    }

    @Override
    public Type getType() {
        return MovementEntity.Type.TRANSFERT;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.transactions.TransfertEntity[ id=" + getId()
                + " ]";
    }

    @Override
    public TransfertDTO toDTO() {
        return new TransfertDTO(getDate(), getRibFrom(), getRibTo(), getMoney(),
                getCurrency().toDTO(), getLabel());
    }

}
