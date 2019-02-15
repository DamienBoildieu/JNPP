class DebitAuthorizationDTO {

    constructor(json) {
        if (!json || !json.ribFrom || !json.ribTo)
            throw new ParseException(json, typeof this);
        this.ribFrom = json.ribFrom;
        this.ribTo = json.ribTo;
    }

}
