package jnpp.dao.entities.accounts;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ShareTitle implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer amount;
    
    @ManyToOne
    @JoinColumn(name = "share_fk")
    private Share share;
    
    @ManyToOne
    @JoinColumn(name = "shareaccount_fk")
    private ShareAccount shareAccount;
    
    public ShareTitle() {}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public ShareAccount getShareAccount() {
        return shareAccount;
    }

    public void setShareAccount(ShareAccount shareAccount) {
        this.shareAccount = shareAccount;
    }    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ShareTitle)) {
            return false;
        }
        ShareTitle other = (ShareTitle) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.ShareTitle[ id=" + id + " ]";
    }
    
}
