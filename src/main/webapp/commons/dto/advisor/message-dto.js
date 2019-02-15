class MessageDTO {

    constructor(json) {
        if (!json || !json.direction || !json.date || !json.content ||
                !json.advisor) throw new ParseException(json, typeof this);
        this.direction = json.direction;
        this.date = json.date;
        this.content = json.content;
        this.advisor = new AdvisorDTO(json.advisor);
    }

}

MessageDTO.Direction = Object.freeze({
    CLIENT_TO_ADVISOR: 0,
    ADVISOR_TO_CLIENT: 1
});
