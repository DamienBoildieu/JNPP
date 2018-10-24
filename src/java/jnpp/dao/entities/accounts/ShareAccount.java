package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = Account.Type.SHARE)
public class ShareAccount extends Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "shareAccount")
    private List<ShareTitle> shareTitles = new ArrayList<ShareTitle>();

    public List<ShareTitle> getShareTitles() {
        return shareTitles;
    }

    public void setShareTitles(List<ShareTitle> shareTitles) {
        this.shareTitles = shareTitles;
    }
       
    @Override
    public String toString() {
        return "jnpp.dao.entities.ShareAccount[ id=" + getRib() + " ]";
    }
    
}
