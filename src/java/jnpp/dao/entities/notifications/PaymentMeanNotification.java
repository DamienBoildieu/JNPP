package jnpp.dao.entities.notifications;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jnpp.dao.entities.PaymentMean;

@Entity
@DiscriminatorValue(value = Notification.Type.Values.PAYMENT_MEAN)
public class PaymentMeanNotification extends Notification 
        implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    @JoinColumn(name = "paymentmean_fk")
    private PaymentMean mean;

    @Override
    public Notification.Type getType() {
        return Notification.Type.PAYMENT_MEAN;
    }

    public PaymentMean getPaymentMean() {
        return mean;
    }

    public void setPaymentMean(PaymentMean mean) {
        this.mean = mean;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.notifications.PaymentMeanNotification[ id=" + getId() + " ]";
    }
    
}
