package jnpp.dao.entities.accounts;

import jnpp.service.dto.accounts.CurrencyDTO;

public enum CurrencyEntity {
    
    EURO;
    
    public CurrencyDTO toDTO() {
        return CurrencyDTO.EURO;
    }
    
}
