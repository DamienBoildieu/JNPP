package jnpp.service.dto.accounts;

import jnpp.dao.entities.accounts.SavingAccountEntity;

public class SavingAccountDTO extends MoneyAccountDTO {
    
    private final SavingBookDTO savingBook;
    
    public SavingAccountDTO(SavingAccountEntity account) {
        super(account);
        savingBook = new SavingBookDTO(account.getSavingBook());
    }

    @Override
    public Type getType() {
        return AccountDTO.Type.SAVING;
    }

    public SavingBookDTO getSavingBook() {
        return savingBook;
    }
    
}
