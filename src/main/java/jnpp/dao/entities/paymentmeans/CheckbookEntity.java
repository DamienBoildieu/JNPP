package jnpp.dao.entities.paymentmeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import jnpp.dao.entities.accounts.AccountEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.service.dto.paymentmeans.CheckbookDTO;

@Entity
@DiscriminatorValue(value = PaymentMeanEntity.Type.Values.CHECKBOOK)
@NamedQueries({
        @NamedQuery(name = "find_checkbook_by_login", query = "SELECT c FROM CheckbookEntity c WHERE c.client.login = :login"),
        @NamedQuery(name = "find_checkbook_by_login_status", query = "SELECT c FROM CheckbookEntity c "
                + "WHERE c.client.login = :login "
                + "  AND c.status = :status"),
        @NamedQuery(name = "find_checkbook_by_login_rib", query = "SELECT c FROM CheckbookEntity c "
                + "WHERE c.client.login = :login "
                + "  AND c.account.rib = :rib") })
public class CheckbookEntity extends PaymentMeanEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public CheckbookEntity() {
    }

    public CheckbookEntity(String id, ClientEntity client,
            AccountEntity account, Status status) {
        super(id, client, account, status);
    }

    @Override
    public Type getType() {
        return PaymentMeanEntity.Type.CHECKBOOK;
    }

    @Override
    public String toString() {
        return "jnpp.dao.entities.Checkbook[ id=" + getId() + " ]";
    }

    @Override
    public CheckbookDTO toDTO() {
        return new CheckbookDTO(getId(), getClient().getLogin(),
                getAccount().getRib(), getStatus().toDTO());
    }

    public static List<CheckbookDTO> toDTO(List<CheckbookEntity> entities) {
        List<CheckbookDTO> dtos = new ArrayList<CheckbookDTO>(entities.size());
        Iterator<CheckbookEntity> it = entities.iterator();
        while (it.hasNext()) {
            dtos.add(it.next().toDTO());
        }
        return dtos;
    }

}
