package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import jnpp.dao.entities.Identity;

import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.clients.PrivateEntity;
import jnpp.service.dto.accounts.JointAccountDTO;
import jnpp.service.dto.clients.ClientDTO;
import jnpp.service.dto.clients.PrivateDTO;

@Entity
@DiscriminatorValue(value = AccountEntity.Type.Values.JOINT)
public class JointAccountEntity extends MoneyAccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;
  
    public JointAccountEntity() {}

    public JointAccountEntity(String rib, List<ClientEntity> clients, Double money, Currency currency) {
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
        List<Identity> owners = new ArrayList<Identity>(getClients().size());
        Iterator<ClientEntity> it = getClients().iterator();
        while (it.hasNext()) {
            ClientEntity client = it.next();
            if (client.getType() == ClientEntity.Type.PRIVATE)
                owners.add(((PrivateEntity) client).getIdentity());
            else 
                throw new IllegalArgumentException("Un professionel a un compte joint.");
        }
        return new JointAccountDTO(getRib(), getMoney(), getCurrency(), owners);
    }
    
}
