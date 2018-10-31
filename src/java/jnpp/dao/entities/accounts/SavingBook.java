package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "is_savingbook_fake",
        query = "SELECT COUNT(s) FROM SavingBook s "
                + "WHERE s.id = :id "
                + "  AND s.name = :name")})
public class SavingBook implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double moneyRate;
    @Column(nullable = false)
    private Double timeRate;
    
    @OneToMany(mappedBy = "savingBook")
    private List<SavingAccount> savingAccounts = new ArrayList<SavingAccount>();
    
    public SavingBook() {}
    
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

    public Double getMoneyRate() {
        return moneyRate;
    }

    public void setMoneyRate(Double moneyRate) {
        this.moneyRate = moneyRate;
    }

    public Double getTimeRate() {
        return timeRate;
    }

    public void setTimeRate(double timeRate) {
        this.timeRate = timeRate;
    }
    
    public List<SavingAccount> getSavingAccounts() {
        return savingAccounts;
    }

    public void setSavingAccounts(List<SavingAccount> savingAccounts) {
        this.savingAccounts = savingAccounts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SavingBook)) {
            return false;
        }
        SavingBook other = (SavingBook) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.SavingBook[ id=" + id + " ]";
    }
    
}
