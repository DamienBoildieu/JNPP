package jnpp.service.dto.paymentmeans;

import jnpp.dao.entities.paymentmeans.BankCardEntity;

public class BankCardDTO extends PaymentMeanDTO {

    public BankCardDTO(BankCardEntity paymentMean) {
        super(paymentMean);
    }
    
    @Override
    public Type getType() {
        return PaymentMeanDTO.Type.BANKCARD;
    }
    
}
