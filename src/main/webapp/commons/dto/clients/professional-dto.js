class ProfessionalDTO extends ClientDTO {

    constructor(json) {
        super(json, ClientDTO.type.PROFESSIONAL);
        if (!json.owner || !json.name)
            throw new ParseException(json, typeof this);
        this.name = json.name;
        this.owner = new IdentityDTO(json.owner);
    }

}
