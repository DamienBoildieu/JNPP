package jnpp.dao.entities.transactions;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import jnpp.dao.entities.accounts.Share;

@Entity
public abstract class ShareTransaction extends AccountTransaction 
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer amount;
    @ManyToOne
    @JoinColumn(name="share_fk")
    private Share share;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }
    
}
