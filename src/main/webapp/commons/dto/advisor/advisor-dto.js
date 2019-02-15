class AdvisorDTO {

    constructor(json) {
        if (!json || !json.identity || !json.email || !json.phone || !json.officeAddress)
            throw new ParseException(json, typeof this);
        this.identity = new IdentityDTO(json.identity);
        this.email = json.email;
        this.phone = json.phone;
        this.officeAddress = new AddressDTO(json.officeAddress);
    }

}
