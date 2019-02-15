class ShareTitleDTO {

    constructor(json) {
        if (!json || !json.amount || !json.share)
            throw new ParseException(json, typeof this);
        this.amount = json.amount;
        this.share = new ShareDTO(json.share);
    }

}
