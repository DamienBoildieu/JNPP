package jnpp.service.dto.accounts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jnpp.dao.entities.accounts.JointAccountEntity;
import jnpp.dao.entities.clients.ClientEntity;
import jnpp.dao.entities.Identity;
import jnpp.dao.entities.accounts.Currency;
import jnpp.dao.entities.clients.PrivateEntity;

public class JointAccountDTO extends MoneyAccountDTO {
    
    private List<Identity> owners;
    
    public JointAccountDTO(String rib, Double money, Currency currency, List<Identity> owners) {
        super(rib, money, currency);
        this.owners = new ArrayList<Identity>(owners);
    }
    
    @Override
    public Type getType() {
        return AccountDTO.Type.JOINT;
    }
    
    public List<Identity> getOwners() {
        return owners;
    }

    public void setOwners(List<Identity> owners) {
        this.owners = owners;
    }
    
}
