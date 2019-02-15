class AccountDTO {

    constructor(json, type) {
        if (!json || !json.rib) throw new ParseException(json, typeof this);
        this.rib = json.rib;
        this.type = type;
    }

}

AccountDTO.Type = Object.freeze({
    CURRENT: 0,
    JOINT: 1,
    SAVING: 2,
    SHARE: 3
});
