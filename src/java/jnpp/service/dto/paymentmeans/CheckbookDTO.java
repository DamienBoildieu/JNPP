package jnpp.service.dto.paymentmeans;

public class CheckbookDTO extends PaymentMeanDTO {

    public CheckbookDTO(String id, String login, String rib, Status status) {
        super(id, login, rib, status);
    }

    @Override
    public Type getType() {
        return PaymentMeanDTO.Type.CHECKBOOK;
    }
    
}
