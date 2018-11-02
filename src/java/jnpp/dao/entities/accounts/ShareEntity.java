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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import jnpp.service.dto.accounts.ShareDTO;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "find_share_by_name",
        query = "SELECT s FROM ShareEntity s WHERE s.name = :name"),
    @NamedQuery(
        name = "find_all_share",
        query = "SELECT s FROM ShareEntity s")})
public class ShareEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double value;
    @Column(nullable = false)
    private CurrencyEntity currency;
    
    public ShareEntity() {}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ShareEntity)) {
            return false;
        }
        ShareEntity other = (ShareEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.ShareEntity[ id=" + id + " ]";
    }
    
    public ShareDTO toDTO() {
        return new ShareDTO(name, value, currency.toDTO());
    }
    
    public static List<ShareDTO> toDTO(List<ShareEntity> entities) {
        List<ShareDTO> dtos = new ArrayList<ShareDTO>(entities.size());
        Iterator<ShareEntity> it = entities.iterator();
        while (it.hasNext()) dtos.add(it.next().toDTO());
        return dtos;
    }
    
}
