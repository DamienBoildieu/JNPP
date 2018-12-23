package jnpp.service.dto.accounts;

import java.util.ArrayList;
import java.util.List;

import jnpp.dao.entities.IdentityEntity;

public class JointAccountDTO extends MoneyAccountDTO {

    private List<IdentityEntity> owners;

    public JointAccountDTO(String rib, Double money, CurrencyDTO currency,
            List<IdentityEntity> owners) {
        super(rib, money, currency);
        this.owners = new ArrayList<IdentityEntity>(owners);
    }

    @Override
    public Type getType() {
        return AccountDTO.Type.JOINT;
    }

    public List<IdentityEntity> getOwners() {
        return owners;
    }

    public void setOwners(List<IdentityEntity> owners) {
        this.owners = owners;
    }

}
