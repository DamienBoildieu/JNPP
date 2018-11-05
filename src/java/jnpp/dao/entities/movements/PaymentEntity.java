package jnpp.dao.entities.movements;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jnpp.dao.entities.accounts.CurrencyEntity;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity;
import jnpp.service.dto.movements.PaymentDTO;

@Entity
@DiscriminatorValue(value = MovementEntity.Type.Values.PAYMENT)
public class PaymentEntity extends MoneyMovementEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String target;
    
    @ManyToOne
    @JoinColumn(name="paymentmean_fk")
    private PaymentMeanEntity paymentMean;
    
    public PaymentEntity() {}
    
    public PaymentEntity(Date date, String ribFrom, Double money, 
            CurrencyEntity currency, String target, PaymentMeanEntity paymentMean) {
        super(date, ribFrom, money, currency);
        this.target = target;
        this.paymentMean = paymentMean;
    }
    
    @Override
    public Type getType() {
        return MovementEntity.Type.PAYMENT;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public PaymentMeanEntity getPaymentMean() {
        return paymentMean;
    }

    public void setPaymentMean(PaymentMeanEntity paymentMean) {
        this.paymentMean = paymentMean;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.movements.PaymentEntity[ id=" + getId() + " ]";
    }
    
    @Override
    public PaymentDTO toDTO() {
        return new PaymentDTO(getDate(), getRibFrom(), getMoney(), getCurrency().toDTO(), target);
    }
    
}
