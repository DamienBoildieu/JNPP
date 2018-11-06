package jnpp.dao.entities.movements;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import jnpp.dao.entities.accounts.ShareEntity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class ShareTradeEntity extends TradeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer amount;
    @ManyToOne
    @JoinColumn(name = "share_fk")
    private ShareEntity share;

    public ShareTradeEntity() {
    }

    public ShareTradeEntity(Date date, String ribFrom, String ribTo, 
            Integer amount, ShareEntity share, String label) {
        super(date, ribFrom, ribTo, label);
        this.amount = amount;
        this.share = share;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ShareEntity getShare() {
        return share;
    }

    public void setShare(ShareEntity share) {
        this.share = share;
    }

}
