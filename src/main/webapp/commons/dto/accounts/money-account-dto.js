class MoneyAccountDTO extends AccountDTO {

    constructor(json, type) {
        super(json, type);
        if (!json.money || !json.currency)
            throw new ParseException(json, typeof this);
        this.money = json.money;
        this.currency = json.currency;
    }

}
