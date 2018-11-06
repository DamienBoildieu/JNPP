package jnpp.service.dto.notifications;

import java.util.Date;
import jnpp.service.dto.paymentmeans.PaymentMeanDTO;

public class PaymentMeanNotificationDTO extends NotificationDTO {

    private PaymentMeanDTO paymentMean;

    public PaymentMeanNotificationDTO(Long id, Date date, Boolean seen, PaymentMeanDTO paymentMean) {
        super(id, date, seen);
        this.paymentMean = paymentMean;
    }

    @Override
    public Type getType() {
        return NotificationDTO.Type.PAYMENT_MEAN;
    }

    public PaymentMeanDTO getPaymentMean() {
        return paymentMean;
    }

    public void setPaymentMean(PaymentMeanDTO paymentMean) {
        this.paymentMean = paymentMean;
    }

}
