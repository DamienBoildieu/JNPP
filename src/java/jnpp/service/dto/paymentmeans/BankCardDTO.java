package jnpp.service.dto.paymentmeans;

public class BankCardDTO extends PaymentMeanDTO {

    public BankCardDTO(String id, String login, String rib, Status status) {
        super(id, login, rib, status);
    }
    
    @Override
    public Type getType() {
        return PaymentMeanDTO.Type.BANKCARD;
    }
    
}
