class AddressDTO {

    constructor(json) {
        if (!json || !json.number || !json.street || !json.city || !json.state)
            throw new ParseException(json, typeof this);
        this.number = json.number;
        this.street = json.street;
        this.city = json.city;
        this.state = json.state;
    }

}
