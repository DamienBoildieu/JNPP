package jnpp.service.dto.accounts;

import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.accounts.CurrentAccountEntity;
import jnpp.dao.entities.accounts.JointAccountEntity;
import jnpp.dao.entities.accounts.SavingAccountEntity;
import jnpp.dao.entities.accounts.ShareAccountEntity;

public abstract class AccountDTO {
    
    public static enum Type {
    
        CURRENT,
        JOINT,
        SAVING,
        SHARE;
        
    }
    
    private String rib;
    
    public AccountDTO(String rib) {
        this.rib = rib;
    }
    
    public abstract Type getType();

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }
    
}
