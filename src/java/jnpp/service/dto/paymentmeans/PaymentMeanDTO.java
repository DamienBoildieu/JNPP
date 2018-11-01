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
    
    private String login;
    private String rib;
    private Status status;

    public PaymentMeanDTO(String login, String rib, Status status) {
        this.login = login;
        this.rib = rib;
        this.status = status;
    }
  
    public abstract Type getType();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
}
