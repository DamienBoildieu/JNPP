package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import jnpp.dao.entities.IdentityEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.dao.entities.movements.MovementEntity;
import jnpp.service.dto.accounts.JointAccountDTO;

@Entity
@DiscriminatorValue(value = AccountEntity.Type.Values.JOINT)
public class JointAccountEntity extends MoneyAccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;
  
    public JointAccountEntity() {}

    public JointAccountEntity(String rib, List<ClientEntity> clients, Double money, CurrencyEntity currency) {
        super(rib, clients, money, currency);
    }
    
    @Override
    public Type getType() {
        return AccountEntity.Type.JOINT;
    }
    
    @Override
    public String toString() {
        return "jnpp.dao.entities.accounts.JointAccountEntity[ id=" + getRib() + " ]";
    }
    
    @Override
    public JointAccountDTO toDTO() {
        List<IdentityEntity> owners = new ArrayList<IdentityEntity>(getClients().size());
        Iterator<ClientEntity> it = getClients().iterator();
        while (it.hasNext()) {
            ClientEntity client = it.next();
            if (client.getType() == ClientEntity.Type.PRIVATE)
                owners.add(((PrivateEntity) client).getIdentity());
            else 
                throw new IllegalStateException("Un professionel a un compte joint.");
        }
        return new JointAccountDTO(getRib(), getMoney(), getCurrency().toDTO(), owners);
    }

    @Override
    public boolean canEmit(MovementEntity.Type movement) {
        switch (movement) {
            case DEBIT:
            case PAYMENT:
            case TRANSFERT:
            case WITHDRAW:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean canReceive(MovementEntity.Type movement) {
        switch (movement) {
            case DEBIT:
            case TRANSFERT:
            case DEPOSIT:
                return true;
            default:
                return false;
        }
    }
    
}
