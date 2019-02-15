class ShareDTO {

    constructor(json) {
        if (!json || !json.name || !json.value || !json.currency)
            throw new ParseException(json, typeof this);
        this.name = json.name;
        this.value = json.value;
        this.currency = json.currency;
    }

}
