class SavingBookDTO {

    constructor(json) {
        if (!json || !json.name || !json.moneyRate || !json.timeRate)
            throw new ParseException(json, typeof this);
        this.amount = json.amount;
        this.moneyRate = json.moneyRate;
        this.timeRate = json.timeRate;
    }

}
