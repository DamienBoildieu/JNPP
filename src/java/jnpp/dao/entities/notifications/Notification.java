package jnpp.dao.entities.notifications;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jnpp.dao.entities.clients.Client;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
    @NamedQuery(
        name = "is_notification_fake",
        query = "SELECT COUNT(n) FROM Notification n "
                + "WHERE n.id = :id"),
    @NamedQuery(
        name = "find_all_client_notification",
        query = "SELECT n FROM Notification n "
                + "WHERE n.client.id = :client_id "
                + "ORDER BY n.date DESC"),
    @NamedQuery(
        name = "find_all_client_unseen_notification",
        query = "SELECT n FROM Notification n "
                + "WHERE n.client.id = :client_id "
                + "  AND NOT n.seen "
                + "ORDER BY n.date DESC"),
    @NamedQuery(
        name = "find_all_client_recent_unseen_notification",
        query = "SELECT n FROM Notification n "
                + "WHERE n.client.id = :client_id "
                + "  AND NOT n.seen "
                + "  AND n.date >= :date "
                + "ORDER BY n.date DESC"),
    @NamedQuery(
        name = "update_all_client_notification_seen",
        query = "UPDATE Notification n "
                + "SET n.seen = true "
                + "WHERE n.client.id = :client_id")})
public abstract class Notification implements Serializable {

    public static enum Type {
        
        APPOINTMENT,
        PAYMENT_MEAN,
        MESSAGE,
        MOVEMENT,
        OVERDRAFT;
        
        public static class Values {
            
            public static final String APPOINTMENT = "APPOINTMENT";
            public static final String PAYMENT_MEAN = "PAYMENT_MEAN";
            public static final String MESSAGE = "MESSAGE";
            public static final String MOVEMENT = "MOVEMENT";
            public static final String OVERDRAFT = "OVERDRAFT";
                    
            private Values() {}
            
        }
        
    }
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "client_fk")
    private Client client;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    private Boolean seen;
    
    public abstract Type getType();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }
    
}
