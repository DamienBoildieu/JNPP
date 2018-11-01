package jnpp.service.dto.movements;

import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.movements.WithdrawEntity;

public class WithdrawDTO extends MovementDTO {
    
    private final Double money;
    private final Currency currency;
    
    public WithdrawDTO(WithdrawEntity withdraw) {
        super(withdraw);
        money = withdraw.getMoney();
        currency = withdraw.getCurrency();
    }
    
    @Override
    public Type getType() {
        return MovementDTO.Type.WITHDRAW;
    }
    
    public Double getMoney() {
        return money;
    }

    public Currency getCurrency() {
        return currency;
    }
    
}
