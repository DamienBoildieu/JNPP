package jnpp.service.dto.paymentmeans;

public class CheckbookDTO extends PaymentMeanDTO {

    public CheckbookDTO(String login, String rib, Status status) {
        super(login, rib, status);
    }

    @Override
    public Type getType() {
        return PaymentMeanDTO.Type.CHECKBOOK;
    }
    
}
