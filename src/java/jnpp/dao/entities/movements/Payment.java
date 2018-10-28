package jnpp.dao.entities.movements;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jnpp.dao.entities.PaymentMean;

import jnpp.dao.entities.accounts.Currency;

@Entity
@DiscriminatorValue(value = Movement.Type.Values.PAYMENT)
public class Payment extends Movement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Double money;
    private Currency currency;
    
    @ManyToOne
    @JoinColumn(name="paymentmean_fk")
    private PaymentMean paymentMean;
    
    public Payment() {}
    
    @Override
    public Type getType() {
        return Movement.Type.PAYMENT;
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

    public PaymentMean getPaymentMean() {
        return paymentMean;
    }

    public void setPaymentMean(PaymentMean paymentMean) {
        this.paymentMean = paymentMean;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.movements.Paiement[ id=" + getId() + " ]";
    }
    
}
