class SavingAccountDTO extends MoneyAccountDTO {

    constructor(json) {
        super(json, AccountDTO.SAVING);
        if (!json.savingBook) throw new ParseException(json, typeof this);
        json.savingBook = new SavingBookDTO(json.savingBook);
    }

}
