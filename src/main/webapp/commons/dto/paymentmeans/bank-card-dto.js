class BankCardDTO extends PaymentMeanDTO {

    constructor(json) {
        super(json, PaymentMeanDTO.Type.BANKCARD);
    }

}
