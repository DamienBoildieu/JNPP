class ClientDTO {

    constructor(json) {
        if (!json || !json.login || !json.password || !json.type || !json.email
                || !json.name) throw new ParseException(json, typeof this);
        this.login = json.login;
        this.password = json.password;
        this.type = json.type;
        this.name = json.name;
        this.email = json.email;
    }

}
