class AppointmentDTO {

    constructor(json) {
        if (!json || !json.id || !json.date || !json.advisor)
            throw new ParseException(json, typeof this);
        this.id = json.id;
        this.date = json.date;
        this.advisor = new AdvisorDTO(json.advisor);
    }

}
