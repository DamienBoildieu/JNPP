class ShareAccountDTO extends AccountDTO {

    constructor(json) {
        super(json, AccountDTO.Type.SHARE);
        if (!json.shareTitles) throw new ParseException(json, typeof this);
        this.shareTitles = new Array();
        for (let i = 0; i < json.shareTitles.length; ++i)
            this.shareTitles.push(new ShareTitleDTO(json.shareTitles[i]));
    }

}
