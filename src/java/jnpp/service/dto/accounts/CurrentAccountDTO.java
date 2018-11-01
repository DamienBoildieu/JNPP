package jnpp.service.dto.accounts;

import jnpp.dao.entities.accounts.CurrentAccountEntity;
import jnpp.service.dto.accounts.AccountDTO.Type;

public class CurrentAccountDTO extends MoneyAccountDTO {

    private final Double limit;
    
    public CurrentAccountDTO(CurrentAccountEntity account) {
        super(account);
        limit = account.getLimit();
    }
    
    @Override
    public Type getType() {
        return AccountDTO.Type.CURRENT;
    }
    
    public Double getLimit() {
        return limit;
    }
    
}
