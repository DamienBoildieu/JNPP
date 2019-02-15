class JointAccountDTO extends CurrentAccountDTO {

    constructor(json) {
        super(json);
        this.type = AccountDTO.Type.JOINT;
        if (!json.owners) throw new ParseException(json, typeof this);
        this.owners = new Array();
        for (let i = 0; i < json.owners.length; ++i)
            this.owners.push(new IdentityDTO(json.owners[i]));
    }

}
