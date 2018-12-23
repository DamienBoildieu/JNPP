package jnpp.dao.entities.notifications;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.dto.notifications.NotificationDTO;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
        @NamedQuery(name = "find_all_notification_by_login", query = "SELECT n FROM NotificationEntity n "
                + "WHERE n.client.login = :login " + "ORDER BY n.date DESC"),
        @NamedQuery(name = "find_unseen_notification_by_login", query = "SELECT n FROM NotificationEntity n "
                + "WHERE n.client.login = :login " + "  AND n.seen = false "
                + "ORDER BY n.date DESC"),
        @NamedQuery(name = "find_unseen_recent_notification_by_login", query = "SELECT n FROM NotificationEntity n "
                + "WHERE n.client.login = :login " + "  AND n.seen = false"
                + "  AND n.date >= :date " + "ORDER BY n.date DESC"),
        @NamedQuery(name = "set_all_notification_seen_by_login", query = "UPDATE NotificationEntity n "
                + "SET n.seen = true " + "WHERE n.client.login = :login") })
public abstract class NotificationEntity implements Serializable {

    public static enum Type {

        APPOINTMENT, PAYMENT_MEAN, MESSAGE, MOVEMENT, OVERDRAFT;

        public static class Values {

            public static final String APPOINTMENT = "APPOINTMENT";
            public static final String PAYMENT_MEAN = "PAYMENT_MEAN";
            public static final String MESSAGE = "MESSAGE";
            public static final String MOVEMENT = "MOVEMENT";
            public static final String OVERDRAFT = "OVERDRAFT";

            private Values() {
            }

        }

    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_fk")
    private ClientEntity client;

    @Temporal(TemporalType.DATE)
    private Date date;
    private Boolean seen;

    public NotificationEntity() {
    }

    public NotificationEntity(ClientEntity client, Date date, Boolean seen) {
        this.client = client;
        this.date = date;
        this.seen = seen;
    }

    public abstract Type getType();

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
        if (!(object instanceof NotificationEntity)) {
            return false;
        }
        NotificationEntity other = (NotificationEntity) object;
        return !((this.id == null && other.id != null)
                || (this.id != null && !this.id.equals(other.id)));
    }

    public abstract NotificationDTO toDTO();

    public static List<NotificationDTO> toDTO(
            List<NotificationEntity> entities) {
        List<NotificationDTO> dtos = new ArrayList<NotificationDTO>(
                entities.size());
        Iterator<NotificationEntity> it = entities.iterator();
        while (it.hasNext()) {
            dtos.add(it.next().toDTO());
        }
        return dtos;
    }

}
