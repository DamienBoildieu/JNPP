package jnpp.dao.entities.movements;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity;

import jnpp.dao.entities.accounts.Currency;

@Entity
@DiscriminatorValue(value = MovementEntity.Type.Values.PAYMENT)
public class PaymentEntity extends MovementEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Double money;
    private Currency currency;
    
    @ManyToOne
    @JoinColumn(name="paymentmean_fk")
    private PaymentMeanEntity paymentMean;
    
    public PaymentEntity() {}
    
    @Override
    public Type getType() {
        return MovementEntity.Type.PAYMENT;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
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
    
}
