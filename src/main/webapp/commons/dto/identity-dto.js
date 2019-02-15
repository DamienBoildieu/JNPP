class IdentityDTO {

    constructor(json) {
        if (!json || !json.gender || !json.firstname || !json.lastname)
            throw new ParseException(json, typeof this);
        this.gender = json.gender;
        this.street = json.street;
        this.city = json.city;
        this.state = json.state;
    }

}

IdentityDTO.Gender = Object.freeze({
    MALE: 0,
    FEMALE: 1
});
