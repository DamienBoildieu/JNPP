package jnpp.service.dto.paymentmeans;

import jnpp.dao.entities.paymentmeans.BankCardEntity;
import jnpp.dao.entities.paymentmeans.CheckbookEntity;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity;

public abstract class PaymentMeanDTO {
    
    public static enum Type {
        
        BANKCARD,
        CHECKBOOK;
        
    }
    
    public static enum Status {
    
        ORDERED,
        ARRIVED,
        DELIVERED;

    }
    
    private final String login;
    private final String rib;
    private final Status status;
    
    public PaymentMeanDTO(PaymentMeanEntity paymentMean) {
        login = paymentMean.getClient().getLogin();
        rib = paymentMean.getAccount().getRib();
        switch (paymentMean.getStatus()) {
            case ARRIVED:
                status = PaymentMeanDTO.Status.ARRIVED;
                break;
            case DELIVERED:
                status = PaymentMeanDTO.Status.DELIVERED;
                break;
            case ORDERED:
                status = PaymentMeanDTO.Status.ORDERED;
                break;
            default:
                status = null;
        }
    }
    
    public abstract Type getType(); 

    public String getLogin() {
        return login;
    }

    public String getRib() {
        return rib;
    }

    public Status getStatus() {
        return status;
    }
    
    public static PaymentMeanDTO newDTO(PaymentMeanEntity paymentMean) {
        switch (paymentMean.getType()) {
            case BANKCARD:
                return new BankCardDTO((BankCardEntity) paymentMean);
            case CHECKBOOK:
                return new CheckbookDTO((CheckbookEntity) paymentMean);
        }
        return null;
    }
    
}
