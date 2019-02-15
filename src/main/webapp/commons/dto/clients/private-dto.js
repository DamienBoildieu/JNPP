class PrivateDTO extends ClientDTO {

    constructor(json) {
        super(json, ClientDTO.type.PRIVATE);
        if (!json.identity || !json.birthday)
            throw new ParseException(json, typeof this);
        this.identity = new IdentityDTO(json.identity);
        this.birthday = json.birthday;
    }

}
