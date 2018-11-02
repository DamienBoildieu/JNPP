package jnpp.service.dto.paymentmeans;

public class BankCardDTO extends PaymentMeanDTO {

    public BankCardDTO(String login, String rib, Status status) {
        super(login, rib, status);
    }
    
    @Override
    public Type getType() {
        return PaymentMeanDTO.Type.BANKCARD;
    }
    
}
