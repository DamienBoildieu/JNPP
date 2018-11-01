package jnpp.service.dto.paymentmeans;

import jnpp.dao.entities.paymentmeans.CheckbookEntity;

public class CheckbookDTO extends PaymentMeanDTO {

    public CheckbookDTO(CheckbookEntity paymentMean) {
        super(paymentMean);
    }
    
    @Override
    public Type getType() {
        return PaymentMeanDTO.Type.CHECKBOOK;
    }
    
}
