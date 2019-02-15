class CurrentAccountDTO extends MoneyAccountDTO {

    constructor(json) {
        super(json, AccountDTO.Type.CURRENT);
        if (!json.limit) throw new ParseException(json, typeof this);
        this.limit = limit;
    }

}
