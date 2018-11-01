package jnpp.dao.entities.advisor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

@Entity
@NamedQueries({
    @NamedQuery(
        name = "find_all_message_by_login",
        query = "SELECT m FROM MessageEntity m "
                + "WHERE m.client.login = :login "
                + "ORDER BY m.date DESC"),
    @NamedQuery(
        name = "find_recent_message_by_login",
        query = "SELECT m FROM MessageEntity m "
                + "WHERE m.client.login = :login "
                + "  AND m.date >= :date "
                + "ORDER BY m.date DESC")})
public class MessageEntity implements Serializable {

    public static enum Direction {
        
        CLIENT_TO_ADVISOR,
        ADVISOR_TO_CLIENT;
        
    }
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_fk")
    private ClientEntity client;
    @ManyToOne
    @JoinColumn(name = "advisor_fk")
    private AdvisorEntity advisor;
    @Enumerated(EnumType.STRING)
    private Direction direction;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    private String content;
    
    public MessageEntity() {}
    
    public MessageEntity(ClientEntity client, AdvisorEntity advisor, 
            Direction direction, Date date, String content) {
        this.client = client;
        this.advisor = advisor;
        this.direction = direction;
        this.date = date;
        this.content = content;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof MessageEntity)) {
            return false;
        }
        MessageEntity other = (MessageEntity) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.Message[ id=" + id + " ]";
    }
    
}
