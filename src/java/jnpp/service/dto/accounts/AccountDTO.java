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
    
    private final String rib;
    
    public AccountDTO(AccountEntity account) {
        rib = account.getRib();
    }
    
    public abstract Type getType();
    
    public String getTRib() {
        return rib;
    }
    
    public static AccountDTO newDTO(AccountEntity account) {
        switch (account.getType()) {
            case CURRENT:
                return new CurrentAccountDTO((CurrentAccountEntity) account);
            case JOINT:
                return new JointAccountDTO((JointAccountEntity) account);
            case SAVING:
                return new SavingAccountDTO((SavingAccountEntity) account);
            case SHARE:
                return new ShareAccountDTO((ShareAccountEntity) account);
        }
        return null;
    }
    
}
