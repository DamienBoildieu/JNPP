package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.movements.MovementEntity;
import jnpp.service.dto.accounts.AccountDTO;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
        @NamedQuery(name = "find_all_rib", query = "SELECT a.rib FROM AccountEntity a"),
        @NamedQuery(name = "find_all_account_by_login", query = "SELECT a "
                + "FROM AccountEntity a " + "INNER JOIN a.clients a_clients "
                + "WHERE a_clients.login = :login"),
        @NamedQuery(name = "has_account", query = "SELECT COUNT(a) "
                + "FROM AccountEntity a " + "INNER JOIN a.clients a_clients "
                + "WHERE a_clients.login = :login"),
        @NamedQuery(name = "find_all_account", query = "SELECT a FROM AccountEntity a") })
public abstract class AccountEntity implements Serializable {

    public static enum Type {

        CURRENT, JOINT, SAVING, SHARE;

        public static class Values {

            public static final String CURRENT = "CURRENT";
            public static final String JOINT = "JOINT";
            public static final String SAVING = "SAVING";
            public static final String SHARE = "SHARE";

            private Values() {
            }

        }

    }

    private static final long serialVersionUID = 1L;

    @Id
    private String rib;

    @JoinTable(name = "account_client", joinColumns = @JoinColumn(name = "account_rib"), inverseJoinColumns = @JoinColumn(name = "client_login"))
    @ManyToMany
    private List<ClientEntity> clients = new ArrayList<ClientEntity>();

    public AccountEntity() {
    }

    public AccountEntity(String rib, ClientEntity client) {
        this.rib = rib;
        clients.add(client);
    }

    public AccountEntity(String rib, List<ClientEntity> clients) {
        this.rib = rib;
        this.clients.addAll(clients);
    }

    public boolean isOwnBy(ClientEntity client) {
        if (client == null) {
            return false;
        }
        boolean clientFound = false;
        Iterator<ClientEntity> itc = clients.iterator();
        while (itc.hasNext() && !clientFound) {
            clientFound = client.equals(itc.next());
        }
        return clientFound;
    }

    public abstract Type getType();

    public String getRib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public List<ClientEntity> getClients() {
        return clients;
    }

    public void setClients(List<ClientEntity> clients) {
        this.clients = clients;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rib != null ? rib.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AccountEntity)) {
            return false;
        }
        AccountEntity other = (AccountEntity) object;
        return !((this.rib == null && other.rib != null)
                || (this.rib != null && !this.rib.equals(other.rib)));
    }

    public abstract AccountDTO toDTO();

    public abstract boolean canEmit(MovementEntity.Type movement);

    public abstract boolean canReceive(MovementEntity.Type movement);

    public static List<AccountDTO> toDTO(List<AccountEntity> entities) {
        List<AccountDTO> dtos = new ArrayList<AccountDTO>(entities.size());
        Iterator<AccountEntity> it = entities.iterator();
        while (it.hasNext()) {
            dtos.add(it.next().toDTO());
        }
        return dtos;
    }

}
