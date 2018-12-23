package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import jnpp.service.dto.accounts.ShareTitleDTO;

@Entity
@NamedQueries({
        @NamedQuery(name = "find_sharetitle_by_rib_name", query = "SELECT s FROM ShareTitleEntity s "
                + "WHERE s.shareAccount.rib = :rib "
                + "  AND s.share.name = :name"),
        @NamedQuery(name = "fin_all_sharetitle_by_rib", query = "SELECT s FROM ShareTitleEntity s "
                + "WHERE s.shareAccount.rib = :rib") })
public class ShareTitleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "share_fk")
    private ShareEntity share;

    @ManyToOne
    @JoinColumn(name = "shareaccount_fk")
    private ShareAccountEntity shareAccount;

    public ShareTitleEntity() {
    }

    public ShareTitleEntity(Integer amount, ShareEntity share,
            ShareAccountEntity shareAccount) {
        this.amount = amount;
        this.share = share;
        this.shareAccount = shareAccount;
    }

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

    public ShareEntity getShare() {
        return share;
    }

    public void setShare(ShareEntity share) {
        this.share = share;
    }

    public ShareAccountEntity getShareAccount() {
        return shareAccount;
    }

    public void setShareAccount(ShareAccountEntity shareAccount) {
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
        if (!(object instanceof ShareTitleEntity)) {
            return false;
        }
        ShareTitleEntity other = (ShareTitleEntity) object;
        return !((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.ShareTitleEntity[ id=" + id + " ]";
    }

    public ShareTitleDTO toDTO() {
        return new ShareTitleDTO(amount, share.toDTO());
    }

    public static List<ShareTitleDTO> toDTO(List<ShareTitleEntity> entities) {
        List<ShareTitleDTO> dtos = new ArrayList<ShareTitleDTO>(
                entities.size());
        Iterator<ShareTitleEntity> it = entities.iterator();
        while (it.hasNext()) {
            dtos.add(it.next().toDTO());
        }
        return dtos;
    }

}
