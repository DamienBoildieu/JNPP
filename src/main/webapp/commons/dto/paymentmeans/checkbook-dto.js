class CheckbookDTO extends PaymentMeanDTO {

    constructor(json) {
        super(json, PaymentMeanDTO.Type.CHECKBOOK);
    }

}
