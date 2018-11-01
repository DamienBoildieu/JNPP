package jnpp.service.dto.notifications;

import jnpp.dao.entities.notifications.PaymentMeanNotificationEntity;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;

public class PaymentMeanNotificationDTO extends NotificationDTO {
    
    private final PaymentMeanDTO paymentMean;
    
    public PaymentMeanNotificationDTO(PaymentMeanNotificationEntity notification) {
        super(notification);
        paymentMean = PaymentMeanDTO.newDTO(notification.getPaymentMean());
    }
    
    @Override
    public Type getType() {
        return NotificationDTO.Type.PAYMENT_MEAN;
    }

    public PaymentMeanDTO getPaymentMean() {
        return paymentMean;
    }
    
}
