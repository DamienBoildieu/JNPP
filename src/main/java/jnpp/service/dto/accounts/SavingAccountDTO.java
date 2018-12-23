package jnpp.service.dto.accounts;

public class SavingAccountDTO extends MoneyAccountDTO {

    private SavingBookDTO savingBook;

    public SavingAccountDTO(String rib, Double money, CurrencyDTO currency,
            SavingBookDTO savingBook) {
        super(rib, money, currency);
        this.savingBook = savingBook;
    }

    @Override
    public Type getType() {
        return AccountDTO.Type.SAVING;
    }

    public SavingBookDTO getSavingBook() {
        return savingBook;
    }

    public void setSavingBook(SavingBookDTO savingBook) {
        this.savingBook = savingBook;
    }

}
