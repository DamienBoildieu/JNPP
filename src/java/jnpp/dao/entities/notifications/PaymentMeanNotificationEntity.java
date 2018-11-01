package jnpp.dao.entities.notifications;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jnpp.dao.entities.paymentmeans.PaymentMeanEntity;

@Entity
@DiscriminatorValue(value = NotificationEntity.Type.Values.PAYMENT_MEAN)
public class PaymentMeanNotificationEntity extends NotificationEntity 
        implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    @JoinColumn(name = "paymentmean_fk")
    private PaymentMeanEntity mean;

    @Override
    public NotificationEntity.Type getType() {
        return NotificationEntity.Type.PAYMENT_MEAN;
    }

    public PaymentMeanEntity getPaymentMean() {
        return mean;
    }

    public void setPaymentMean(PaymentMeanEntity mean) {
        this.mean = mean;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.notifications.PaymentMeanNotificationEntity[ id=" + getId() + " ]";
    }
    
}
