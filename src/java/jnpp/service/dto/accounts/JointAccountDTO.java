package jnpp.service.dto.accounts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jnpp.dao.entities.accounts.JointAccountEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.Identity;
import jnpp.dao.entities.clients.PrivateEntity;

public class JointAccountDTO extends MoneyAccountDTO {
    
    private final List<Identity> owners;
    
    public JointAccountDTO(JointAccountEntity account) {
        super(account);
        List<ClientEntity> clients = account.getClients();
        owners = new ArrayList<Identity>(clients.size());
        Iterator<ClientEntity> it = clients.iterator();
        while (it.hasNext()) {
            ClientEntity client = it.next();
            if (client.getType() != ClientEntity.Type.PRIVATE) throw new IllegalArgumentException("Un professionel a un compte joint.");
            owners.add(((PrivateEntity) client).getIdentity());
        }
    }
    
    @Override
    public Type getType() {
        return AccountDTO.Type.JOINT;
    }
    
    public List<Identity> getOwners() {
        return owners;
    }
    
    
    
}
