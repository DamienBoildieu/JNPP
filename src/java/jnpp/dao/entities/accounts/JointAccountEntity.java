package jnpp.dao.entities.accounts;

import java.io.Serializable;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jnpp.dao.entities.clients.ClientEntity;

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
    
}
