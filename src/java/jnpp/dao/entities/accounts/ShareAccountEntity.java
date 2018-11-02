package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.dto.accounts.ShareAccountDTO;
import jnpp.service.dto.accounts.ShareTitleDTO;

@Entity
@DiscriminatorValue(value = AccountEntity.Type.Values.SHARE)
@NamedQueries({
    @NamedQuery(
        name = "has_share_account",
        query = "SELECT COUNT(a) FROM ShareAccountEntity a "
                + "WHERE a.clients.id = :login")})
public class ShareAccountEntity extends AccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "shareAccount")
    private List<ShareTitleEntity> shareTitles = new ArrayList<ShareTitleEntity>();

    public ShareAccountEntity(String rib, ClientEntity client) {
        super(rib, client);
    } 
    
    public ShareAccountEntity() {}
    
    @Override
    public Type getType() {
        return AccountEntity.Type.SHARE;
    }
    
    public List<ShareTitleEntity> getShareTitles() {
        return shareTitles;
    }

    public void setShareTitles(List<ShareTitleEntity> shareTitles) {
        this.shareTitles = shareTitles;
    }
       
    @Override
    public String toString() {
        return "jnpp.dao.entities.ShareAccount[ id=" + getRib() + " ]";
    }
    
    @Override
    public ShareAccountDTO toDTO() {
        List<ShareTitleDTO> titles = new ArrayList<ShareTitleDTO>(shareTitles.size());
        Iterator<ShareTitleEntity> it = shareTitles.iterator();
        while (it.hasNext()) titles.add(it.next().toDTO());
        return new ShareAccountDTO(getRib(), titles);
    }
    
}
