package jnpp.service.dto.accounts;

import java.util.ArrayList;
import java.util.List;

import jnpp.service.dto.IdentityDTO;

public class JointAccountDTO extends MoneyAccountDTO {

    private List<IdentityDTO> owners;

    public JointAccountDTO(String rib, Double money, CurrencyDTO currency,
            List<IdentityDTO> owners) {
        super(rib, money, currency);
        this.owners = new ArrayList<IdentityDTO>(owners);
    }

    @Override
    public Type getType() {
        return AccountDTO.Type.JOINT;
    }

    public List<IdentityDTO> getOwners() {
        return owners;
    }

    public void setOwners(List<IdentityDTO> owners) {
        this.owners = owners;
    }

}
