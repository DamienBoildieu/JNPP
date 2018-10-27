package jnpp.dao.entities.notifications;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jnpp.dao.entities.accounts.Account;

@Entity
@DiscriminatorValue(value = Notification.Type.Values.OVERDRAFT)
public class OverdraftNotification extends Notification 
        implements Serializable {

    private static final long serialVersionUID = 1L;
  
    @ManyToOne
    @JoinColumn(name = "account_fk")
    private Account account;

    @Override
    public Type getType() {
        return Notification.Type.OVERDRAFT;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.notifications.OverdraftNotification[ id=" + getId() + " ]";
    }
    
}
