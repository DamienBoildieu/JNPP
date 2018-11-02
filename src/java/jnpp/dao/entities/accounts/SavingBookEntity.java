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
import jnpp.service.dto.accounts.SavingBookDTO;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "find_savingbook_by_name",
        query = "SELECT s FROM SavingBookEntity s WHERE s.name = :name"),
    @NamedQuery(
        name = "find_all_savingbook",
        query = "SELECT s FROM SavingBookEntity s")})
public class SavingBookEntity implements Serializable {

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
    
    public SavingBookEntity() {}
    
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SavingBookEntity)) {
            return false;
        }
        SavingBookEntity other = (SavingBookEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.SavingBookEntity[ id=" + id + " ]";
    }
    
    public SavingBookDTO toDTO() {
        return new SavingBookDTO(name, moneyRate, timeRate);
    }
    
    public static List<SavingBookDTO> toDTO(List<SavingBookEntity> entities) {
        List<SavingBookDTO> dtos = new ArrayList<SavingBookDTO>(entities.size());
        Iterator<SavingBookEntity> it = entities.iterator();
        while (it.hasNext()) dtos.add(it.next().toDTO());
        return dtos;
    }
    
}
