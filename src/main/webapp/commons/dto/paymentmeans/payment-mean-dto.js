class PaymentMeanDTO {

    constructor(json, type) {
        if (!json || !json.id || !json.login || !json.rib || !json.status)
            throw new ParseException(json, typeof this);
        this.id = json.id;
        this.login = json.login;
        this.rib = json.rib;
        this.status = json.status;
        this.type = type;
    }

}

PaymentMeanDTO.Type = Object.freeze({
    BANKCARD: 0,
    CHECKBOOK: 1
});

PaymentMeanDTO.Status = Object.freeze({
    ORDERED: 0,
    ARRIVED: 1,
    DELIVERED: 2
});
