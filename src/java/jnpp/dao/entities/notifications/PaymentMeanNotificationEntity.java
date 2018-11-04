package jnpp.dao.entities.notifications;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.paymentmeans.PaymentMeanEntity;
import jnpp.service.dto.notifications.PaymentMeanNotificationDTO;

@Entity
@DiscriminatorValue(value = NotificationEntity.Type.Values.PAYMENT_MEAN)
public class PaymentMeanNotificationEntity extends NotificationEntity 
        implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    @JoinColumn(name = "paymentmean_fk")
    private PaymentMeanEntity paymentmean;

    public PaymentMeanNotificationEntity() {}
    
    public PaymentMeanNotificationEntity(ClientEntity client, Date date, Boolean seen, PaymentMeanEntity paymentmean) {
        super(client, date, seen);
        this.paymentmean = paymentmean;
    }
    
    @Override
    public NotificationEntity.Type getType() {
        return NotificationEntity.Type.PAYMENT_MEAN;
    }

    public PaymentMeanEntity getPaymentMean() {
        return paymentmean;
    }

    public void setPaymentMean(PaymentMeanEntity mean) {
        this.paymentmean = mean;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.notifications.PaymentMeanNotificationEntity[ id=" + getId() + " ]";
    }
    
    @Override
    public PaymentMeanNotificationDTO toDTO() {
        return new PaymentMeanNotificationDTO(getId(), getDate(), paymentmean.toDTO());
    }
    
}
