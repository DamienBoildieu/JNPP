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
import jnpp.service.dto.paymentmeans.BankCardDTO;

@Entity
@DiscriminatorValue(value = PaymentMeanEntity.Type.Values.BANKCARD)
@NamedQueries({
    @NamedQuery(
        name = "find_bankcard_by_login",
        query = "SELECT b FROM BankCardEntity b WHERE b.client.login = :login"),
    @NamedQuery(
        name = "find_bankcard_by_login_status",
        query = "SELECT b FROM BankCardEntity b "
                + "WHERE b.client.login = :login "
                + "  AND b.status = :status"),
    @NamedQuery(
        name = "find_bankcard_by_login_rib",
        query = "SELECT b FROM BankCardEntity b "
                + "WHERE b.client.login = :login "
                + "  AND b.account.rib = :rib")})
public class BankCardEntity extends PaymentMeanEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public BankCardEntity() {}
    
    public BankCardEntity(String id, ClientEntity client, AccountEntity account, Status status) {
        super(id, client, account, status);
    }
    
    @Override
    public Type getType() {
        return PaymentMeanEntity.Type.BANKCARD;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.BankCard[ id=" + getId() + " ]";
    }
    
    @Override
    public BankCardDTO toDTO() {
        return new BankCardDTO(getId(), getClient().getLogin(), getAccount().getRib(), getStatus().toDTO());
    }
    
    public static List<BankCardDTO> toDTO(List<BankCardEntity> entities) {
        List<BankCardDTO> dtos = new ArrayList<BankCardDTO>(entities.size());
        Iterator<BankCardEntity> it = entities.iterator();
        while (it.hasNext()) dtos.add(it.next().toDTO());
        return dtos;
    }
    
}
