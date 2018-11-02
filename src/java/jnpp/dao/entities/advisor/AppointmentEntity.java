package jnpp.dao.entities.advisor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.dto.advisor.AppointmentDTO;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "find_all_appointment_by_rib",
        query = "SELECT a FROM AppointmentEntity a "
                + "WHERE a.client.login = :login "
                + "ORDER BY a.date DESC"),
    @NamedQuery(
        name = "find_recent_appointment_by_rib",
        query = "SELECT a FROM AppointmentEntity a "
                + "WHERE a.client.login = :login "
                + "  AND a.date >= :date "
                + "ORDER BY a.date DESC"),
    @NamedQuery(
        name = "count_advisor_appointment_in_min_max",
        query = "SELECT COUNT(a) FROM AppointmentEntity a "
                + "WHERE a.advisor.id = :id "
                + "  AND :min <= a.date "
                + "  AND a.date <= :max")})
public class AppointmentEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;
    @ManyToOne
    @JoinColumn(name="client_fk")
    private ClientEntity client;
    @ManyToOne
    @JoinColumn(name="advisor_fk")
    private AdvisorEntity advisor;
    
    public AppointmentEntity() {}
    
    public AppointmentEntity(Date date, ClientEntity client, AdvisorEntity advisor) {
        this.date = date;
        this.client = client;
        this.advisor = advisor;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public AdvisorEntity getAdvisor() {
        return advisor;
    }

    public void setAdvisor(AdvisorEntity advisor) {
        this.advisor = advisor;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AppointmentEntity)) {
            return false;
        }
        AppointmentEntity other = (AppointmentEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.Appointment[ id=" + id + " ]";
    }
    
    public AppointmentDTO toDTO() {
        return new AppointmentDTO(id, date, advisor.toDTO());
    }
    
    public static List<AppointmentDTO> toDTO(List<AppointmentEntity> entities) {
        List<AppointmentDTO> dtos = new ArrayList<AppointmentDTO>(entities.size());
        Iterator<AppointmentEntity> it = entities.iterator();
        while (it.hasNext()) dtos.add(it.next().toDTO());
        return dtos;
    }
    
}
