package jnpp.service.dto.accounts;

import jnpp.dao.entities.accounts.ShareEntity;

public class ShareDTO {
    
    private final String name;
    private final Double value;
    
    public ShareDTO(ShareEntity share) {
        name = share.getName();
        value = share.getValue();
    }
    
    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }
    
}
