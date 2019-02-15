class ClientDTO {

    constructor(json, type) {
        if (!json || !json.login || !json.address || !json.email || !json.phone)
            throw new ParseException(json, typeof this);
        this.login = json.login;
        this.address = new AddressDTO(json.address);
        this.email = json.email;
        this.phone = json.phone;
        this.type = type;
    }

}

ClientDTO.Type = Object.freeze({
    PRIVATE: 0,
    PROFESIONAL: 1
});
