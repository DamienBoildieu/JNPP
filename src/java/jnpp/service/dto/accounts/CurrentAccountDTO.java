package jnpp.service.dto.accounts;

import jnpp.dao.entities.accounts.Currency;
import jnpp.service.dto.accounts.AccountDTO.Type;

public class CurrentAccountDTO extends MoneyAccountDTO {

    private Double limit;
    
    public CurrentAccountDTO(String rib, Double money, Currency currency, Double limit) {
        super(rib, money, currency);
        this.limit = limit;
    }
    
    @Override
    public Type getType() {
        return AccountDTO.Type.CURRENT;
    }
    
    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }
    
}
