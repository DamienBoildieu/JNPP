package jnpp.dao.entities.notifications;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.dto.notifications.OverdraftNotificationDTO;

@Entity
@DiscriminatorValue(value = NotificationEntity.Type.Values.OVERDRAFT)
public class OverdraftNotificationEntity extends NotificationEntity
        implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "account_fk")
    private AccountEntity account;

    public OverdraftNotificationEntity() {
    }

    public OverdraftNotificationEntity(ClientEntity client, Date date,
            Boolean seen, AccountEntity account) {
        super(client, date, seen);
        this.account = account;
    }

    @Override
    public Type getType() {
        return NotificationEntity.Type.OVERDRAFT;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.notifications.OverdraftNotificationEntity[ id="
                + getId() + " ]";
    }

    @Override
    public OverdraftNotificationDTO toDTO() {
        return new OverdraftNotificationDTO(getId(), getDate(), getSeen(),
                account.getRib());
    }

}
